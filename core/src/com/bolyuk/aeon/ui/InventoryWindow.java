package com.bolyuk.aeon.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InventoryWindow extends DefaultWindow {
    public InventoryWindow() {
        super(" [TileBlock]");
        Table t = new Table();
        ScrollPane p = new ScrollPane(new Inventory());

        p.setForceScroll(true,true);
        p.setOverscroll(false, false);
        add(t);
        t.add(p);
        padTop(10);
        p.layout();
        p.layout();
    }


}
