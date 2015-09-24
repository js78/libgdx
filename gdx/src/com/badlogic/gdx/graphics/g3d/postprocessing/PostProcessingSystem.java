
package com.badlogic.gdx.graphics.g3d.postprocessing;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.postprocessing.effects.Effect;
import com.badlogic.gdx.graphics.g3d.postprocessing.providers.Provider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class PostProcessingSystem {
	protected ObjectMap<String, Provider> cachedProviders = new ObjectMap<String, Provider>();
	protected Array<String> providers = new Array<String>();
	protected Array<Effect> effects = new Array<Effect>();
	protected ModelBatch mainModelBatch;
	int currentProvider;

	public void addEffect (Effect effect) {
		effects.add(effect);
		resetProviders();
	}

	public void removeEffect (Effect effect) {
		effects.removeValue(effect, true);
		resetProviders();
	}

	protected Provider getProvider (int i) {
		return cachedProviders.get(providers.get(i));
	}

	/*
	 * public int getPassQuantity () { int result = 0; for (String provider : providers) { result +=
	 * cachedProviders.get(provider).getPassQuantity(); } return result; }
	 */

	public void begin (ModelBatch modelBatch) {
		// if (providers.size <= i) {
		// throw new GdxRuntimeException("Only " + providers.size + " providers in PostProcessingSystem");
		// }
		// currentProvider = i;
		// cachedProviders.get(providers.get(i)).begin();
		currentProvider = 0;
		mainModelBatch = modelBatch;
	};

	public void end () {
		// if (i != currentProvider) {
		// throw new GdxRuntimeException("Call begin before end");
		// }
		// cachedProviders.get(providers.get(i)).begin();
	};

	protected void resetProviders () {
		providers.clear();
		for (Effect effect : effects) {
			for (String p : effect.getProviders()) {
				if (!providers.contains(p, false)) {
					providers.add(p);
				}
			}
		}

		for (String p : providers) {
			if (!cachedProviders.containsKey(p)) {
				Provider newProvider = null;
				try {
					newProvider = (Provider)Class.forName(p).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				cachedProviders.put(p, newProvider);
			}
		}
	}

	public ModelBatch next () {
		if (currentProvider >= providers.size) {
			return null;
		}
		Provider provider = getProvider(currentProvider);
		currentProvider++;
		return provider.getModelBatch();
	};

	public ModelBatch getMainModelBatch () {
		return mainModelBatch;
	}

	public void render () {

	}
}
