package com.dualdigital.skywalker.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxBoxCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.dualdigital.skywalker.PlayGame;
import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * Created by tunde_000 on 22/03/2016.
 */
public abstract class GameObject {
    protected Vector3 position;
    protected Vector3 velocity;
    protected ModelInstance modelInstance;
    protected boolean dead;
    static int numberOfObjects;
    protected btCollisionShape collisionShape;
    protected btCollisionObject collisionObject;
    protected float width, height, depth;
    protected BoundingBox bb;

    public GameObject(Model model, Vector3 position, Vector3 velocity){
        this.position = position;
        this.velocity = velocity;
        numberOfObjects++;
        dead = false;
        modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
        bb = new BoundingBox();
        model.calculateBoundingBox(bb);
        width = bb.getWidth();
        height = bb.getHeight();
        depth = bb.getDepth();
    }

    public void setModel(Model model){
        modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
    }

    public ModelInstance getModel(){
        return modelInstance;
    }

    public abstract void update(float dt);

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void dispose(){
        collisionObject.dispose();
        collisionShape.dispose();
    }

    public btCollisionObject getCollisionObject(){
        return collisionObject;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getDepth(){
        return depth;
    }

    public static boolean checkCollision(btCollisionObject player , btCollisionObject object){
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(player);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(object);

        btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
        ci.setDispatcher1(PlayGame.dispatcher);
        btCollisionAlgorithm algorithm = new btBoxBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);

        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

        boolean r = result.getPersistentManifold().getNumContacts() > 0;

        result.dispose();
        info.dispose();
        algorithm.dispose();
        ci.dispose();
        co1.dispose();
        co0.dispose();

        return r;
    }

    public boolean isDead(){
        return dead;
    }

    public void setToDead(){
        dead = true;
    }
}
