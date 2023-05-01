package com.bolyuk.aeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bolyuk.aeon.utils.TextureUtil;

import java.util.HashMap;

public class TextureList {
    public static TextureAtlas block_atlas;
    public static HashMap<String, Sprite> textures = new HashMap<>();
    public static void init(){
        block_atlas = new TextureAtlas(Gdx.files.internal("blocks/texture_atlas.atlas"));
        //old blocks
        put("sand2", block_atlas.createSprite("sand2"));
        put("sand1",block_atlas.createSprite("sand1"));
        put("grass",block_atlas.createSprite("default_grass"));
        put("water",block_atlas.createSprite("water1"));
        put("tree1",block_atlas.createSprite("tree5"));
        put("frog",block_atlas.createSprite("frog1"));
        put("player",block_atlas.createSprite("player"));
        put("selected", block_atlas.createSprite("selected"));
        put("selected_primary", block_atlas.createSprite("selected_primary"));
        // new blocks
        put("tree2", block_atlas.createSprite("tree1"));
        put("tree3", block_atlas.createSprite("tree2"));
        put("tree4", block_atlas.createSprite("tree3"));
        put("tree5", block_atlas.createSprite("tree4"));

        put("flower_grass1",block_atlas.createSprite("flower_grass1") );
        put("flower_grass2",block_atlas.createSprite("flower_grass2") );
        put("flower_grass3",block_atlas.createSprite("flower_grass3") );
        put("flower_grass4",block_atlas.createSprite("flower_grass4") );
        put("flower_grass5",block_atlas.createSprite("flower_grass5") );
        put("flower_grass6",block_atlas.createSprite("flower_grass6") );

        put("dark_grass1",block_atlas.createSprite("dark_grass1") );
        put("dark_grass2",block_atlas.createSprite("dark_grass2") );

        put("mushroom1",block_atlas.createSprite("mushroom1") );
        put("mushroom2",block_atlas.createSprite("mushroom2") );

        put("mushroom_grass1",block_atlas.createSprite("mushroom_grass1") );

        put("water2",block_atlas.createSprite("water2"));
        put("water3",block_atlas.createSprite("water3"));

        put("computer1",block_atlas.createSprite("computer1"));

        put("disk1",block_atlas.createSprite("disk1"));
    }

    public static void put(String s, Sprite t){
        textures.put(s+"_texture",t);
    }

    public static Sprite get(String s) {
        if(textures.containsKey(s))
            return  textures.get(s);
            System.out.println("TEXTURE ["+s+"] is not exist");
        return null;
    }

    public static void dispose(){
        block_atlas.dispose();
    }
}
