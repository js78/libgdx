
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class SsaoShader extends QuadShader {
	/** Uniforms */
	protected int u_tanHalfFov;
	protected int u_aspectRatio;
	protected int u_noiseTexture;
	protected int u_projectionMatrix;
	protected int u_kernelSize;
	protected int u_radius;
	protected int u_power;

	protected int u_kernelOffsets0;
	protected int u_kernelOffsets1;
	protected int kernelOffsetsLoc;
	protected int kernelOffsetsSize;

	/** Parameters */
	protected int kernelSize;
	protected float radius;
	protected float power;
	protected int noiseSize;

	/** Internal */
	protected Texture noiseTexture;
	protected Array<Vector3> kernelOffsets = new Array<Vector3>();
	protected boolean dirtyNoise = true;
	protected boolean dirtyKernelSize = true;
	protected Vector3 tmpV = new Vector3();

	public SsaoShader () {
		this(4, 1.5f, 2, 4);
	}

	public SsaoShader (int kernelSize, float radius, float power, int noiseSize) {
		super();

		// @TODO FORCE
		boolean force = true;
		u_tanHalfFov = program.fetchUniformLocation("u_tanHalfFov", force);
		u_aspectRatio = program.fetchUniformLocation("u_aspectRatio", force);
		u_noiseTexture = program.fetchUniformLocation("u_noiseTexture", force);
		u_projectionMatrix = program.fetchUniformLocation("u_projectionMatrix", force);
		u_kernelSize = program.fetchUniformLocation("u_kernelSize", force);
		u_radius = program.fetchUniformLocation("u_radius", force);
		u_power = program.fetchUniformLocation("u_power", force);

		u_kernelOffsets0 = program.fetchUniformLocation("u_kernelOffsets[0]", force);
		u_kernelOffsets1 = program.fetchUniformLocation("u_kernelOffsets[1]", force);
		kernelOffsetsLoc = u_kernelOffsets0;
		kernelOffsetsSize = u_kernelOffsets1 - u_kernelOffsets0;

		this.kernelSize = kernelSize;
		this.radius = radius;
		this.power = power;
		this.noiseSize = noiseSize;
	}

	@Override
	protected String getVertex () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/ssao/ssao.vertex.glsl";
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

		PerspectiveCamera camera = (PerspectiveCamera)component.getSystem().camera;

		program.setUniformf(u_tanHalfFov, (float)Math.tan(camera.fieldOfView / 2f));
		program.setUniformf(u_aspectRatio, camera.viewportWidth / camera.viewportHeight);
		program.setUniformi(u_noiseTexture, context.textureBinder.bind(noiseTexture));
		program.setUniformMatrix(u_projectionMatrix, camera.projection);
		program.setUniformi(u_kernelSize, kernelSize);
		program.setUniformf(u_radius, radius);
		program.setUniformf(u_power, power);

		for (int i = 0; i < kernelSize; i++) {
			int idx = kernelOffsetsLoc + i * kernelOffsetsSize;
			program.setUniformf(idx, kernelOffsets.get(i));
		}
	}

	protected void generateNoiseTexture () {
		if (noiseTexture != null) {
			noiseTexture.dispose();
			noiseTexture = null;
		}

		Pixmap pix = new Pixmap(noiseSize, noiseSize, Pixmap.Format.RGBA8888);
		for (int x = 0; x < noiseSize; x++) {
			for (int y = 0; y < noiseSize; y++) {
				tmpV.set(MathUtils.random(-1, 1), MathUtils.random(-1, 1), 0).nor();
				pix.drawPixel(x, y, Color.rgba8888(tmpV.x, tmpV.y, tmpV.z, 1));
			}
		}

		noiseTexture = new Texture(pix, Pixmap.Format.RGBA8888, false);
	}

	protected void generateKernelOffsets () {
		kernelOffsets.clear();

		for (int i = 0; i < kernelSize; i++) {
			Vector3 v = new Vector3(MathUtils.random(-1, 1), MathUtils.random(-1, 1), MathUtils.random(0, 1)).nor();
			float scale = (float)i / (float)kernelSize;
			v.scl(Interpolation.linear.apply(0.1f, 1, scale * scale));

			kernelOffsets.add(v);
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

	@Override
	protected boolean alwaysDirty () {
		return true;
	}
}
