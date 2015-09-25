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

package com.badlogic.gdx.graphics.g3d.postprocessing.components.depth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DepthShader extends DefaultShader {

	private static String defaultVertexShader = null;

	public final static String getDefaultVertexShader () {
		if (defaultVertexShader == null)
			defaultVertexShader = Gdx.files.classpath(
				"com/badlogic/gdx/graphics/g3d/postprocessing/components/depth/depth.vertex.glsl").readString();
		return defaultVertexShader;
	}

	private static String defaultFragmentShader = null;

	public final static String getDefaultFragmentShader () {
		if (defaultFragmentShader == null)
			defaultFragmentShader = Gdx.files.classpath(
				"com/badlogic/gdx/graphics/g3d/postprocessing/components/depth/depth.fragment.glsl").readString();
		return defaultFragmentShader;
	}

	public static String createPrefix (final Renderable renderable, final Config config) {
		String prefix = DefaultShader.createPrefix(renderable, config);
		return prefix;
	}

	public DepthShader (final Renderable renderable) {
		this(renderable, new Config());
	}

	public DepthShader (final Renderable renderable, final Config config) {
		this(renderable, config, createPrefix(renderable, config));
	}

	public DepthShader (final Renderable renderable, final Config config, final String prefix) {
		this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(),
			config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
	}

	public DepthShader (final Renderable renderable, final Config config, final String prefix, final String vertexShader,
		final String fragmentShader) {
		this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
	}

	public DepthShader (final Renderable renderable, final Config config, final ShaderProgram shaderProgram) {
		super(renderable, config, shaderProgram);
	}
}
