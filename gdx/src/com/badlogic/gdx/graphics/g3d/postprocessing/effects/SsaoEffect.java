
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.normaldepth.NormalDepthComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao.SsaoComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao_composer.SsaoComposerComponent;

public class SsaoEffect extends BasePostProcessingEffect {
	protected NormalDepthComponent normalDepthComponent;
	protected SsaoComponent ssaoComponent;
	protected SsaoComposerComponent ssaoComposerComponent;

	public SsaoEffect () {
		init();
	}

	public SsaoEffect (int kernelSize, float radius, float power, int noiseSize, int blurSize) {
		init();
		ssaoComponent.setKernelSize(kernelSize).setRadius(radius).setPower(power).setNoiseSize(noiseSize);
		ssaoComposerComponent.setBlurSize(blurSize);
	}

	private void init () {
		normalDepthComponent = new NormalDepthComponent();
		ssaoComponent = new SsaoComponent();
		ssaoComposerComponent = new SsaoComposerComponent();

		addComponent(normalDepthComponent);
		addComponent(ssaoComponent);
		addComponent(ssaoComposerComponent);
	}

	public SsaoEffect setKernelSize (int kernelSize) {
		ssaoComponent.setKernelSize(kernelSize);
		return this;
	}

	public SsaoEffect setRadius (float radius) {
		ssaoComponent.setRadius(radius);
		return this;
	}

	public SsaoEffect setPower (float power) {
		ssaoComponent.setPower(power);
		return this;
	}

	public SsaoEffect setNoiseSize (int noiseSize) {
		ssaoComponent.setNoiseSize(noiseSize);
		return this;
	}

	public SsaoEffect setBlurSize (int blurSize) {
		ssaoComposerComponent.setBlurSize(blurSize);
		return this;
	}

}
