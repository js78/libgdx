
package com.badlogic.gdx.graphics.g3d.postprocessing.components.blur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BlurShader implements Shader {
	protected Mesh mesh;
	protected ShaderProgram program;

	protected int u_input;

	protected RenderContext context;

	public BlurShader () {
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[] {-1, -1, 1, -1, -1, 1, 1, 1});
		program = new ShaderProgram(Gdx.files.classpath(
			"com/badlogic/gdx/graphics/g3d/postprocessing/components/blur/blur.vertex.glsl").readString(), Gdx.files.classpath(
				"com/badlogic/gdx/graphics/g3d/postprocessing/components/blur/blur.fragment.glsl").readString());
		if (!program.isCompiled()) throw new GdxRuntimeException(program.getLog());

		u_input = program.fetchUniformLocation("uInputTex", true);
		context = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.ROUNDROBIN));
	}

	@Override
	public void dispose () {
		mesh.dispose();
		program.dispose();
	}

	@Override
	public void init () {

	}

	@Override
	public int compareTo (Shader other) {
		return 0;
	}

	@Override
	public boolean canRender (Renderable instance) {
		return true;
	}

	@Override
	public void begin (Camera camera, RenderContext context) {
	}

	@Override
	public void render (Renderable renderable) {
	}

	public void render (Texture texture) {
		context.begin();
		program.begin();
		program.setUniformi(u_input, context.textureBinder.bind(texture));

		mesh.render(program, GL20.GL_TRIANGLE_STRIP, 0, mesh.getNumVertices(), true);
		program.end();
		context.end();
	}

	@Override
	public void end () {
	}

}
