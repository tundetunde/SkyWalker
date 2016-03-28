package com.dualdigital.skywalker.gameobjects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by tunde_000 on 28/03/2016.
 */
public abstract class Pickups extends GameObject{
    protected boolean pickedUp;

    public Pickups(Model model, Vector3 position, Vector3 velocity) {
        super(model, position, velocity);
        pickedUp = false;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isPicked(){
        return pickedUp;
    }

    @Override
    public void update(float dt) {
        position.z -= 2 * dt;
        modelInstance.transform.setToTranslation(position.x, position.y, position.z);
    }
}
