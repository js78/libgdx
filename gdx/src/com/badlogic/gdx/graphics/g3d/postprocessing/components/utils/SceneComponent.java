
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
		frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		modelBatch = new ModelBatch(getShaderProvider());
	}

	abstract protected ShaderProvider getShaderProvider ();

	@Override
	public Texture render (Texture input, boolean window) {
		if (!window) frameBuffer.begin();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(system.camera);
		modelBatch.render(system.instances);
		modelBatch.end();
		if (!window) frameBuffer.end();
		return frameBuffer.getColorBufferTexture();
	}

	@Override
	public void init (PostProcessingSystem system) {
		this.system = system;
	}

	@Override
	public boolean needsMainTexture () {
		return false;
	}
}
