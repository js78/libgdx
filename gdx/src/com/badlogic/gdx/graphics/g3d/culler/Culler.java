package com.badlogic.gdx.graphics.g3d.culler;

import com.badlogic.gdx.graphics.g3d.model.Node;

public interface Culler {
	public boolean accept(Node node);
}
