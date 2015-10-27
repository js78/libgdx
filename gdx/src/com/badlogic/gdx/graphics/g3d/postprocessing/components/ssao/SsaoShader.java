
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class SsaoShader extends QuadShader {
	/** Uniforms */
	protected int u_noiseTexture;
	protected int u_projectionMatrix;
	protected int u_kernelSize;
	protected int u_kernelOffsets;
	protected int u_radius;
	protected int u_power;

	/** Parameters */
	protected int kernelSize;
	protected float radius;
	protected float power;
	protected int noiseSize;

	/** Internal */
	protected Texture noiseTexture;
	protected float[] kernelOffsets;
	protected boolean dirtyNoise = true;
	protected boolean dirtyKernelSize = true;
	protected Vector3 tmpV = new Vector3();

	public SsaoShader () {
		this(4, 1.5f, 2, 4);
	}

	public SsaoShader (int kernelSize, float radius, float power, int noiseSize) {
		super();

		u_noiseTexture = program.fetchUniformLocation("u_noiseTexture", true);
		u_projectionMatrix = program.fetchUniformLocation("u_projectionMatrix", true);
		u_kernelSize = program.fetchUniformLocation("u_kernelSize", true);
		u_kernelOffsets = program.fetchUniformLocation("u_kernelOffsets", true);
		u_radius = program.fetchUniformLocation("u_radius", true);
		u_power = program.fetchUniformLocation("u_power", true);

		this.kernelSize = kernelSize;
		this.radius = radius;
		this.power = power;
		this.noiseSize = noiseSize;
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/ssao/ssao.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		if (dirtyNoise || noiseTexture == null) {
			generateNoiseTexture();
			dirtyNoise = false;
		}

		if (dirtyKernelSize || kernelOffsets == null) {
			generateKernelOffsets();
			dirtyKernelSize = false;
		}

		program.setUniformi(u_noiseTexture, context.textureBinder.bind(noiseTexture));
		program.setUniformMatrix(u_projectionMatrix, this.component.getSystem().camera.projection);
		program.setUniformf(u_kernelSize, kernelSize);
		program.setUniform3fv(u_kernelOffsets, kernelOffsets, 0, kernelOffsets.length);
		program.setUniformf(u_radius, radius);
		program.setUniformf(u_power, power);
	}

	protected void generateNoiseTexture () {
		if (noiseTexture != null) {
			noiseTexture.dispose();
			noiseTexture = null;
		}

		Pixmap pix = new Pixmap(noiseSize, noiseSize, Pixmap.Format.RGB888);
		for (int x = 0; x < noiseSize; x++) {
			for (int y = 0; y < noiseSize; y++) {
				tmpV.set(MathUtils.random(-1, 1), MathUtils.random(-1, 1), 0).nor();
				pix.drawPixel(x, y, Color.rgb888(tmpV.x, tmpV.y, tmpV.z));
			}
		}

		noiseTexture = new Texture(pix, Pixmap.Format.RGB888, false);
	}

	protected void generateKernelOffsets () {
		if (kernelOffsets != null) {
			kernelOffsets = null;
		}

		kernelOffsets = new float[kernelSize * 3];

		for (int i = 0; i < kernelSize; i++) {
			tmpV.set(MathUtils.random(-1, 1), MathUtils.random(-1, 1), MathUtils.random(0, 1)).nor();
			float scale = (float)i / (float)kernelSize;
			tmpV.scl(Interpolation.linear.apply(0.1f, 1, scale * scale));

			int id = i * 3;
			kernelOffsets[id] = tmpV.x;
			kernelOffsets[id + 1] = tmpV.y;
			kernelOffsets[id + 2] = tmpV.z;
		}
	}

	public void setKernelSize (int kernelSize) {
		this.kernelSize = kernelSize;
		dirty = true;
		dirtyKernelSize = true;
	}

	public void setRadius (float radius) {
		this.radius = radius;
		dirty = true;
	}

	public void setPower (float power) {
		this.power = power;
		dirty = true;
	}

	public void setNoiseSize (int size) {
		this.noiseSize = size;
		dirty = true;
		dirtyNoise = true;
	}
}
