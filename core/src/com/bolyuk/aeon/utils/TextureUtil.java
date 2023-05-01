package com.bolyuk.aeon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureUtil {

    public static Texture texture(String internalPath){
        return new Texture(Gdx.files.internal(internalPath));
    }

    public static Drawable drawable(String internalPath){
        return new TextureRegionDrawable(texture(internalPath));
    }

    public static NinePatchDrawable ninePatch(String internalPath, int x1, int x2, int x3, int x4){
        return new NinePatchDrawable(new NinePatch(texture(internalPath),x1,x2,x3,x4));
    }
}
