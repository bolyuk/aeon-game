package com.bolyuk.aeon.world;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.world.World;

public class MapInteract2 implements InputProcessor {
    OrthographicCamera camera;

    public MapInteract2(OrthographicCamera camera){
        this.camera=camera;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!World.isReady())
            return true;

        Vector3 v = new Vector3(screenX, screenY, 0);
        camera.unproject(v);

        float x = v.x;
        float y = v.y;

        float start_x = World.p().rendered_x+(C.tile_width /2);
        float start_y = World.p().rendered_y+(C.tile_width /2);

        Vector2 v1 = new Vector2(start_x-(C.tile_width /2), start_y-(C.tile_width /2)).sub(new Vector2(start_x,start_y));

        Vector2 v2 = new Vector2(x,y).sub(new Vector2(start_x,start_y));

        double alpha = v1.angleRad(v2);

        double c = new Vector2(start_x,start_y).dst(new Vector2(x,y));

        double result_x =World.p().x() +(Math.cos(alpha)*c/(C.tile_width /2));

        double result_y =World.p().y() -(Math.sin(alpha)*c/(C.tile_width /2));

        if(result_x < 0 || result_y < 0)
            return false;

        Vector3 r = new Vector3((int)result_x, (int)result_y, World.p().zInt());

        if(Math.abs(r.dst(World.p().getPosition())) > 5)
            return false;

        World.selected_position = r;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

