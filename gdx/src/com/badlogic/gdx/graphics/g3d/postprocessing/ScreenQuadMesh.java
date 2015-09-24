
package com.badlogic.gdx.graphics.g3d.postprocessing;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/** To compute texture coordinate in the shader texcoord = position * 0.5 + 0.5
 * @author realitix */
public class ScreenQuadMesh implements RenderableProvider {
	protected Mesh mesh;
	protected Material material = new Material();
	protected Shader shader;

	public ScreenQuadMesh () {
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[] {-1, -1, 1, -1, -1, 1, 1, 1});
	}

	public void setShader (Shader shader) {
		this.shader = shader;
	}

	@Override
	public void getRenderables (Array<Renderable> renderables, Pool<Renderable> pool) {
		Renderable r = pool.obtain();
		r.mesh = mesh;
		r.meshPartOffset = 0;
		r.meshPartSize = mesh.getNumVertices();
		r.primitiveType = GL20.GL_TRIANGLE_STRIP;
		r.material = material;
		r.bones = null;
		r.userData = null;
		r.shader = shader;
		renderables.add(r);
	}

}
