
package com.badlogic.gdx.graphics.g3d.postprocessing.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;

public interface PostProcessingComponent {
	public void render (Texture input, Texture output);

	public void init (PostProcessingSystem system);
}
