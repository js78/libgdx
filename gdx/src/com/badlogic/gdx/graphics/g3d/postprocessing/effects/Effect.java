
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.g3d.postprocessing.providers.Provider;

public interface Effect {
	public String[] getProviders ();

	public void render (Provider... providers);
}
