
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.blur.BlurComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample.DownSampleComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare.LensFlareComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.lensflare_composer.LensFlareComposerComponent;

public class SsaoEffect extends BasePostProcessingEffect {
	protected DownSampleComponent downSampleComponent;
	protected LensFlareComponent lensFlareComponent;
	protected BlurComponent blurComponent;
	protected LensFlareComposerComponent lensFlareComposerComponent;

	public SsaoEffect (float frameBufferScale, float scale, float bias, int samples, float dispersal, float haloWidth,
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

	public SsaoEffect setFrameBufferScale (float scale) {
		downSampleComponent.setFrameBufferScale(scale);
		return this;
	}

	public SsaoEffect setScale (float scale) {
		downSampleComponent.setScale(scale);
		return this;
	}

	public SsaoEffect setBias (float bias) {
		downSampleComponent.setBias(bias);
		return this;
	}

	public SsaoEffect setSamples (int samples) {
		lensFlareComponent.setSamples(samples);
		return this;
	}

	public SsaoEffect setDispersal (float dispersal) {
		lensFlareComponent.setDispersal(dispersal);
		return this;
	}

	public SsaoEffect setHaloWidth (float haloWidth) {
		lensFlareComponent.setHaloWidth(haloWidth);
		return this;
	}

	public SsaoEffect setDistortion (float distortion) {
		lensFlareComponent.setDistortion(distortion);
		return this;
	}

	public SsaoEffect setRadius (int radius) {
		blurComponent.setRadius(radius);
		return this;
	}

	public SsaoEffect setFlareOnly (boolean flareOnly) {
		lensFlareComposerComponent.setFlareOnly(flareOnly);
		return this;
	}
}
