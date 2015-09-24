
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.g3d.postprocessing.providers.Provider;
import com.badlogic.gdx.graphics.g3d.postprocessing.providers.SceneProvider;

public class NoEffect extends BaseEffect {

	@Override
	protected void initProviders () {
		providers = new String[] {"com.badlogic.gdx.graphics.g3d.postprocessing.providers.SceneProvider"};
	}

	@Override
	public void render (Provider... providers) {
		SceneProvider sceneProvider = (SceneProvider)providers[0];
	}

}
