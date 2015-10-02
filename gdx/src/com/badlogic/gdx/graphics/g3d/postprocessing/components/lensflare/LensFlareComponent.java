
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class LensFlareComponent extends QuadComponent {
	protected int samples;
	protected float dispersal;
	protected float haloWidth;
	protected float distortion;

	public LensFlareComponent () {
		this(8, 0.25f, 1, 1);
	}

	public LensFlareComponent (int samples, float dispersal, float haloWidth, float distortion) {
		this.samples = samples;
		this.dispersal = dispersal;
		this.haloWidth = haloWidth;
		this.distortion = distortion;
	}

	@Override
	protected QuadShader getShader () {
		return new LensFlareShader(samples, dispersal, haloWidth, distortion);
	}
}
