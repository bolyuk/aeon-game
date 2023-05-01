package com.bolyuk.aeon.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.constants.Styles;
import com.bolyuk.aeon.fb.DB;
import com.bolyuk.aeon.utils.TextureUtil;
import com.bolyuk.aeon.world.World;

public class HUD extends Stage {

    Joystick j = new Joystick(5,5);
    Joystick j1 = new Joystick(C.screen_width-80,5, 80);
    ImageButton put = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("place_button.png")));
    ImageButton up = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("up_button.png")));
    ImageButton down = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("down_button.png")));
    ImageButton inv = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("inventory_button.png")));
    ImageButton del = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("delete_button.png")));
    ImageButton left = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("rotate_left_button.png")));
    ImageButton right = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("rotate_right_button.png")));
    ImageButton chat = new ImageButton(Styles.buttonStyle(TextureUtil.drawable("chat_button.png")));

    public HUD(Viewport viewport, Batch batch){
        super(viewport,batch);

        put.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(World.getSelectedBlock() != "" && World.getSelectedPosition() != null)
                   World.setBlock(World.getSelectedPosition(),World.getSelectedBlock());
            }
        });

        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.rotation-=90;
                if(World.rotation < 0)
                    World.rotation+=360;
            }
        });

        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.rotation+=90;
                if(World.rotation >= 360)
                    World.rotation-=360;
            }
        });


        up.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(World.getSelectedPosition() != null)
                   World.selected_position.z+=1;
            }
        });

        inv.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(new InventoryWindow());
            }
        });

        del.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(World.getSelectedPosition() != null)
                    World.delBlock(World.getSelectedPosition());
            }
        });


        down.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(World.getSelectedPosition() != null)
                    World.selected_position.z-=1;
            }
        });

        chat.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(World.p().getMessage() != null)
                    DB.setMessage(null);
                else{
                    Input.TextInputListener list = new Input.TextInputListener() {
                        @Override
                        public void input(String text) {
                            DB.setMessage(text);
                        }

                        @Override
                        public void canceled() {

                        }
                    };
                    Gdx.input.getTextInput(list, "Message", "", "write here you\'re message!");
                }

            }
        });

        up.setPosition(C.screen_width-20 , 90);
        put.setPosition(C.screen_width -100, 70);
        down.setPosition(C.screen_width-50 , 90);
        inv.setPosition(10, C.screen_height-40);
        chat.setPosition(40, C.screen_height-40);
        left.setPosition(10, 100);
        right.setPosition(75, 100);
        del.setPosition(C.screen_width -100, 20);

        addActor(put);
        addActor(inv);
        addActor(del);
        addActor(j);
        addActor(up);
        addActor(down);
        addActor(right);
        addActor(left);
        addActor(chat);
        addActor(j1);

    }

    @Override
    public void draw() {
        if(World.isReady())
           super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(j.isTouched())
            World.playerMove(-j.getKnobPercentX(), j.getKnobPercentY());
    }
}
