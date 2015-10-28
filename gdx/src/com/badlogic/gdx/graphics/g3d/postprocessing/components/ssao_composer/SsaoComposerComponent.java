
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao_composer;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class SsaoComposerComponent extends QuadComponent<SsaoComposerShader> {
	@Override
	protected SsaoComposerShader getShader () {
		return new SsaoComposerShader();
	}

	public SsaoComposerComponent setBlurSize (int blurSize) {
		this.shader.setBlurSize(blurSize);
		return this;
	}
}
