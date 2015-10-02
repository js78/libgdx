
package com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class DownSampleComponent extends QuadComponent {
	protected float scale;
	protected float bias;

	public DownSampleComponent () {
		this(1, 0);
	}

	public DownSampleComponent (float scale, float bias) {
		this.scale = scale;
		this.bias = bias;
	}

	@Override
	protected QuadShader getShader () {
		return new DownSampleShader(scale, bias);
	}

	@Override
	protected FrameBuffer getFrameBuffer () {
		return new FrameBuffer(Format.RGBA8888, (int)(Gdx.graphics.getWidth() * scale), (int)(Gdx.graphics.getHeight() * scale),
			true);
	}
}
