
package com.badlogic.gdx.graphics.g3d.postprocessing.components.blur;

import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadShader;
import com.badlogic.gdx.math.Vector2;

public class BlurShader extends QuadShader {
	/** Uniforms */
	protected int u_radius;
	protected int u_direction;

	/** Parameters */
	protected int radius;
	protected Vector2 direction = new Vector2();

	public BlurShader () {
		this(16, 1, 1);
	}

	public BlurShader (int radius, float directionX, float directionY) {
		super();
		u_radius = program.fetchUniformLocation("u_radius", true);
		u_direction = program.fetchUniformLocation("u_direction", true);
		this.radius = radius;
		this.direction.set(directionX, directionY);
	}

	@Override
	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/blur/blur.fragment.glsl";
	}

	@Override
	protected void setUniforms () {
		program.setUniformi(u_radius, radius);
		program.setUniformf(u_direction, direction);
	}

	public void setRadius (int radius) {
		this.radius = radius;
	}

	public void setDirection (float x, float y) {
		this.direction.set(x, y);
	}
}
