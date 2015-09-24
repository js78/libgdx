
package com.badlogic.gdx.graphics.g3d.postprocessing.providers;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;

public interface Provider {

	public void begin (PostProcessingSystem system);

	public void end ();

	public ModelBatch getModelBatch ();
}
