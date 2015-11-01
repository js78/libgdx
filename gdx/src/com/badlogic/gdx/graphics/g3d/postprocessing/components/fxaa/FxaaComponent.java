
package com.badlogic.gdx.graphics.g3d.postprocessing.components.fxaa;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class FxaaComponent extends QuadComponent<FxaaShader> {
	@Override
	protected FxaaShader getShader () {
		return new FxaaShader();
	}

	public FxaaComponent setRadius (int radius) {
		this.shader.setRadius(radius);
		return this;
	}

	public FxaaComponent setDirection (float x, float y) {
		this.shader.setDirection(x, y);
		return this;
	}
}
