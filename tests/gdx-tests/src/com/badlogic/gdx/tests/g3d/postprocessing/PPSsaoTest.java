/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.tests.g3d.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.blur.BlurComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.effects.SsaoEffect;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PPSsaoTest extends GdxTest {
	PerspectiveCamera cam;
	FirstPersonEvolvedCameraController inputController;

	Model model;
	ModelInstance instance;
	Environment environment;
	Array<ModelInstance> instances = new Array<ModelInstance>();

	public Model axesModel;
	public ModelInstance axesInstance;

	ModelBatch modelBatch;
	PostProcessingSystem ppSystem;

	SpotLight sl;
	SpotLight sl2;
	SpotLight sl3;
	DirectionalLight dl;
	float radius = 1f;
	Vector3 center = new Vector3(), transformedCenter = new Vector3(), tmpV = new Vector3();

	BlurComponent blur;
	FrameBuffer fb;

	protected ModelBatch cubeBatch;
	protected Cubemap cubemap;
	protected ModelInstance cubeInstance;
	protected PerspectiveCamera camCube;

	SsaoEffect ssaoEffect;

	Stage stage;
	Label kernelSizeLabel;
	Label radiusLabel;
	Label powerLabel;
	Label noiseSizeLabel;
	Label blurSizeLabel;

	Slider kernelSize;
	Slider radiusSlider;
	Slider power;
	Slider noiseSize;
	Slider blurSize;

	@Override
	public void create () {
		modelBatch = new ModelBatch();
		environment = new Environment();

		cubeBatch = new ModelBatch(Gdx.files.internal("data/shaders/cubemap-vert.glsl"),
			Gdx.files.internal("data/shaders/cubemap-frag.glsl"));

		cubemap = new Cubemap(Gdx.files.internal("data/g3d/environment/nissibeach_PX.jpg"),
			Gdx.files.internal("data/g3d/environment/nissibeach_NX.jpg"),
			Gdx.files.internal("data/g3d/environment/nissibeach_PY.jpg"),
			Gdx.files.internal("data/g3d/environment/nissibeach_NY.jpg"),
			Gdx.files.internal("data/g3d/environment/nissibeach_PZ.jpg"),
			Gdx.files.internal("data/g3d/environment/nissibeach_NZ.jpg"));

		ModelBuilder builder = new ModelBuilder();
		Model cubeModel = builder.createBox(100f, 100f, 100f, new Material(new CubemapAttribute(CubemapAttribute.EnvironmentMap,
			cubemap)), VertexAttributes.Usage.Position);
		cubeInstance = new ModelInstance(cubeModel);

		camCube = new PerspectiveCamera(67, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);
		camCube.position.set(0f, 2f, 2f);
		camCube.lookAt(0, 0, 0);
		camCube.near = 1f;
		camCube.far = 300f;
		camCube.update();

		sl = new SpotLight().setPosition(0, 10, -6).setColor(0.8f, 0.3f, 0.3f, 1).setDirection(0, -0.57346237f, 0.8192319f)
			.setIntensity(20).setCutoffAngle(60).setExponent(60);

		sl2 = new SpotLight().setPosition(0, 7, 5).setColor(0.3f, 0.8f, 0.3f, 1).setDirection(new Vector3(0, -1f, -0.06f).nor())
			.setIntensity(20).setCutoffAngle(60).setExponent(60);

		sl3 = new SpotLight().setPosition(0, 9, 6).setColor(0.3f, 0.3f, 0.8f, 1).setDirection(new Vector3(0, -1f, -0.06f).nor())
			.setIntensity(20).setCutoffAngle(60).setExponent(60);

		dl = new DirectionalLight().setColor(0.5f, 0.5f, 0.5f, 1).setDirection(0, -1f, 0);

		environment.add(sl);
		environment.add(sl2);
		environment.add(sl3);
		environment.add(dl);

		// The user camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 0f);
		cam.lookAt(1, 0, 0);
		cam.near = 1f;
		cam.far = 110f;
		cam.up.set(0, 1, 0);
		cam.update();

		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder mpb = modelBuilder.part("ground", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.ColorUnpacked,
			new Material(ColorAttribute.createDiffuse(Color.RED)));
		mpb.box(0, -1.5f, 0, 10, 1, 10);

		mpb = modelBuilder.part("ball", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.ColorUnpacked, new Material(
			ColorAttribute.createDiffuse(Color.BLUE)));
		mpb.sphere(2f, 2f, 2f, 20, 20);

		model = modelBuilder.end();
		instance = new ModelInstance(model);
		instances.add(instance);

		Array<ModelInstance> instances = new Array<ModelInstance>();
		instances.add(instance);

		createAxes();

		ppSystem = new PostProcessingSystem();

		initStage();

		ssaoEffect = new SsaoEffect().setBlurSize((int)blurSize.getValue()).setKernelSize((int)kernelSize.getValue())
			.setNoiseSize((int)noiseSize.getValue()).setPower(power.getValue()).setRadius(radiusSlider.getValue());

		ppSystem.addEffect(ssaoEffect);

		inputController = new FirstPersonEvolvedCameraController(cam);
		CameraInputController i2 = new CameraInputController(cam);
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, i2));
	}

	private void initStage () {
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage(new ScreenViewport());

		Table table = new Table();
		table.setFillParent(true);
		// table.setDebug(true);
		stage.addActor(table);

		table.right();

		// Sliders
		float initBlurSize = 4;
		blurSize = new Slider(0, 10, 1f, false, skin);
		blurSize.setValue(initBlurSize);
		blurSizeLabel = new Label(String.valueOf(initBlurSize), skin);
		table.add(new Label("Blur size", skin));
		table.row();
		table.add(blurSize);
		table.add(blurSizeLabel);
		table.row();

		float initNoiseSize = 4;
		noiseSize = new Slider(0, 20, 1f, false, skin);
		noiseSize.setValue(initNoiseSize);
		noiseSizeLabel = new Label(String.valueOf(initNoiseSize), skin);
		table.add(new Label("Noise size", skin));
		table.row();
		table.add(noiseSize);
		table.add(noiseSizeLabel);
		table.row();

		float initKernelSize = 4;
		kernelSize = new Slider(1, 10, 1, false, skin);
		kernelSize.setValue(initKernelSize);
		kernelSizeLabel = new Label(String.valueOf(initKernelSize), skin);
		table.add(new Label("Kernel Size", skin));
		table.row();
		table.add(kernelSize);
		table.add(kernelSizeLabel);
		table.row();

		float initRadius = 1.5f;
		radiusSlider = new Slider(0, 5, 0.5f, false, skin);
		radiusSlider.setValue(initRadius);
		radiusLabel = new Label(String.valueOf(initRadius), skin);
		table.add(new Label("Radius", skin));
		table.row();
		table.add(radiusSlider);
		table.add(radiusLabel);
		table.row();

		float initPower = 2f;
		power = new Slider(0, 6, 1f, false, skin);
		power.setValue(initPower);
		powerLabel = new Label(String.valueOf(initPower), skin);
		table.add(new Label("Power", skin));
		table.row();
		table.add(power);
		table.add(powerLabel);
		table.row();

		// Event
		blurSize.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float val = ((Slider)actor).getValue();
				val = Math.round(val * 100) / (float)100;
				ssaoEffect.setBlurSize((int)val);
				blurSizeLabel.setText(Float.toString(val));
			}
		});

		noiseSize.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float val = ((Slider)actor).getValue();
				val = Math.round(val * 100) / (float)100;
				ssaoEffect.setNoiseSize((int)val);
				noiseSizeLabel.setText(Float.toString(val));
			}
		});

		power.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float val = ((Slider)actor).getValue();
				val = Math.round(val * 100) / (float)100;
				ssaoEffect.setPower(val);
				powerLabel.setText(Float.toString(val));
			}
		});

		kernelSize.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float val = ((Slider)actor).getValue();
				val = Math.round(val * 100) / (float)100;
				ssaoEffect.setKernelSize((int)val);
				kernelSizeLabel.setText(Float.toString(val));
			}
		});

		radiusSlider.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				float val = ((Slider)actor).getValue();
				val = Math.round(val * 100) / (float)100;
				ssaoEffect.setRadius(val);
				radiusLabel.setText(Float.toString(val));
			}
		});
	}

	private void createAxes () {
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder builder = modelBuilder.part("axes", GL20.GL_LINES, Usage.Position | Usage.ColorUnpacked, new Material());

		float v1 = 0, v2 = 0, v3 = 100;
		// RED = X
		builder.setColor(Color.RED);
		builder.line(0, 0, 0, v1, v2, v3);

		// GREEN = Y
		builder.setColor(Color.GREEN);
		builder.line(0, 0, 0, v3, v1, v2);

		// BLUE = Z
		builder.setColor(Color.BLUE);
		builder.line(0, 0, 0, v2, v3, v1);

		axesModel = modelBuilder.end();
		axesInstance = new ModelInstance(axesModel);
	}

	long lastTime;

	@Override
	public void render () {
		final float delta = Gdx.graphics.getDeltaTime();
		inputController.update(delta);

		/*
		 * sl.position.rotate(Vector3.Y, -delta * 20f); sl.position.rotate(Vector3.X, -delta * 30f); sl.position.rotate(Vector3.Z,
		 * -delta * 10f); sl.direction.set(Vector3.Zero.cpy().sub(sl.position));
		 * 
		 * sl2.position.rotate(Vector3.Y, delta * 10f); sl2.position.rotate(Vector3.X, delta * 20f); sl2.position.rotate(Vector3.Z,
		 * delta * 30f); sl2.direction.set(Vector3.Zero.cpy().sub(sl2.position));
		 * 
		 * sl3.position.rotate(Vector3.Y, delta * 30f); sl3.position.rotate(Vector3.X, delta * 10f); sl3.position.rotate(Vector3.Z,
		 * delta * 20f); sl3.direction.set(Vector3.Zero.cpy().sub(sl3.position));
		 * 
		 * dl.direction.rotate(Vector3.X, delta * 10f);
		 */

		ppSystem.begin();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		// cubeBatch.begin(cam);
		// cubeBatch.render(cubeInstance);
		// cubeBatch.end();
		modelBatch.begin(cam);
		modelBatch.render(axesInstance);
		modelBatch.render(instances, environment);
		modelBatch.end();
		ppSystem.end();

		ppSystem.render(cam, instances, environment);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose () {
		model.dispose();
	}

	public boolean needsGL20 () {
		return true;
	}

	@Override
	public void resume () {
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}
}
