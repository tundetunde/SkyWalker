package com.dualdigital.skywalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class EndGame extends State {
    private Texture background;
    private BitmapFont font, scorefont;
    private Label.LabelStyle labelStyle;
    int cameraWidth = SkyWalker.WIDTH / 2;
    int cameraHeight = SkyWalker.HEIGHT / 2;
    Stage stage;
    private ImageButton playButton;


    protected EndGame(GameStateManager gcm) {
        super(gcm);
        background = AssetLoader.background;
        font = AssetLoader.font;
        font.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        stage.addActor(playButton);
        camera.setToOrtho(false, SkyWalker.WIDTH / 2, SkyWalker.HEIGHT / 2);
        initializeButtons();
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
        String title = "GAME OVER";
        font.draw(sb, title, SkyWalker.WIDTH / 4 - (title.length() * 35), (SkyWalker.HEIGHT / 8) * 3 + 30);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
