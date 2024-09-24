package io.github.duckpocalypse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture backgroundImage;
    private Sprite mapSprite;
    private Player player;
    private OrthographicCamera camera;
    private Enemy enemy;
    private TiledMap map;
    private TiledMapTileLayer collisionLayer; 
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void create() {
        map = new TmxMapLoader().load("TileMap/duckmap.tmx");
        backgroundImage = new Texture("libgdx.png");
        mapSprite = new Sprite(new Texture("grey.jpg"));
        mapSprite.setSize(
            Constants.WorldConstants.WORLD_WIDTH,
            Constants.WorldConstants.WORLD_HEIGHT
        );
        player = new Player();
        player.setSize(1, 1);
        enemy = new Enemy();
        enemy.setSize(1, 1);
        
        float unitScale = 1 / 32f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30,20);
        renderer.setView(camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
        spriteBatch = new SpriteBatch();
        
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        handleInput();
        camera.update();
        renderer.setView(camera);
        renderer.render();

		spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        player.draw(spriteBatch);
        enemy.draw(spriteBatch);
        enemy.getVisionCone().draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundImage.dispose();
        mapSprite.getTexture().dispose();
        enemy.getTexture().dispose();
        enemy.getVisionCone().getTexture().dispose();
        player.getTexture().dispose();
    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-0.3f, 0, 0);
            /*
            if(collisionLayer.getCell((int) (player.getX()-0.3f)/16,(int) player.getY()/16).getTile().getId() != 7 & collisionLayer.getCell((int) (player.getX()-0.3f)/16,(int) player.getY()/16).getTile().getId() != 8){
                player.translate(-0.3f,0);
            }
            */
            player.translate(-0.3f,0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(0.3f, 0, 0);
            /*
            if(collisionLayer.getCell((int) (player.getX()+0.3f)/16,(int) player.getY()/16).getTile().getId() != 7 & collisionLayer.getCell((int) (player.getX()+0.3f)/16,(int) player.getY()/16).getTile().getId() != 8){
                player.translate(0.3f,0);
            }
            */
            player.translate(0.3f,0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -0.3f, 0);
            /*
            if(collisionLayer.getCell((int) player.getX()/16,(int) (player.getY() - 0.3f)/16).getTile().getId() != 7 & collisionLayer.getCell((int) player.getX()/16,(int) (player.getY() - 0.3f)/16).getTile().getId() != 8){
                player.translate(0,-0.3f);
            }
            */
            player.translate(0,-0.3f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 0.3f, 0);
            /*
            if(collisionLayer.getCell((int) player.getX()/16,(int) (player.getY() - 0.3f)/16).getTile().getId() != 7 & collisionLayer.getCell((int) player.getX()/16,(int) (player.getY() - 0.3f)/16).getTile().getId() != 8){
                player.translate(0,0.3f);
            }
            */
            player.translate(0,0.3f);
		}

		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;


		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth/2f, 60-effectiveViewportWidth/2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight/2f, 40-effectiveViewportHeight/2f);

        enemy.setVisionConePosition();
	}

     private void draw() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        player.draw(spriteBatch);
        enemy.draw(spriteBatch);
        spriteBatch.end();
    }


    

}
