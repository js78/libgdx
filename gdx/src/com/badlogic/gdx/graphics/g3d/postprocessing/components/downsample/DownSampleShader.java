
package com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class DownSampleShader extends QuadShader {
	/** Uniforms */
	protected int u_scale;
	protected int u_bias;

	/** Parameters */
	protected float scale;
	protected float bias;

	public DownSampleShader () {
		this(1f, 0);
	}

	public DownSampleShader (float scale, float bias) {
		super();
		u_scale = program.fetchUniformLocation("u_scale", true);
		u_bias = program.fetchUniformLocation("u_bias", true);
		this.scale = scale;
		this.bias = bias;
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/downsample/downsample.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		program.setUniformf(u_scale, scale, scale, scale, scale);
		program.setUniformf(u_bias, bias, bias, bias, bias);
	}

	public void setScale (float scale) {
		this.scale = scale;
	}

	public void setBias (float bias) {
		this.bias = bias;
	}
}
