
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;

public interface PostProcessingEffect {
	public void render (Texture input, Texture output);

	public void init (PostProcessingSystem system);
}
