
package com.badlogic.gdx.graphics.g3d.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.postprocessing.effects.PostProcessingEffect;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;

public class PostProcessingSystem {
	protected Array<PostProcessingEffect> effects = new Array<PostProcessingEffect>();
	protected FrameBuffer frameBuffer;
	protected Texture mainTexture;
	public Array<ModelInstance> instances;
	public Environment environment;
	public Camera camera;

	public PostProcessingSystem () {
		frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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

	public PostProcessingSystem addEffect (PostProcessingEffect effect) {
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
			mainTexture = effect.render(mainTexture, window);
		}
	}
}
