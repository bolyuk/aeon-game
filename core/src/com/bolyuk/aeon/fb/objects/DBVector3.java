package com.bolyuk.aeon.fb.objects;

import com.badlogic.gdx.math.Vector3;

public class DBVector3 {
    public Float x;
    public Float y;
    public Float z;

    public DBVector3(){}

    public DBVector3(Vector3 v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3 toVector3(){
        return new Vector3(x,y,z);
    }
}
