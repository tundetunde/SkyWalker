package com.dualdigital.skywalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.utils.Array;
import com.dualdigital.skywalker.gameobjects.Block;
import com.dualdigital.skywalker.gameobjects.GameObject;
import com.dualdigital.skywalker.gameobjects.PlayerModel;
import com.dualdigital.skywalker.gameobjects.Ring;

import java.util.ArrayList;

/**
 * Created by tunde_000 on 20/03/2016.
 */
public class PlayGame extends State implements GestureDetector.GestureListener{
    public static btCollisionConfiguration collisionConfig;
    public static btDispatcher dispatcher;
    public PerspectiveCamera cam;
    int cameraWidth = SkyWalker.WIDTH / 2;
    int cameraHeight = SkyWalker.HEIGHT / 2;
    public ModelBatch modelBatch;
    private Environment environment;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private PlayerModel playerModel;
    private ArrayList<Ring> rings = new ArrayList<Ring>();
    public ArrayList<ModelInstance> instances = new ArrayList<ModelInstance>();
    boolean loading;
    boolean startingRings = false;
    private boolean collision;
    public static long score = 0;

    protected PlayGame(GameStateManager gcm) {
        super(gcm);
        modelBatch = new ModelBatch();
        loading = true;
        cam = new PerspectiveCamera(67, cameraWidth, cameraHeight);
        cam.position.set(0f, 7f, -25f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        collision = false;
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        createBlocks();
        hideAd();
    }

    public void hideAd(){
        //GdxAppodeal.getInstance().hide(GdxAppodeal.BANNER_BOTTOM);
    }

    @Override
    protected void handleInput() {
        Gdx.input.setInputProcessor(new GestureDetector(0.0f, 0.0f, 0.0f, 5f, this));
    }



    @Override
    public void update(float dt) {
        if(!startingRings)
            startingRings = generateStartingRings();
        handleInput();
        if(playerModel != null && playerModel.getPosition().z > -25f){
            cam.position.add(0, 0, 20 * dt);
            if(isGameOver())
                gcm.set(new EndGame(gcm));
        }

        cam.update();
        ArrayList<Block> tempblocks;
        ArrayList<Ring> temprings;
        tempblocks = (ArrayList<Block>) blocks.clone();
        temprings = (ArrayList<Ring>) rings.clone();
        ArrayList<ModelInstance> tempinstances = new ArrayList<ModelInstance>();

        if(playerModel != null && tempinstances.isEmpty())
            tempinstances.add(playerModel.getModel());

        if(!blocks.isEmpty()){
            if(isVisible(cam, blocks.get(0)) == false){
                blocks.get(0).setToDead();
            }
        }

        if(!rings.isEmpty()){
            if(isVisible(cam, rings.get(0)) == false){
                rings.get(0).setToDead();
            }
        }

        for(Block block: blocks){
            if(playerModel != null && block.isDead() == false){
                if(collision = GameObject.checkCollision(playerModel.getCollisionObject(), block.getCollisionObject()));{
                    playerModel.update(dt, collision);
                    break;
                }
            }
        }

        if(blocks.get(0).isDead()){
            Block b = generateBlock();
            tempblocks.add(b);
            tempblocks.remove(blocks.get(0));
        }

        if(!rings.isEmpty()){
            if(rings.get(0).isDead()){
                Ring ring = new Ring(AssetLoader.rings, new Vector3(blocks.get(blocks.size() - 1).getPosition().x, 1.3f, blocks.get(blocks.size() - 1).getPosition().z), new Vector3(0,0,0));
                temprings.add(ring);
                temprings.remove(rings.get(0));
            }
        }


        for(Block block: tempblocks){
            tempinstances.add(block.getModel());
        }

        for(Ring ring: rings){
            tempinstances.add(ring.getModel());
        }

        instances = tempinstances;
        blocks = tempblocks;
        rings = temprings;
    }

    private Block generateBlock() {
        float h = blocks.get(blocks.size() - 1).getPosition().z;
        return Block.createBlock(h);
    }

    @Override
    public void render(SpriteBatch sb) {
        if(loading && AssetLoader.assets.update()){
            AssetLoader.playerModel = AssetLoader.assets.get("ship.obj", Model.class);
            AssetLoader.rings = AssetLoader.assets.get("ring/untitled.obj", Model.class);
            playerModel = new PlayerModel(AssetLoader.playerModel, new Vector3(blocks.get(0).getPosition().x, 10, blocks.get(0).getPosition().z), new Vector3());
            instances.add(playerModel.getModel());
            loading = false;
        }
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        blocks.clear();
        collisionConfig.dispose();
        dispatcher.dispose();
    }

    private boolean isGameOver(){
        if(playerModel.getPosition().y == -1)
            return true;
        return false;
    }

    private void createBlocks(){
        for(int i = 0; i < 10; i++){
            Block block;
            if(!blocks.isEmpty()){
                block = Block.createBlock(blocks.get(blocks.size() - 1).getPosition().z);
                instances.add(block.getModel());
                blocks.add(block);
            }else{
                block = Block.createBlock(-27);
                instances.add(block.getModel());
                blocks.add(block);
            }
        }
    }

    private boolean generateStartingRings(){
        if(!loading){
            if(!blocks.isEmpty()){
                for(Block b: blocks){
                    Ring r = new Ring(AssetLoader.rings, new Vector3(b.getPosition().x,1.3f,b.getPosition().z), new Vector3(0,0,0));
                    rings.add(r);
                    instances.add(r.getModel());
                }
                return true;
            }
        }
        return false;
    }

    protected boolean isVisible(final Camera cam, final GameObject instance) {
        if(cam.position.z > instance.getPosition().z)
            return false;
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("MOVING");
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if (velocityX > 0)
                playerModel.moveRight();
            else
                playerModel.moveLeft();
        }

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
