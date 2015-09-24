
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;


public abstract class BaseEffect implements Effect {
	String[] providers;

	public BaseEffect () {
		initProviders();
	}

	protected abstract void initProviders ();

	@Override
	public String[] getProviders () {
		return providers;
	}
}
