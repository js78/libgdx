
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class LensFlareComponent extends QuadComponent<LensFlareShader> {

	@Override
	protected LensFlareShader getShader () {
		return new LensFlareShader();
	}

	public LensFlareComponent setSamples (int samples) {
		this.shader.setSamples(samples);
		return this;
	}

	public LensFlareComponent setDispersal (float dispersal) {
		this.shader.setDispersal(dispersal);
		return this;
	}

	public LensFlareComponent setHaloWidth (float haloWidth) {
		this.shader.setHaloWidth(haloWidth);
		return this;
	}

	public LensFlareComponent setDistortion (float distortion) {
		this.shader.setDistortion(distortion);
		return this;
	}
}
