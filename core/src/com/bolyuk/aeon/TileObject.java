package com.bolyuk.aeon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.constants.Styles;

public class TileObject extends TileBlock {
     protected Vector3 position = new Vector3();
     protected Vector3 delta_position = new Vector3();

     protected String message=null;
     protected float message_offset=0;

     protected String texture_name;

    public float rendered_x, rendered_y;

    public TileObject(String texture){
        super(texture);
        texture_name = texture;
        offset.x=(C.tile_width/2f)-(getTexture().getWidth()/2f);
        offset.y=(C.tile_height+C.tile_z_offset);

    }

    public TileObject position(Vector3 v){
        position=v;


        delta_position.x= position.x-(int)position.x;
        delta_position.y= position.y-(int)position.y;
        delta_position.z= position.z-(int)position.z;
        return this;
    }

    public TileObject message(String text){
        message=text;
        return this;
    }

    public float width(){
        return texture.getWidth();
    }

    public float height(){
        return texture.getHeight();
    }

    @Override
    public void draw(Batch b, float x, float y) {
        float start_x = x;
        float start_y = y;

        rendered_x=x;
        rendered_y=y;

        start_x-=((xDelta()-yDelta())*C.tile_width)/2f;
        start_y-=((xDelta()+yDelta())*C.tile_width)/4f+zDelta()*C.tile_z_offset;

        super.draw(b, start_x, start_y);
        if(message != null)
           message_offset = Styles.chatFont.draw(b, message, start_x-message_offset,start_y+ texture.getHeight()+11f).width/2f;
    }

    public float x(){
        return position.x;
    }

    public float y(){
        return position.y;
    }

    public float z(){
        return position.z;
    }

    public float xDelta() { return delta_position.x;}

    public float yDelta() { return delta_position.y;}

    public float zDelta() { return delta_position.z;}

    public int xInt(){
        return  (int)position.x;
    }

    public int yInt(){
        return (int)position.y;
    }

    public int zInt(){
        return (int)position.z;
    }

    public Vector3 getIntPosition(){
        return new Vector3((int)position.x, (int)position.y, (int)position.z);
    }

    public Vector3 getPosition(){
        return position;
    }

    public String getMessage(){
        return message;
    }



    public String getTextureName() { return texture_name; }
}
