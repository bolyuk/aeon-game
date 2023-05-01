package com.bolyuk.aeon.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.bolyuk.aeon.BlockList;
import com.bolyuk.aeon.TileBlock;
import com.bolyuk.aeon.TileObject;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.fb.Auth;
import com.bolyuk.aeon.fb.DB;
import com.bolyuk.aeon.fb.objects.DBUser;
import com.bolyuk.aeon.interfaces.Action;
import com.bolyuk.aeon.utils.MapUtil;


import java.util.HashMap;
import java.util.List;

public class World {

   public static Vector2 help_vector2 = new Vector2();
   public static boolean isMapReady=false;
   public static String current_path="";

   public static TileObject player;
   public static int rotation=0;

   public static Vector3 selected_position;
   static String selected_block;

   public static HashMap<String, Chunk> map = new HashMap<>();
   public static HashMap<String, TileObject> objects = new HashMap<>();
   public static Array<String> load_queue = new Array<>();

   public static boolean isReady(){
      return isMapReady;
   }

   public static TileObject p(){
      return player;
   }

   public static String getSelectedBlock(){
      return selected_block;
   }

   public static void setSelectedBlock(String block){
      selected_block=block;
   }

   public static Vector3 getSelectedPosition(){
      return selected_position;
   }

   public static void init(String world_name, final Action onWorldLoaded){
      current_path=C.DB_initPath+"/maps/"+world_name;

      Auth.login(new Action() {
         @Override
         public void aNotify() {
            DB.getUser(Auth.getUid(), new DB.Getter<TileObject>() {
               @Override
               public void oGet(TileObject data) {
                  player = data;
                  objects.put(Auth.getUid(),player);
                  DB.setOnline(Auth.getUid(), true);
                  System.out.println("\n\n\n\n\n user loaded \n\n\n\n\n");
                  loadChunks(onWorldLoaded);
                  DB.setUsersListener(new DB.Getter<List<DBUser>>() {
                     @Override
                     public void oGet(List<DBUser> data) {
                           System.out.println("\n\n\n\n\n users parsed start \n\n\n\n\n");

                           for (int i = 0; i < data.size(); i++) {
                              DBUser cur = data.get(i);
                              if (cur.uId != null && !cur.uId.equals(Auth.getUid()) && cur.online)
                                 synchronized (objects) {
                                    if (!objects.containsKey(cur.uId))
                                       objects.put(cur.uId, cur.toTileObject());
                                    else
                                       objects.get(cur.uId).message(cur.message).position(cur.position.toVector3());
                                 }
                           }
                          System.out.println("\n\n\n\n\n users parsed \n\n\n\n\n");
                        }

                  });
               DB.onlineListener();
               }
            });
         }
      });
   }

   public static void playerMove(float dx, float dy){
      help_vector2.x=dx;
      help_vector2.y=dy;
      help_vector2.rotateDeg(45);
      help_vector2.rotateDeg(-rotation);
      dx=help_vector2.x*.4f;
      dy=help_vector2.y*.4f;

      float next_x = player.x()+dx;
      float next_y = player.y()-dy;
      float next_z = player.z();

      if(next_x < 0)
         next_x=0;
      if(next_y < 0)
         next_y=0;

      Vector3 result = new Vector3(next_x, next_y, next_z);
      Vector3 result_int = new Vector3((int)next_x,(int)next_y,(int)next_z);
      if(getBlock(new Vector3(result_int.x, result_int.y, result_int.z+1))  != null)
         return;
      if(getBlock(result_int) != null)
         result.z+=1;
      else if(getBlock(new Vector3(result_int.x, result_int.y, result_int.z-1)) == null)
         result.z-=1;



      if(selected_position != null && Math.abs(selected_position.dst(player.getPosition())) > 5)
        selected_position = null;

      player.position(result);
      DB.set(current_path + "/users/" + Auth.getUid(),new DBUser(player));
      loadChunks(null);
   }

   public static void setBlock(Vector3 v, String block){
      Vector3 p = MapUtil.pointInChunkFromPoint(v);
      String path = MapUtil.chunkNameFromPoint(v)+"/"+MapUtil.pointToString(p);

      DB.set(current_path+"/chunks/"+path, block);
      DB.set(current_path+"/requests/"+path, block);
   }

   public static void delBlock(Vector3 v){
      Vector3 p = MapUtil.pointInChunkFromPoint(v);
      String path = MapUtil.chunkNameFromPoint(v)+"/"+MapUtil.pointToString(p);

      DB.set(current_path+"/chunks/"+path, null);
      DB.set(current_path+"/requests/"+path, "delete");
   }

   //TODO synchronized
   public static TileBlock getBlock(Vector3 v){
      help_vector2.x=(int)v.x;
      help_vector2.y=(int)v.y;
      help_vector2.rotateAroundDeg(new Vector2(player.xInt(), player.yInt()), rotation);
      v.x=Math.round(help_vector2.x);
      v.y=Math.round(help_vector2.y);
      v.z=Math.round(v.z);
      if(v.x < 0 || v.y < 0 || v.z < 0)
         return null;
      String chunk_name = MapUtil.chunkNameFromPoint(v);
      TileBlock r=null;
      synchronized (map) {
         if (map.containsKey(chunk_name)) {
            r = map.get(chunk_name).get(MapUtil.pointInChunkFromPoint(v));

            if (v.z == 0 && r == null)
               r = BlockList.get("water");
         }

         return r;
      }
   }

   public static void loadChunks(final Action onChunksLoaded){
      synchronized (map) {
      final Vector3 c_vector = MapUtil.chunkFromPoint(player.getPosition());
      final Array<String> vectors = new Array<>();
      final Array<String> unload_chunks = new Array<>();

      for(int x=(int)c_vector.x-1; x<(int)c_vector.x+2;x++ )
         for(int y=(int)c_vector.y-1; y<(int)c_vector.y+2;y++ )
            for(int z=(int)c_vector.z-1; z<(int)c_vector.z+2;z++ )
               if(z > -1 && !map.containsKey(x+":"+y+":"+z) && x > -1 && y > -1)
               vectors.add(x+":"+y+":"+z);

            Action a = new Action() {
               @Override
               public void aNotify() {
                     for(String s : map.keySet())
                        if(MapUtil.parsePoint(s).dst(c_vector) > 2)
                           unload_chunks.add(s);

                     for (String s : unload_chunks)
                        map.remove(s).dispose();


                     if(onChunksLoaded != null)
                        onChunksLoaded.aNotify();
               }
            };

            for(int i=0; i < vectors.size; i++)
              loadChunk(vectors.get(i), a);
      }
   }

   public static void loadChunk(final String chunk_name, final Action onChunksLoaded){
      synchronized (map) {
         if (!map.containsKey(chunk_name) && !load_queue.contains(chunk_name, false)) {
            load_queue.add(chunk_name);
            DB.getChunk(chunk_name, new DB.Getter<Chunk>() {
               @Override
               public void oGet(Chunk data) {

                  map.put(chunk_name, data);

                  //System.out.println("\n\n\n\n\n " + chunk_name + " loaded \n\n\n\n\n");
                  load_queue.removeValue(chunk_name, false);
                  if (onChunksLoaded != null && load_queue.isEmpty())
                     onChunksLoaded.aNotify();
               }
            });

         } else if (onChunksLoaded != null && load_queue.isEmpty())
            onChunksLoaded.aNotify();
      }
   }
}
