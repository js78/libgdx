
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.utils.Array;

public interface PostProcessingEffect {
	public BasePostProcessingEffect addComponent (PostProcessingComponent component);

	public Texture render (Texture input, boolean window);

	public void init (PostProcessingSystem system);

	public Array<PostProcessingComponent> getComponents ();

	public boolean needsMainTexture ();
}
