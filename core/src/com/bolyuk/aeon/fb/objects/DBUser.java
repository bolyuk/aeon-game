package com.bolyuk.aeon.fb.objects;

import com.badlogic.gdx.math.Vector2;
import com.bolyuk.aeon.TileObject;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.fb.Auth;

public class DBUser {
    public DBVector3 position;
    public Boolean online;
    public String message;
    public String uId;
    public String texture;

    public DBUser(){}

    public DBUser setMessage(String text){
        message=text;
        return this;
    }

    public DBUser setOnline(boolean value){
        online=value;
        return this;
    }

    public DBUser setPosition(DBVector3 v){
        this.position=v;
        return this;
    }

    public DBUser(TileObject user){
        position = new DBVector3(user.getPosition());
        message = user.getMessage();
        uId = Auth.getUid();
        texture = user.getTextureName();
        online = true;
    }

    public TileObject toTileObject(){
        if(texture == null)
            texture = "player_texture";

        return new TileObject(texture).
                position(position.toVector3()).
                message(message);
    }
}
