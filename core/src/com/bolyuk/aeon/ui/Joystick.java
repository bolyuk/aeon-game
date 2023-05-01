package com.bolyuk.aeon.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.bolyuk.aeon.constants.Styles;

public class Joystick extends Touchpad {
    public Joystick(float x, float y) {
        super(0, Styles.touchpadStyle());
        setBounds(x,y,100,100);
    }

    public Joystick(float x, float y, float size){
        super(0, Styles.touchpadStyle());
        setBounds(x,y,size,size);
    }
}
