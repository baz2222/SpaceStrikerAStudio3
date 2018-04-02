package com.juniorgames.spacestriker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
//import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
//import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class SpaceStriker extends ApplicationAdapter implements InputProcessor {
	private PerspectiveCamera cam;
	private Model model;
	private ModelInstance modelInstance;
	private ModelBatch modelBatch;
	private Environment environment;
	private CameraInputController camController;
	//AnimationController animController;
	private AssetManager assets;
	private boolean loading;
	InputMultiplexer multiplexer;
	private float zCoord = 0f;
	boolean moving = false;
	@Override
	public void create () {
		assets = new AssetManager();
		assets.load("1.g3db",Model.class);
		loading = true;
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1f));
		environment.add(new DirectionalLight().set(0.8f,0.8f,0.8f,-1f,-0.8f,-0.2f));
		cam = new PerspectiveCamera(17, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.position.set(05f,0f,25f);
		cam.lookAt(0f,0f,0f);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		multiplexer = new InputMultiplexer();
		camController = new CameraInputController(cam);
		multiplexer.addProcessor(camController);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}
	private void doneLoading(){
		model = assets.get("1.g3db",Model.class);
		modelInstance = new ModelInstance(model);
		modelInstance.transform.setToTranslation(0,0,zCoord);
		//animController = new AnimationController(modelInstance);
		//animController.setAnimation("Skelet|Stay",-1);
		loading = false;
	}

	@Override
	public void render () {
		if(loading&&assets.update()){
			doneLoading();
		}
		if(moving){
			zCoord += 0.02;
			modelInstance.transform.setToTranslation(0,0,zCoord);
		}
		camController.update();
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f,0f,0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		if(!loading){
			modelBatch.begin(cam);
			modelBatch.render(modelInstance,environment);
			modelBatch.end();
			//animController.update(Gdx.graphics.getDeltaTime());
		}
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public boolean keyDown(int keycode){
		return false;
	}

	@Override
	public boolean keyUp(int keycode){
		return false;
	}

	@Override
	public boolean keyTyped(char character){
		return false;
	}

	@Override
	public boolean touchDown(int screenX,int screenY,int pointer,int button){
		return false;
	}

	@Override
	public boolean touchUp(int screenX,int screenY,int pointer,int button){
		return false;
	}

	@Override
	public boolean touchDragged(int screenX,int screenY,int pointer){
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX,int screenY){
		return false;
	}
	@Override
	public boolean scrolled(int amount){
		return false;
	}
}
