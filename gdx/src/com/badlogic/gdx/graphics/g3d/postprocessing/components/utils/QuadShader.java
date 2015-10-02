
package com.badlogic.gdx.graphics.g3d.postprocessing.components.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class QuadShader implements Disposable {
	protected Mesh mesh;
	protected ShaderProgram program;
	protected RenderContext context;
	protected int u_texture;

	public QuadShader () {
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[] {-1, -1, 1, -1, -1, 1, 1, 1});
		program = new ShaderProgram(Gdx.files.classpath(getVertex()).readString(), Gdx.files.classpath(getFragment()).readString());
		if (!program.isCompiled()) throw new GdxRuntimeException(program.getLog());
		context = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.ROUNDROBIN));

		u_texture = program.fetchUniformLocation("u_texture", false);
	}

	protected String getVertex () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/utils/quad.vertex.glsl";
	};

	protected String getFragment () {
		return "com/badlogic/gdx/graphics/g3d/postprocessing/components/utils/quad.fragment.glsl";
	};

	@Override
	public void dispose () {
		mesh.dispose();
		program.dispose();
	}

	protected void setUniforms () {
	}

	public void render (Texture texture) {
		context.begin();
		program.begin();
		program.setUniformi(u_texture, context.textureBinder.bind(texture));
		setUniforms();
		mesh.render(program, GL20.GL_TRIANGLE_STRIP, 0, mesh.getNumVertices(), true);
		program.end();
		context.end();
	}
}
