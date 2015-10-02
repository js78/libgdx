
package com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class LensFlareComposerComponent extends QuadComponent {

	@Override
	protected QuadShader getShader () {
		return new LensFlareComposerShader();
	}
}
