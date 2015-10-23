
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class LensFlareComposerComponent extends QuadComponent<LensFlareComposerShader> {

	@Override
	protected LensFlareComposerShader getShader () {
		return new LensFlareComposerShader();
	}

	public LensFlareComposerComponent setFlareOnly (boolean flareOnly) {
		this.shader.setFlareOnly(flareOnly);
		return this;
	}
}
