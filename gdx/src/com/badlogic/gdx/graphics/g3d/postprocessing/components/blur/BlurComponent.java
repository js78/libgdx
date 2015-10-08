
package com.badlogic.gdx.graphics.g3d.postprocessing.components.blur;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class BlurComponent extends QuadComponent<BlurShader> {
	@Override
	protected BlurShader getShader () {
		return new BlurShader();
	}

	public BlurComponent setRadius (int radius) {
		this.shader.setRadius(radius);
		return this;
	}

	public BlurComponent setDirection (float x, float y) {
		this.shader.setDirection(x, y);
		return this;
	}
}
