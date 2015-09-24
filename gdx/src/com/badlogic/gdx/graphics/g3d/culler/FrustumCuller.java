package com.badlogic.gdx.graphics.g3d.culler;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.collision.BoundingBox;

public class FrustumCuller implements Culler {

	protected BoundingBox bb = new BoundingBox();
	protected Camera camera;

	public FrustumCuller(Camera camera) {
		this.camera = camera;
	}

	@Override
	public boolean accept (Node node) {
		if(camera != null && camera.frustum.boundsInFrustum(node.calculateBoundingBox(bb)))
			return true;
		return false;
	}

}
