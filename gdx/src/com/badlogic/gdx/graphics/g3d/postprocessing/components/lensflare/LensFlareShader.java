
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class LensFlareShader extends QuadShader {
	/** Uniforms */
	protected int u_lensColor;
	protected int u_samples;
	protected int u_dispersal;
	protected int u_haloWidth;
	protected int u_distortion;

	/** Parameters */
	protected Texture lensColor;
	protected int samples;
	protected float dispersal;
	protected float haloWidth;
	protected float distortion;

	public LensFlareShader () {
		this(8, 0.25f, 1, 1);
	}

	public LensFlareShader (int samples, float dispersal, float haloWidth, float distortion) {
		super();

		u_lensColor = program.fetchUniformLocation("u_lensColor", true);
		u_samples = program.fetchUniformLocation("u_samples", true);
		u_dispersal = program.fetchUniformLocation("u_dispersal", true);
		u_haloWidth = program.fetchUniformLocation("u_haloWidth", true);
		u_distortion = program.fetchUniformLocation("u_distortion", true);

		this.lensColor = new Texture(
			Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare/lenscolor.png"));
		this.samples = samples;
		this.dispersal = dispersal;
		this.haloWidth = haloWidth;
		this.distortion = distortion;
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare/lensflare.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		program.setUniformi(u_lensColor, context.textureBinder.bind(lensColor));
		program.setUniformi(u_samples, samples);
		program.setUniformf(u_dispersal, dispersal);
		program.setUniformf(u_haloWidth, haloWidth);
		program.setUniformf(u_distortion, distortion);
	}

	@Override
	protected boolean alwaysDirty () {
		return true;
	}

	public void setSamples (int samples) {
		this.samples = samples;
		dirty = true;
	}

	public void setDispersal (float dispersal) {
		this.dispersal = dispersal;
		dirty = true;
	}

	public void setHaloWidth (float haloWidth) {
		this.haloWidth = haloWidth;
		dirty = true;
	}

	public void setDistortion (float distortion) {
		this.distortion = distortion;
		dirty = true;
	}
}
