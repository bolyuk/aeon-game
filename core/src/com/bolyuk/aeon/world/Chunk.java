package com.bolyuk.aeon.world;


import com.badlogic.gdx.math.Vector3;
import com.bolyuk.aeon.BlockList;
import com.bolyuk.aeon.TileBlock;
import com.bolyuk.aeon.fb.DB;
import com.bolyuk.aeon.utils.MapUtil;

import java.util.HashMap;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class Chunk {
    public String chunk_name;
    public TileBlock[][][] map = new TileBlock[16][16][16];
    public ListenerPromise<HashMap> listener;


    public void set(Vector3 v, String block){
        if(block != null)
            map[(int)v.x][(int)v.y][(int)v.z]= BlockList.get(block);
        else
            map[(int)v.x][(int)v.y][(int)v.z] = null;
    }

    public TileBlock get(Vector3 v){
        return map[(int)v.x][(int)v.y][(int)v.z];
    }

    public TileBlock get(int x, int y, int z){
        return map[x][y][z];
    }

    public Chunk(final String chunk_name){
        this.chunk_name=chunk_name;

       listener = GdxFIRDatabase.inst()
                .inReference(World.current_path+"/requests/"+chunk_name)
                .onDataChange(HashMap.class);

        GdxFIRDatabase.promise().then(listener).then(new Consumer<HashMap<String, String>>() {
                    @Override
                    public void accept(HashMap<String, String> row_requests) {
                        for(String point : row_requests.keySet())
                            if(row_requests.get(point).equals("delete"))
                                set(MapUtil.parsePoint(point), null);
                            else
                               set(MapUtil.parsePoint(point), row_requests.get(point));

                        if(row_requests.size() > 5)
                            DB.del(World.current_path+"/requests/"+chunk_name);

                    }
                });

    }

    public void dispose(){
      listener.cancel();
    }

}
