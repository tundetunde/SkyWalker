package com.dualdigital.skywalker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class SkyWalker extends ApplicationAdapter {
	SpriteBatch batch;
	public static final int WIDTH = 1440;
	public static final int HEIGHT = 2400;
	private static GameStateManager gsm;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new SplashScreen(gsm));
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						gsm.push(new Menu(gsm));
					}
				}, 4);
			}
		}).start();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause() {
		super.pause();
		if(isGameOn())
			gsm.push(new PauseScreen(gsm));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

	public static boolean isGameOn(){
		if(gsm.top() instanceof PlayGame)
			return true;
		return false;
	}
}
