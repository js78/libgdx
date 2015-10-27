
package com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class DownSampleComponent extends QuadComponent<DownSampleShader> {
	protected float frameBufferScale;
	protected float currentFrameBufferScale;

	public DownSampleComponent () {
		this(1);
	}

	public DownSampleComponent (float scale) {
		setFrameBufferScale(scale);
	}

	@Override
	protected DownSampleShader getShader () {
		return new DownSampleShader();
	}

	public DownSampleComponent setFrameBufferScale (float scale) {
		if (scale <= 0 || scale > 1) {
			throw new GdxRuntimeException("Can't scale framebuffer.");
		}
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
	protected void checkFrameBuffer (int width, int height) {
		super.checkFrameBuffer(width, height);
		if (frameBuffer != null && currentFrameBufferScale != frameBufferScale) {
			frameBuffer.dispose();
			frameBuffer = null;
		}

		currentFrameBufferScale = frameBufferScale;
	}

	@Override
	protected FrameBuffer getFrameBuffer (int width, int height) {
		return new FrameBuffer(Format.RGBA8888, (int)(width * frameBufferScale), (int)(height * frameBufferScale), true);
	}
}
