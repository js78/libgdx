
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.blur.BlurComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample.DownSampleComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare.LensFlareComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer.LensFlareComposerComponent;

public class LensFlareEffect extends BasePostProcessingEffect {
	protected DownSampleComponent downSampleComponent;
	protected LensFlareComponent lensFlareComponent;
	protected BlurComponent blurComponent;
	protected LensFlareComposerComponent lensFlareComposerComponent;

	public LensFlareEffect (float frameBufferScale, float scale, float bias, int samples, float dispersal, float haloWidth,
		float distortion, int radius, boolean flareOnly) {

		downSampleComponent = new DownSampleComponent(frameBufferScale).setScale(scale).setBias(bias);
		lensFlareComponent = new LensFlareComponent().setSamples(samples).setDispersal(dispersal).setHaloWidth(haloWidth)
			.setDistortion(distortion);
		blurComponent = new BlurComponent().setRadius(radius);
		lensFlareComposerComponent = new LensFlareComposerComponent().setFlareOnly(flareOnly);

		addComponent(downSampleComponent);
		addComponent(lensFlareComponent);
		addComponent(blurComponent);
		addComponent(lensFlareComposerComponent);
	}

	public LensFlareEffect setFrameBufferScale (float scale) {
		downSampleComponent.setFrameBufferScale(scale);
		return this;
	}

	public LensFlareEffect setScale (float scale) {
		downSampleComponent.setScale(scale);
		return this;
	}

	public LensFlareEffect setBias (float bias) {
		downSampleComponent.setBias(bias);
		return this;
	}

	public LensFlareEffect setSamples (int samples) {
		lensFlareComponent.setSamples(samples);
		return this;
	}

	public LensFlareEffect setDispersal (float dispersal) {
		lensFlareComponent.setDispersal(dispersal);
		return this;
	}

	public LensFlareEffect setHaloWidth (float haloWidth) {
		lensFlareComponent.setHaloWidth(haloWidth);
		return this;
	}

	public LensFlareEffect setDistortion (float distortion) {
		lensFlareComponent.setDistortion(distortion);
		return this;
	}

	public LensFlareEffect setRadius (int radius) {
		blurComponent.setRadius(radius);
		return this;
	}

	public LensFlareEffect setFlareOnly (boolean flareOnly) {
		lensFlareComposerComponent.setFlareOnly(flareOnly);
		return this;
	}
}
