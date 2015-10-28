/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.graphics.g3d.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.graphics.g3d.postprocessing.effects.BasePostProcessingEffect;
import com.badlogic.gdx.graphics.g3d.postprocessing.effects.PostProcessingEffect;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;

/** @author realitix */
public class PostProcessingSystem {
	/** Effects added to system */
	protected Array<PostProcessingEffect> effects = new Array<PostProcessingEffect>();
	/** Global frame buffer */
	protected FrameBuffer frameBuffer;
	/** Scene texture */
	protected Texture mainTexture;
	/** Allows to copy mainTexture */
	protected QuadShader copyShader;
	protected FrameBuffer copyFrameBuffer;

	public Array<ModelInstance> instances;
	public Environment environment;
	public Camera camera;

	public PostProcessingSystem () {
		frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		copyShader = new QuadShader();
	}

	public void begin () {
		frameBuffer.begin();
	}

	public void end () {
		frameBuffer.end();
		mainTexture = frameBuffer.getColorBufferTexture();
	}

	public Texture getMainTexture () {
		return mainTexture;
	}

	public PostProcessingSystem addEffect (BasePostProcessingEffect effect) {
		effects.add(effect);
		effect.init(this);
		return this;
	}

	public void render (Camera camera, Array<ModelInstance> instances, Environment environment) {
		this.instances = instances;
		this.environment = environment;
		this.camera = camera;

		boolean window = false;
		for (PostProcessingEffect effect : effects) {
			if (effect == effects.get(effects.size - 1)) {
				window = true;
			}
			mainTexture = effect.render(interceptTexture(effect), window);
		}
	}

	protected Texture interceptTexture (PostProcessingEffect effect) {
		if (!effect.needsMainTexture()) return mainTexture;
		return copyTexture(mainTexture);
	}

	protected Texture copyTexture (Texture sourceTexture) {
		if (copyFrameBuffer == null) {
			copyFrameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		copyFrameBuffer.begin();
		copyShader.render(sourceTexture);
		copyFrameBuffer.end();
		return copyFrameBuffer.getColorBufferTexture();
	}
}
