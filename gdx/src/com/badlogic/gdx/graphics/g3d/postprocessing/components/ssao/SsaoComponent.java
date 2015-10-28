
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class SsaoComponent extends QuadComponent<SsaoShader> {

	@Override
	protected SsaoShader getShader () {
		return new SsaoShader();
	}

	public SsaoComponent setKernelSize (int kernelSize) {
		shader.setKernelSize(kernelSize);
		return this;
	}

	public SsaoComponent setRadius (float radius) {
		shader.setRadius(radius);
		return this;
	}

	public SsaoComponent setPower (float power) {
		shader.setPower(power);
		return this;
	}

	public SsaoComponent setNoiseSize (int size) {
		shader.setNoiseSize(size);
		return this;
	}
}
