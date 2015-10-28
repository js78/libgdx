
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

	@Override
	public BasePostProcessingEffect addComponent (PostProcessingComponent component) {
		components.add(component);
		return this;
	}

	@Override
	public Texture render (Texture input, boolean window) {
		boolean window2 = false;
		Texture output = null;
		int width = input.getWidth(), height = input.getHeight();

		for (PostProcessingComponent component : components) {
			if (component == components.get(components.size - 1) && window) {
				window2 = true;
			}
			output = component.render(input, window2, width, height);
			width = component.getWidth();
			height = component.getHeight();
			input = output;
		}

		return output;
	}

	@Override
	public void init (PostProcessingSystem system) {
		this.system = system;
		for (PostProcessingComponent component : components) {
			component.init(system);
		}
	}

	@Override
	public Array<PostProcessingComponent> getComponents () {
		return components;
	}

	@Override
	public boolean needsMainTexture () {
		for (PostProcessingComponent component : components) {
			if (component.needsMainTexture()) return true;
		}
		return false;
	}
}
