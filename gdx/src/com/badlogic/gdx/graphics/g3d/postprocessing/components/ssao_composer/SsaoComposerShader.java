
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao_composer;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class SsaoComposerShader extends QuadShader {
	/** Uniforms */
	protected int u_mainTexture;
	protected int u_blurSize;

	/** Parameters */
	protected int blurSize;

	public SsaoComposerShader () {
		this(4);
	}

	public SsaoComposerShader (int blurSize) {
		super();

		u_mainTexture = program.fetchUniformLocation("u_mainTexture", true);
		u_blurSize = program.fetchUniformLocation("u_blurSize", true);

		this.blurSize = blurSize;
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/ssao_composer/ssao_composer.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		program.setUniformi(u_mainTexture, context.textureBinder.bind(component.getSystem().getMainTexture()));
		program.setUniformi(u_blurSize, blurSize);
	}

	public void setBlurSize (int blurSize) {
		this.blurSize = blurSize;
		dirty = true;
	}
}
