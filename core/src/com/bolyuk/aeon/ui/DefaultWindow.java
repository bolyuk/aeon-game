package com.bolyuk.aeon.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.bolyuk.aeon.constants.Styles;

public class DefaultWindow extends Window {
    public DefaultWindow(String title) {
        super(title, Styles.windowStyle());

        ImageButton close_button = new ImageButton(Styles.cancelButtonStyle());
        close_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });

        getTitleTable().add(close_button).size(30,30).padBottom(10);

    }
}
