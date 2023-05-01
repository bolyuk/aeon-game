package com.bolyuk.aeon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bolyuk.aeon.utils.TextureUtil;


public class BlockList {
    public static ObjectMap<String, TileBlock> blocks = new ObjectMap<>();

    public static void init(){
        blocks.put("frog", new TileObject("frog_texture"));
        blocks.put("tree1", new TileObject("tree1_texture"));
        blocks.put("water", new TileBlock("water_texture"));
        blocks.put("grass", new TileBlock("grass_texture"));
        blocks.put("sand1", new TileBlock("sand1_texture"));
        blocks.put("sand2", new TileBlock("sand1_texture"));

        putObj("tree2", "tree2");
        putObj("tree3", "tree3");
        putObj("tree4", "tree4");
        putObj("tree5", "tree5");

        putObj("mushroom1");
        putObj("mushroom2");

        put("flower_grass1");
        put("flower_grass2");
        put("flower_grass3");
        put("flower_grass4");
        put("flower_grass5");
        put("flower_grass6");

        put("dark_grass1");
        put("dark_grass2");

        put("mushroom_grass1");

        put("water2");
        put("water3");

        put("computer1");
        blocks.get("computer1").setOffset(new Vector2(1,1));

        put("disk1");

    }

    public static void putObj(String name){
        putObj(name, name);
    }

    public static void putObj(String name, String texture_short){
        blocks.put(name, new TileObject(texture_short+"_texture"));
    }

    public static void put(String name){
        put(name, name);
    }

    public static void put(String name, String texture_short){
        blocks.put(name, new TileBlock(texture_short+"_texture"));
    }

    public static TileBlock get(String name){
        return blocks.get(name);
    }

    public static Array<String> getNames(){
        Array<String> r = new Array<>();
        for(String s: blocks.keys())
            r.add(s);
        return r;
    }
}
