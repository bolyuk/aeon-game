package com.bolyuk.aeon.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bolyuk.aeon.BlockList;
import com.bolyuk.aeon.TileBlock;
import com.bolyuk.aeon.constants.Styles;
import com.bolyuk.aeon.world.World;

public class Inventory extends Table {
    Array<Drawable> draws = new Array<>();
    public Inventory(){
        ObjectMap<String, TileBlock> blocks = BlockList.blocks;
        ObjectMap.Values<TileBlock> val = blocks.values();
        int i=0;
        TileBlock b;
        while(val.hasNext()){
            b=val.next();
            if(i%4 == 0)
                row();
            final String name = blocks.findKey(b, false);
            draws.add(new TextureRegionDrawable(b.texture));
            ImageButton but = new ImageButton(Styles.buttonStyle(draws.get(i)));
            but.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    World.setSelectedBlock(name);
                }
            });
            add(but).pad(3).size(25,25);
            i++;
        }

    }
}
