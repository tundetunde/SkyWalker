package com.dualdigital.skywalker.gameobjects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxBoxCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereBoxCollisionAlgorithm;
import com.dualdigital.skywalker.AssetLoader;
import com.dualdigital.skywalker.PlayGame;
import com.dualdigital.skywalker.SkyWalker;

import java.util.Random;

/**
 * Created by tunde_000 on 23/03/2016.
 */
public class Block extends GameObject{
    private BlockType blockType;
    private Ring ring;

    public enum BlockType{
        Small,
        Medium,
        Long,
        SuperLong
    }

    public enum Lanes{
        Lane1,
        Lane2,
        Lane3,
        Lane4
    }

    public Block(Model model, Vector3 position, Vector3 velocity, BlockType blockType) {
        super(model, position, velocity);
        this.blockType = blockType;
        initializeCollisionObjects();
    }

    public void initializeRings(){
        ring = new Ring(AssetLoader.rings, new Vector3(position.x, 0f, position.z), new Vector3(0,0,0));
    }

    private void initializeCollisionObjects(){
        collisionShape = new btBoxShape(new Vector3(getWidth() / 2, getHeight() / 2, getDepth() / 2));
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(collisionShape);
        collisionObject.setWorldTransform(modelInstance.transform);
    }

    @Override
    public void update(float dt) {
        position.z -= 2 * dt;
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
        collisionObject.setWorldTransform(modelInstance.transform);
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    public static Block createBlock(float z){
        Lanes L = chooseLane();
        BlockType type = chooseBlockType();
        int width = SkyWalker.WIDTH / 2;
        int height = SkyWalker.HEIGHT / 2;
        float x;
        x = 0;
        z += 15;
        switch (L){
            case Lane1:
                x = -5;
                break;
            case Lane2:
                x = -2;
                break;
            case Lane3:
                x = 0;
                break;
            case Lane4:
                x = 5;
                break;
        }
        Block block = new Block(AssetLoader.shortBoxModel, new Vector3(x, 0 , z), new Vector3(), type);
        block.setModel(AssetLoader.shortBoxModel);
        return block;
    }

    @Override
    public void setModel(Model model) {
        switch (blockType){
            case Small:
                super.setModel(AssetLoader.shortBoxModel);
                break;
            case Medium:
                super.setModel(AssetLoader.mediumBoxModel);
                break;
            case Long:
                super.setModel(AssetLoader.longBoxModel);
                break;
            case SuperLong:
                super.setModel(AssetLoader.superLongBoxModel);
                break;
        }
    }

    public static Lanes chooseLane(){
        Random rand = new Random();
        int x = rand.nextInt(4) + 1;

        switch (x){
            case 1:
                return Lanes.Lane1;
            case 2:
                return Lanes.Lane2;
            case 3:
                return Lanes.Lane3;
            case 4:
                return Lanes.Lane4;
            default:
                return Lanes.Lane4;
        }
    }

    public static BlockType chooseBlockType(){
        Random rand = new Random();
        int x = rand.nextInt(4) + 1;

        switch (x){
            case 1:
                return BlockType.Small;
            case 2:
                return BlockType.Medium;
            case 3:
                return BlockType.Long;
            case 4:
                return BlockType.SuperLong;
            default:
                return BlockType.SuperLong;
        }
    }

    public BlockType getBlockType(){
        return blockType;
    }
}
