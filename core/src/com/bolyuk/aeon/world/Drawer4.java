package com.bolyuk.aeon.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.BlockList;
import com.bolyuk.aeon.TextureList;
import com.bolyuk.aeon.TileBlock;
import com.bolyuk.aeon.TileObject;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.world.World;

public class Drawer4 {

    static TileObject camera_obj;
    static TileBlock cur;
    static Vector3 vec = new Vector3();

    public static void draw(Batch b){
        if(!World.isReady())
            return;
        camera_obj = World.p();

        float x_draw = C.screen_half_width - C.tile_half_width;
        float y_draw = C.screen_height + (C.tile_height * C.render) - (C.tile_z_offset * World.p().zInt());

        x_draw +=((camera_obj.xDelta()-camera_obj.yDelta())*C.tile_width)/2f;
        y_draw +=((camera_obj.xDelta()+camera_obj.yDelta())*C.tile_width)/4f+camera_obj.zDelta()*C.tile_z_offset;



        int cam_x=camera_obj.xInt();
        int cam_y=camera_obj.yInt();
        int cam_z=camera_obj.zInt();

        int x=cam_x-C.render;
        int y=cam_y-C.render;
        int z=cam_z-C.render;

        int x_end=cam_x+C.render;
        int y_end=cam_y+C.render;
        int z_end=cam_z+C.render;

        if(x <0) {
            int xD = -x;
            y_draw-=xD*C.tile_height;
            x_draw-=xD*C.tile_half_width;
            x = 0;
        }
        if(y < 0) {
            int yD = -y;
            y_draw-=yD*C.tile_height;
            x_draw+=yD*C.tile_half_width;
            y = 0;
        }
        if(z < 0)
           z = 0;

        for(;x < x_end; x++, y++, y_draw -=C.tile_height*2f)
            draw_xy_layer(x,y,z,x_end,y_end, z_end,x_draw,y_draw, b);

    }

    public static void draw_xy_layer(int x , int y, int z,int x_end,int y_end, int z_end, float x_draw, float y_draw,Batch b){
        int cy=y;
        int cx=x;

        draw_z_layer(cx,cy,z, z_end, x_draw, y_draw,b);

        float cy_draw=y_draw-C.tile_height;
        float cx_draw=x_draw+C.tile_half_width;

        for(cy+=1; cy < y_end; cy++, cy_draw -=C.tile_height, cx_draw+=C.tile_half_width )
            draw_z_layer(cx,cy,z, z_end, cx_draw, cy_draw,b);

        cy_draw=y_draw-C.tile_height;
        cx_draw=x_draw-C.tile_half_width;

        cy=y;
        cx=x;

        for(cx+=1; cx < x_end; cx++, cy_draw -=C.tile_height, cx_draw-=C.tile_half_width)
            draw_z_layer(cx,cy,z, z_end, cx_draw, cy_draw, b);

    }

    public static void draw_z_layer(int cx, int cy,int z, int z_end, float x_draw, float y_draw,Batch b){
        for(; z < z_end; z++, y_draw+=C.tile_z_offset){
            vec.x=cx;
            vec.y=cy;
            vec.z=z;

            synchronized (World.map) {
                cur = World.getBlock(vec);
            }
            if(cur != null){
                cur.draw(b, x_draw, y_draw);
            }
            synchronized (World.objects) {
                for (TileObject t : World.objects.values())
                    if (t.getIntPosition().equals(vec))
                        t.draw(b, x_draw, y_draw);
            }

            //if(darker == true)
            //    b.setColor(Color.WHITE);
            if(World.selected_position != null && World.selected_position.equals(vec))
                b.draw(TextureList.get("selected_texture"),x_draw,y_draw);
            else if(World.selected_position != null && World.selected_position.x == cx && World.selected_position.y == cy)
                b.draw(TextureList.get("selected_primary_texture"),x_draw,y_draw);
        }
    }
}
