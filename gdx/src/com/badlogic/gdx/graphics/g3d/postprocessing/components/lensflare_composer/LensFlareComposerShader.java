
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class LensFlareComposerShader extends QuadShader {
	/** Uniforms */
	protected int u_mainTexture;
	protected int u_lensDirtTexture;
	protected int u_lensStarTexture;
	protected int u_lensStarMatrix;
	protected int u_flareOnly;

	/** Parameters */
	protected Texture lensDirtTexture;
	protected Texture lensStarTexture;
	protected int flareOnly = 0;

	protected Vector3 camX = new Vector3();
	protected Vector3 camZ = new Vector3();

	protected Matrix3 scaleBias1 = new Matrix3(new float[] {2, 0, 0, 0, 2, 0, -1, -1, 1});
	protected Matrix3 scaleBias2 = new Matrix3(new float[] {0.5f, 0, 0, 0, 0.5f, 0, 0.5f, 0.5f, 1});
	protected Matrix3 rotation = new Matrix3();
	protected Matrix3 tmpM = new Matrix3();

	public LensFlareComposerShader () {
		super();

		u_mainTexture = program.fetchUniformLocation("u_mainTexture", true);
		u_lensDirtTexture = program.fetchUniformLocation("u_lensDirtTexture", true);
		u_lensStarTexture = program.fetchUniformLocation("u_lensStarTexture", true);
		u_lensStarMatrix = program.fetchUniformLocation("u_lensStarMatrix", true);
		u_flareOnly = program.fetchUniformLocation("u_flareOnly", true);

		this.lensDirtTexture = new Texture(
			Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensdirt.png"));
		this.lensStarTexture = new Texture(
			Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensstar.png"));
	}

	public void setFlareOnly (boolean flareOnly) {
		if (flareOnly)
			this.flareOnly = 1;
		else
			this.flareOnly = 0;
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensflare_composer.fragment.glsl";
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

		Matrix3 resultMatrix = tmpM.set(scaleBias2).mul(rotation).mul(scaleBias1);

		program.setUniformi(u_mainTexture, context.textureBinder.bind(component.getSystem().getMainTexture()));
		program.setUniformi(u_lensDirtTexture, context.textureBinder.bind(lensDirtTexture));
		program.setUniformi(u_lensStarTexture, context.textureBinder.bind(lensStarTexture));
		program.setUniformMatrix(u_lensStarMatrix, resultMatrix);
		program.setUniformi(u_flareOnly, flareOnly);
	}

	@Override
	protected boolean alwaysDirty () {
		return true;
	}
}
