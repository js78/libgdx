
package com.badlogic.gdx.graphics.g3d.postprocessing.components.blur;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;

public class BlurComponent extends QuadComponent {
	@Override
	protected QuadShader getShader () {
		return new BlurShader();
	}
}
