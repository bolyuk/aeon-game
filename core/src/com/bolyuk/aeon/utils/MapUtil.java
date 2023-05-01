package com.bolyuk.aeon.utils;

import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.world.World;

public class MapUtil {

    public static Vector3 chunkFromPoint(Vector3 v){
        Vector3 r = new Vector3();
        r.x = (int)(v.x/16);
        r.y = (int)(v.y/16);
        r.z = (int)(v.z/16);
        return r;
    }

    public static String chunkNameFromPoint(Vector3 v){
        return (int)(v.x/16)+":"+(int)(v.y/16)+":"+(int)(v.z/16);
    }

    public static String chunkName(int x, int y, int z){
        return (int)(x/16)+":"+(int)(y/16)+":"+(int)(z/16);
    }

    public static String pointToString(Vector3 v){
        return (int)v.x+":"+(int)v.y+":"+(int)v.z;
    }

    public static Vector3 parsePoint(String point){
        Vector3 r = new Vector3();
        String[] b = point.split(":");
        r.x=Integer.parseInt(b[0]);
        r.y=Integer.parseInt(b[1]);
        r.z=Integer.parseInt(b[2]);
        return r;
    }

    public static Vector3 pointInChunkFromPoint(Vector3 v){
        Vector3 r = new Vector3();
        r.x=((int)v.x%16);
        r.y=((int)v.y%16);
        r.z=((int)v.z%16);
        return r;
    }

    public static int mod(int value, int mod)
    {
        if(value < 0)
            return ((value % mod + mod) % mod);
        else
            return value%mod;
    }
}
