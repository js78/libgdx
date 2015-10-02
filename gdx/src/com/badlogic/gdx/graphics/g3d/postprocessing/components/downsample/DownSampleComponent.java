
package com.badlogic.gdx.graphics.g3d.postprocessing.components.downsample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class DownSampleComponent extends QuadComponent {
	@Override
	protected QuadShader getShader () {
		return new DownSampleShader();
	}

	@Override
	protected FrameBuffer getFrameBuffer () {
		return new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, true);
	}
}
