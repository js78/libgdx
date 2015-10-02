
package com.badlogic.gdx.graphics.g3d.postprocessing.components.depth;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.SceneComponent;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;

public class DepthComponent extends SceneComponent {
	@Override
	protected ShaderProvider getShaderProvider () {
		return new DepthShaderProvider();
	}
}
