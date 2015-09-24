package com.badlogic.gdx.graphics.g3d.culler;

import com.badlogic.gdx.graphics.g3d.model.Node;

public class AcceptAllCuller implements Culler {

	@Override
	public boolean accept (Node node) {
		return true;
	}

}
