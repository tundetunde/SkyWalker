package com.dualdigital.skywalker.gameobjects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxBoxCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.dualdigital.skywalker.PlayGame;

/**
 * Created by tunde_000 on 24/03/2016.
 */
public class PlayerModel extends GameObject {
    public PlayerModel(Model model, Vector3 position, Vector3 velocity) {
        super(model, position, velocity);
        initializeCollisionObjects();
    }

    @Override
    public void update(float dt) {
        position.y -= 0.1;
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
        collisionObject.setWorldTransform(modelInstance.transform);
    }

    public void update(float dt, boolean collision) {
        if(!collision)
            position.y -= 3 * dt;
        position.z += 10 * dt;
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
        collisionObject.setWorldTransform(modelInstance.transform);
    }

    private void initializeCollisionObjects(){
        //collisionShape = new btBoxShape(new Vector3(position.x / 2, position.y / 2, position.z / 2));
        System.out.println("Player Width: " + width);
        System.out.println("Player height: " + height);
        System.out.println("Player depth: " + depth);
        collisionShape = new btBoxShape(new Vector3(width / 2, height / 2, depth / 2));
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(collisionShape);
        collisionObject.setWorldTransform(modelInstance.transform);
    }

    public void moveRight() {
        System.out.println("MOVE RIGHT");
        position.x += 3;
    }

    public void moveLeft() {
        System.out.println("MOVE LEFT");
        position.x -= 3;
    }
}
