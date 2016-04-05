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
import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class AssetLoader {
    public static Preferences prefs;
    public static BitmapFont font;
    public static Texture background, splash;
    public static Skin menuSkin;
    public static ImageButton.ImageButtonStyle playStyle,scoreStyle, rateStyle,soundStyle, shareStyle;
    public static Model shortBoxModel, longBoxModel, mediumBoxModel, superLongBoxModel, playerModel, rings;
    public static AssetManager assets;
    public static boolean modelsLoaded;
    private static String appKey = "c76cf67af790f35425d9ba82ed61adce7f59305797ebb995";

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
        rateStyle = new ImageButton.ImageButtonStyle();
        rateStyle.imageUp = menuSkin.getDrawable("rate");
        rateStyle.imageDown = menuSkin.getDrawable("rateR");
        scoreStyle = new ImageButton.ImageButtonStyle();
        scoreStyle.imageUp = menuSkin.getDrawable("score");
        scoreStyle.imageDown = menuSkin.getDrawable("scoreR");
        soundStyle = new ImageButton.ImageButtonStyle();
        soundStyle.imageUp = menuSkin.getDrawable("sound");
        shareStyle = new ImageButton.ImageButtonStyle();
        shareStyle.imageUp = menuSkin.getDrawable("share");
        shareStyle.imageDown = menuSkin.getDrawable("shareR");

        ModelBuilder modelBuilder = new ModelBuilder();
        shortBoxModel = modelBuilder.createBox(2f, 1f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.VIOLET)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        mediumBoxModel = modelBuilder.createBox(2f, 1f, 7f,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        longBoxModel = modelBuilder.createBox(2f, 1f, 10f,
                new Material(ColorAttribute.createDiffuse(Color.PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        superLongBoxModel = modelBuilder.createBox(2f, 1f, 15f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        assets = new AssetManager();
        assets.load("ship.obj", Model.class);
        assets.load("ring/untitled.obj", Model.class);
        modelsLoaded = AssetLoader.assets.update();
        Bullet.init();

        //playerModel = assets.get("ship.obj", Model.class);
    }

    public static String getAppKey(){
        return appKey;
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
