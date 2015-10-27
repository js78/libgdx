
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class SsaoComponent extends QuadComponent<SsaoShader> {

	@Override
	protected SsaoShader getShader () {
		return new SsaoShader();
	}

	public SsaoComponent setSamples (int samples) {
		this.shader.setSamples(samples);
		return this;
	}

	public SsaoComponent setDispersal (float dispersal) {
		this.shader.setDispersal(dispersal);
		return this;
	}

	public SsaoComponent setHaloWidth (float haloWidth) {
		this.shader.setHaloWidth(haloWidth);
		return this;
	}

	public SsaoComponent setDistortion (float distortion) {
		this.shader.setDistortion(distortion);
		return this;
	}
}
