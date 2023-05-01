package com.bolyuk.aeon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.world.World;

public class TileBlock {
    public Sprite texture;
    public boolean isPlacable=true;
    public boolean isUsable=false;
    public Vector2 offset = new Vector2();

    public TileBlock(){

    }

    public TileBlock setOffset(Vector2 v){
        offset = v;
        return this;
    }

    public TileBlock(String texture){
        this.texture=TextureList.get(texture);
    }

    public Sprite getTexture(){
        return texture;
    }

    public void draw(Batch b,float x, float y){
        b.draw(texture,x+offset.x,y+offset.y);
    }

    public void place(Vector3 point){
        World.setBlock(point, BlockList.blocks.findKey(this, false));
    }

    public void remove(Vector3 point){
      World.delBlock(point);
    }

    public void use(TileObject user){

    }

    public Vector2 getOffset(){
        return offset;
    }
}
