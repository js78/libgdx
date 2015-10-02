
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Matrix3;

public class LensFlareComposerShader extends QuadShader {
	/** Uniforms */
	protected int u_mainTexture;
	protected int u_lensDirtTexture;
	protected int u_lensStarTexture;
	protected int u_lensStarMatrix;

	/** Parameters */
	protected Texture mainTexture;
	protected Texture lensDirtTexture;
	protected Texture lensStarTexture;
	protected Matrix3 lensStarMatrix;

	public LensFlareComposerShader () {
		super();

		u_mainTexture = program.fetchUniformLocation("u_mainTexture", true);
		u_lensDirtTexture = program.fetchUniformLocation("u_lensDirtTexture", true);
		u_lensStarTexture = program.fetchUniformLocation("u_lensStarTexture", true);
		u_lensStarMatrix = program.fetchUniformLocation("u_lensStarMatrix", true);

		this.lensDirtTexture = new Texture(
			Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensdirt.png"));
		this.lensStarTexture = new Texture(
			Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensstar.png"));
		this.lensStarMatrix = new Matrix3();
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/lensflare_composer/lensflare_composer.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		program.setUniformi(u_mainTexture, context.textureBinder.bind(component.getSystem().getMainTexture()));
		program.setUniformi(u_lensDirtTexture, context.textureBinder.bind(lensDirtTexture));
		program.setUniformi(u_lensStarTexture, context.textureBinder.bind(lensStarTexture));
		program.setUniformMatrix(u_lensStarMatrix, lensStarMatrix);
	}
}
