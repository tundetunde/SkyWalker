package com.dualdigital.skywalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class Menu extends State {
    private Texture background;
    private ImageButton playButton;
    int cameraWidth = SkyWalker.WIDTH / 2;
    int cameraHeight = SkyWalker.HEIGHT / 2;
    private BitmapFont fontTitle;
    Stage stage;

    protected Menu(GameStateManager gcm) {
        super(gcm);
        camera.setToOrtho(false, SkyWalker.WIDTH / 2, SkyWalker.HEIGHT / 2);
        background = AssetLoader.background;
        fontTitle = AssetLoader.font;
        fontTitle.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        initializeButtons();
        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);
    }

    private void initializeButtons(){
        playButton = new ImageButton(AssetLoader.playStyle);
        playButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        String title = "MAIN MENU";
        fontTitle.draw(sb, title, SkyWalker.WIDTH / 4 - (title.length() * 35), (SkyWalker.HEIGHT / 8) * 3 + 30);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
