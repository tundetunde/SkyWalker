package com.dualdigital.skywalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class AssetLoader {
    public static Preferences prefs;
    public static BitmapFont font;
    public static Texture background, splash;
    public static Skin menuSkin;
    public static ImageButton.ImageButtonStyle playStyle;
    public static Model shortBoxModel, longBoxModel, mediumBoxModel, superLongBoxModel, playerModel;
    public static AssetManager assets;

    public static void load(){
        prefs = Gdx.app.getPreferences("Sky Walker");
        splash = new Texture("splash.png");
        font = new BitmapFont(Gdx.files.internal("bold_font.fnt"));
        background = new Texture("bg.png");
        menuSkin= new Skin();
        menuSkin.addRegions(new TextureAtlas(Gdx.files.internal("menuButtons.pack")));
        playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = menuSkin.getDrawable("play");
        playStyle.imageDown = menuSkin.getDrawable("playR");

        ModelBuilder modelBuilder = new ModelBuilder();
        shortBoxModel = modelBuilder.createBox(2f, 1f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        mediumBoxModel = modelBuilder.createBox(2f, 1f, 7f,
                new Material(ColorAttribute.createDiffuse(Color.PURPLE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        longBoxModel = modelBuilder.createBox(2f, 1f, 10f,
                new Material(ColorAttribute.createDiffuse(Color.PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        superLongBoxModel = modelBuilder.createBox(2f, 1f, 15f,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        assets = new AssetManager();
        assets.load("ship.obj", Model.class);
        Bullet.init();
        //playerModel = assets.get("ship.obj", Model.class);
    }

    public static void dispose(){
        font.dispose();
        splash.dispose();
        shortBoxModel.dispose();
        mediumBoxModel.dispose();
        longBoxModel.dispose();
        superLongBoxModel.dispose();
        playerModel.dispose();
    }
}
