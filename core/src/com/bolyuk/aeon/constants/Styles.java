package com.bolyuk.aeon.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bolyuk.aeon.utils.TextureUtil;

public class Styles {

    public static String charset = FreeTypeFontGenerator.DEFAULT_CHARS+"йцукенгшщзхъфывапролджэячсмитьёбюіїєЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮІЇЄ";
    public static BitmapFont defaultFont = getFont(11);
    public static BitmapFont chatFont = chatFont();

    public static BitmapFont chatFont(){
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 9;
        p.characters = charset;
        BitmapFont r = new FreeTypeFontGenerator(Gdx.files.internal("font/Pixel Times.ttf")).generateFont(p);
        r.setColor(Color.BLACK);
        return r;
    }

    public static TextField.TextFieldStyle textFieldStyle(){
        TextField.TextFieldStyle r = new TextField.TextFieldStyle();
        r.background = TextureUtil.ninePatch("list_background.png",2,2,2,2);
        r.font = getFont(9);
        r.fontColor = Color.WHITE;
        return r;
    }

    public static ScrollPane.ScrollPaneStyle scrollPaneStyle(){
        ScrollPane.ScrollPaneStyle r = new ScrollPane.ScrollPaneStyle();
        r.background = TextureUtil.ninePatch("list_background.png",2,2,2,2);
        r.vScrollKnob = TextureUtil.ninePatch("list_background.png",2,2,2,2);
        return r;
    }

    public static TextButton.TextButtonStyle buttonStyle(){
        TextButton.TextButtonStyle r = new TextButton.TextButtonStyle();
        r.up =TextureUtil.ninePatch("list_background.png",2,2,2,2);
        r.font = getFont(10);
        r.fontColor = Color.WHITE;
        return r;
    }

    public static Label.LabelStyle labelStyle(){
        Label.LabelStyle r = new Label.LabelStyle();
        r.font = getFont(11);
        r.fontColor = Color.WHITE;
        return r;
    }
    public static List.ListStyle listStyle(){
        List.ListStyle r = new List.ListStyle();
        r.selection= TextureUtil.drawable("list_selection.png");
        r.font = getFont(11);
        r.fontColorSelected = Color.WHITE;
        r.fontColorUnselected = Color.GRAY;
        return r;
    }

    public static Window.WindowStyle windowStyle(){
        Window.WindowStyle r = new Window.WindowStyle();
        r.background = TextureUtil.ninePatch("list_background.png",2,2,2,2);
        r.titleFont = defaultFont;
        return r;
    }

    public static Touchpad.TouchpadStyle touchpadStyle(){
        Touchpad.TouchpadStyle r = new Touchpad.TouchpadStyle();
        r.background= TextureUtil.drawable("joystick.png");
        r.knob=TextureUtil.drawable("joystick_knob.png");
        return r;
    }

    public static ImageButton.ImageButtonStyle cancelButtonStyle(){
        ImageButton.ImageButtonStyle r = new ImageButton.ImageButtonStyle();
        r.imageUp = TextureUtil.drawable("close_button.png");
        return r;
    }

    public static ImageButton.ImageButtonStyle buttonStyle(Drawable background){
        ImageButton.ImageButtonStyle r = new ImageButton.ImageButtonStyle();
        r.imageUp = background;
        return r;
    }

    public static BitmapFont getFont(int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = size;
        return new FreeTypeFontGenerator(Gdx.files.internal("font/retrogaming.ttf")).generateFont(p);
    }
}
