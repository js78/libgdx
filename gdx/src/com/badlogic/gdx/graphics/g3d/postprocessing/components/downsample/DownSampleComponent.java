
package com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class DownSampleComponent extends QuadComponent<DownSampleShader> {
	protected float frameBufferScale;

	public DownSampleComponent () {
		this(1);
	}

	public DownSampleComponent (float scale) {
		this.frameBufferScale = scale;
	}

	@Override
	protected DownSampleShader getShader () {
		return new DownSampleShader();
	}

	public DownSampleComponent setFrameBufferScale (float scale) {
		this.frameBufferScale = scale;
		return this;
	}

	public DownSampleComponent setScale (float scale) {
		this.shader.setScale(scale);
		return this;
	}

	public DownSampleComponent setBias (float bias) {
		this.shader.setBias(bias);
		return this;
	}

	@Override
	protected FrameBuffer getFrameBuffer (int width, int height) {
		return new FrameBuffer(Format.RGBA8888, (int)(width * frameBufferScale), (int)(height * frameBufferScale), true);
	}
}
