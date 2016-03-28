package com.dualdigital.skywalker.gameobjects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.dualdigital.skywalker.PlayGame;

/**
 * Created by tunde_000 on 28/03/2016.
 */
public class Ring extends Pickups {
    public Ring(Model model, Vector3 position, Vector3 velocity) {
        super(model, position, velocity);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void setPickedUp(boolean pickedUp) {
        if(pickedUp)
            setToDead();
        PlayGame.score++;
    }
}
