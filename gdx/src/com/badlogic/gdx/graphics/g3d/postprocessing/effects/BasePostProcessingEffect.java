
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;

public class BasePostProcessingEffect implements PostProcessingEffect {
	protected Array<PostProcessingComponent> components = new Array<PostProcessingComponent>();
	protected PostProcessingSystem system;
	protected FrameBuffer frameBuffer;

	public void addComponent (PostProcessingComponent component) {
		components.add(component);
	}

	@Override
	public void render (Texture input, Texture output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init (PostProcessingSystem system) {
		this.system = system;
	}

}
