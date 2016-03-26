package com.dualdigital.skywalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class SplashScreen extends State{
    private SpriteBatch batch;

    OrthographicCamera camera;
    Texture bgTexture;
    Sprite bgSprite;

    protected SplashScreen(GameStateManager gcm) {
        super(gcm);
        AssetLoader.load();
        camera = new OrthographicCamera();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        bgTexture = AssetLoader.splash;
        batch = new SpriteBatch();
        bgSprite = new Sprite(bgTexture);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, SkyWalker.WIDTH / 2, SkyWalker.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgTexture, 0, 0, SkyWalker.WIDTH / 2, SkyWalker.HEIGHT / 2);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
