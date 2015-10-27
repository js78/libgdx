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

package com.badlogic.gdx.graphics.g3d.postprocessing.components.normaldepth;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;

public class NormalDepthShaderProvider extends BaseShaderProvider {
	public final NormalDepthShader.Config config;

	public NormalDepthShaderProvider (final NormalDepthShader.Config config) {
		this.config = (config == null) ? new NormalDepthShader.Config() : config;
	}

	public NormalDepthShaderProvider (final String vertexShader, final String fragmentShader) {
		this(new NormalDepthShader.Config(vertexShader, fragmentShader));
	}

	public NormalDepthShaderProvider (final FileHandle vertexShader, final FileHandle fragmentShader) {
		this(vertexShader.readString(), fragmentShader.readString());
	}

	public NormalDepthShaderProvider () {
		this(null);
	}

	@Override
	protected Shader createShader (final Renderable renderable) {
		return new NormalDepthShader(renderable, config);
	}
}
