package com.bolyuk.aeon.fb;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;
import com.bolyuk.aeon.BlockList;
import com.bolyuk.aeon.TileObject;
import com.bolyuk.aeon.fb.objects.DBUser;
import com.bolyuk.aeon.utils.MapUtil;
import com.bolyuk.aeon.utils.TextureUtil;
import com.bolyuk.aeon.world.Chunk;
import com.bolyuk.aeon.world.World;

import java.util.HashMap;
import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DB {
    public  interface Getter<T>{
         void oGet(T data);
    }

    public static void setOnline(String uId, boolean value){
        set(World.current_path+"/users/"+uId+"/online", value);
    }

    public static void onlineListener(){
        GdxFIRDatabase.inst()
                .inReference(World.current_path+"/users/"+Auth.getUid()+"/online")
                .onDisconnect()
                .setValue(false);
    }

    public static void setMessage(String text){
        World.p().message(text);
        set(World.current_path+"/users/"+Auth.getUid(), new DBUser(World.p()));
    }

    public static void getChunk(final String chunk_name, final Getter<Chunk> getter){
            GdxFIRDatabase.inst()
                    .inReference(World.current_path + "/chunks/" + chunk_name)
                    .readValue(HashMap.class)
                    .then(new Consumer<HashMap<String, String>>() {
                        @Override
                        public void accept(HashMap<String, String> raw_chunk) {
                            Chunk r = new Chunk(chunk_name);

                                if(raw_chunk != null)
                                for (String point : raw_chunk.keySet())
                                    r.set(MapUtil.parsePoint(point), raw_chunk.get(point));
                                getter.oGet(r);
                        }
                    });
    }

    public static void getUser(final String Uid, final Getter<TileObject> getter){
            GdxFIRDatabase.inst()
                    .inReference(World.current_path + "/users/" + Uid)
                    .readValue(DBUser.class)
                    .then(new Consumer<DBUser>() {
                        @Override
                        @MapConversion(DBUser.class)
                        public void accept(DBUser dbUser) {
                            TileObject r;

                            if (dbUser == null)
                                r = new TileObject("player_texture").position(new Vector3(0.1f, 0, 1));
                            else
                                r = dbUser.toTileObject();

                            getter.oGet(r);
                        }
                    });

    }

    public static void setUsersListener(final Getter<List<DBUser>> getter){
        try {
            GdxFIRDatabase.inst()
                    .inReference(World.current_path + "/users/")
                    .onDataChange(List.class)
                    .then(new Consumer<List<DBUser>>() {
                        @Override
                        @MapConversion(DBUser.class)
                        public void accept(List<DBUser> users) {
                            getter.oGet(users);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void push(String path, Object value){
        GdxFIRDatabase.inst()
                .inReference(path)
                        .push()
                //.silentFail()
                        .setValue(value);
    }

    public static <T> void set(String path, T value){
        GdxFIRDatabase.inst()
                .inReference(path)
                //.silentFail()
                .setValue(value);
    }

    public static void del(String path){
        GdxFIRDatabase.inst()
                .inReference(path)
                //.silentFail()
                .setValue(null);
    }
}
