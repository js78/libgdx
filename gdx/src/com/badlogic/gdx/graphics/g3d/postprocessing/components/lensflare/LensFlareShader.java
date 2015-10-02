
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class LensFlareShader extends QuadShader {
	/** Uniforms */
	protected int u_lensColor;
	protected int u_samples;
	protected int u_dispersal;
	protected int u_haloWidth;
	protected int u_distortion;
	protected int u_lensStarMatrix;

	/** Parameters */
	protected Texture lensColor;
	protected int samples;
	protected float dispersal;
	protected float haloWidth;
	protected float distortion;

	protected Vector3 camX = new Vector3();
	protected Vector3 camZ = new Vector3();

	protected Matrix3 scaleBias1 = new Matrix3(new float[] {2, 0, 0, 0, 2, 0, -1, -1, 1});
	protected Matrix3 scaleBias2 = new Matrix3(new float[] {0.5f, 0, 0, 0, 0.5f, 0, 0.5f, 0.5f, 1});
	protected Matrix3 rotation = new Matrix3();

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
		u_lensStarMatrix = program.fetchUniformLocation("u_lensStarMatrix", true);

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
		// Compute Matrix3
		float[] v = this.component.getSystem().camera.view.val;
		camX.set(v[Matrix4.M00], v[Matrix4.M10], v[Matrix4.M20]);
		camZ.set(v[Matrix4.M01], v[Matrix4.M11], v[Matrix4.M21]);

		float camRotation = camX.dot(0, 0, 1) + camZ.dot(0, 1, 0);
		rotation.val[Matrix3.M00] = (float)Math.cos(camRotation);
		rotation.val[Matrix3.M10] = (float)Math.sin(camRotation);
		rotation.val[Matrix3.M20] = 0;

		rotation.val[Matrix3.M01] = (float)-Math.sin(camRotation);
		rotation.val[Matrix3.M11] = (float)Math.cos(camRotation);
		rotation.val[Matrix3.M21] = 0;

		rotation.val[Matrix3.M02] = 0;
		rotation.val[Matrix3.M12] = 0;
		rotation.val[Matrix3.M22] = 1;

		Matrix3 resultMatrix = new Matrix3(scaleBias2).mul(rotation).mul(scaleBias1);

		program.setUniformi(u_lensColor, context.textureBinder.bind(lensColor));
		program.setUniformi(u_samples, samples);
		program.setUniformf(u_dispersal, dispersal);
		program.setUniformf(u_haloWidth, haloWidth);
		program.setUniformf(u_distortion, distortion);
		program.setUniformMatrix(u_lensStarMatrix, resultMatrix);
	}
}
