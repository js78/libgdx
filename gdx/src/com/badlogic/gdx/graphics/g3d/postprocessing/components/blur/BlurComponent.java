
package com.badlogic.gdx.graphics.g3d.postprocessing.components.blur;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class BlurComponent extends QuadComponent<BlurShader> {
	@Override
	protected BlurShader getShader () {
		return new BlurShader();
	}

	public void setRadius (int radius) {
		this.shader.setRadius(radius);
	}

	public void setDirection (float x, float y) {
		this.shader.setDirection(x, y);
	}
}
