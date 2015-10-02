
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;

public class PostProcessingEffect {
	protected Array<PostProcessingComponent> components = new Array<PostProcessingComponent>();
	protected PostProcessingSystem system;
	protected FrameBuffer frameBuffer;

	public PostProcessingEffect addComponent (PostProcessingComponent component) {
		components.add(component);
		return this;
	}

	public Texture render (Texture input, boolean window) {
		boolean window2 = false;
		Texture output = null;
		for (PostProcessingComponent component : components) {
			if (component == components.get(components.size - 1) && window) {
				window2 = true;
			}
			output = component.render(input, window2);
			input = output;
		}
		return output;
	}

	public void init (PostProcessingSystem system) {
		this.system = system;
		for (PostProcessingComponent component : components) {
			component.init(system);
		}
	}

	public Array<PostProcessingComponent> getComponents () {
		return components;
	}

	public boolean needsMainTexture () {
		for (PostProcessingComponent component : components) {
			if (component.needsMainTexture()) return true;
		}
		return false;
	}
}
