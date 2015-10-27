
package com.badlogic.gdx.graphics.g3d.postprocessing.components.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public abstract class SceneComponent implements PostProcessingComponent {
	protected PostProcessingSystem system;
	protected FrameBuffer frameBuffer;
	protected ModelBatch modelBatch;

	public SceneComponent () {
		modelBatch = new ModelBatch(getShaderProvider());
	}

	abstract protected ShaderProvider getShaderProvider ();

	protected FrameBuffer getFrameBuffer (int width, int height) {
		return new FrameBuffer(Format.RGBA8888, width, height, true);
	}

	@Override
	public Texture render (Texture input, boolean window, int width, int height) {
		if (!window) {
			if (frameBuffer != null && (frameBuffer.getWidth() != width || frameBuffer.getHeight() != height)) {
				frameBuffer.dispose();
				frameBuffer = null;
			}

			if (frameBuffer == null) {
				frameBuffer = getFrameBuffer(width, height);
			}

			frameBuffer.begin();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(system.camera);
		modelBatch.render(system.instances);
		modelBatch.end();

		if (!window) {
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
