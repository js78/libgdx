
package com.badlogic.gdx.graphics.g3d.postprocessing.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;

public interface PostProcessingComponent {
	public Texture render (Texture input, boolean window);

	public void init (PostProcessingSystem system);

	public boolean needsMainTexture ();
}
