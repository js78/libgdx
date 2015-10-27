
package com.badlogic.gdx.graphics.g3d.postprocessing.components.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public abstract class QuadComponent<T extends QuadShader> implements PostProcessingComponent {
	protected PostProcessingSystem system;
	protected FrameBuffer frameBuffer;
	protected T shader;

	public QuadComponent () {
		shader = getShader();
		shader.init(this);
	}

	protected FrameBuffer getFrameBuffer (int width, int height) {
		return new FrameBuffer(Format.RGBA8888, width, height, true);
	}

	abstract protected T getShader ();

	protected void checkFrameBuffer (int width, int height) {
		if (frameBuffer != null && (frameBuffer.getWidth() != width || frameBuffer.getHeight() != height)) {
			frameBuffer.dispose();
			frameBuffer = null;
		}
	}

	@Override
	public Texture render (Texture input, boolean window, int width, int height) {
		if (!window) {
			checkFrameBuffer(width, height);

			if (frameBuffer == null) {
				frameBuffer = getFrameBuffer(width, height);
			}

			frameBuffer.begin();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		shader.render(input);

		if (!window) {
			// ScreenshotFactory.saveScreenshot(frameBuffer.getWidth(), frameBuffer.getHeight(), "toto", true);
			frameBuffer.end();
			return frameBuffer.getColorBufferTexture();
		}
		return null;
	}

	@Override
	public void init (PostProcessingSystem system) {
		this.system = system;
	}

	@Override
	public PostProcessingSystem getSystem () {
		return system;
	}

	@Override
	public boolean needsMainTexture () {
		return false;
	}

	@Override
	public int getWidth () {
		if (frameBuffer == null) return 0;
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight () {
		if (frameBuffer == null) return 0;
		return frameBuffer.getHeight();
	}
}
