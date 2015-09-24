
package com.badlogic.gdx.graphics.g3d.postprocessing.providers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/** Provider which contains the scene */
public class SceneProvider implements Provider {
	protected FrameBuffer frameBuffer;
	protected ModelBatch modelBatch;

	public SceneProvider () {
		modelBatch = new ModelBatch();
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	}

	@Override
	public void begin (PostProcessingSystem system) {
		modelBatch = system.getMainModelBatch();
		frameBuffer.begin();
	}

	@Override
	public void end () {
		frameBuffer.end();
	}

	@Override
	public ModelBatch getModelBatch () {
		return modelBatch;
	}

}
