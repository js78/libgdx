
package com.badlogic.gdx.graphics.g3d.postprocessing.components.normaldepth;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.SceneComponent;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;

public class NormalDepthComponent extends SceneComponent {
	@Override
	protected ShaderProvider getShaderProvider () {
		return new NormalDepthShaderProvider();
	}
}
