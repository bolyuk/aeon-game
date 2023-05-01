package com.bolyuk.aeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bolyuk.aeon.constants.C;
import com.bolyuk.aeon.constants.Styles;
import com.bolyuk.aeon.interfaces.Action;
import com.bolyuk.aeon.ui.HUD;
import com.bolyuk.aeon.world.MapInteract2;
import com.bolyuk.aeon.world.World;
import com.bolyuk.aeon.world.Drawer4;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	ExtendViewport viewport;
	HUD hud;
	BitmapFont f;
	Integer min_fps=300;
	Integer max_fps=0;
	Integer cur_fps;
	
	@Override
	public void create () {
              World.init("test-map1", new Action() {
				  @Override
				  public void aNotify() {
					  //System.out.println("\n\n\n\n\n  map loaded \n\n\n\n\n");
					  World.isMapReady = true;
				  }
			  });


		batch = new SpriteBatch();
		TextureList.init();
		BlockList.init();

		camera = new OrthographicCamera();
		viewport = new ExtendViewport(C.screen_width, C.screen_height, camera);
		hud = new HUD(viewport, batch);
		f = Styles.getFont(10);

		InputMultiplexer multiplexer = new InputMultiplexer( hud, new MapInteract2(camera));
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 1, 1, 1);

		batch.begin();

		//Drawer3.draw(batch);
		Drawer4.draw(batch);

		if(World.isReady()) {
			cur_fps = Gdx.graphics.getFramesPerSecond();
			if(cur_fps > max_fps) max_fps = cur_fps;
			if(cur_fps < min_fps) min_fps = cur_fps;
			f.draw(batch, "FPS:" +cur_fps+" MIN:"+min_fps+" MAX:"+max_fps+" R:"+World.rotation , 10, C.screen_height-5);
		}

		batch.end();
		hud.draw();
		hud.act();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		hud.dispose();
		TextureList.dispose();
	}
}
