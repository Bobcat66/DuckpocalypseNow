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
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture backgroundImage;
    private Sprite mapSprite;
    private Player player;
    private OrthographicCamera camera;
    private ArrayList<Enemy> enemyList;
    private TiledMap map;
    private TiledMapTileLayer collisionLayer; 
    private OrthogonalTiledMapRenderer renderer;
    Sprite goal;

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

        goal = new Sprite(new Texture("star.png"));
        goal.setSize(1, 1);
        goal.setPosition(58, 38);

        initializeEnemies();
        
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
        for(Enemy enemy : enemyList){
            enemy.draw(spriteBatch);
            enemy.getVisionCone().draw(spriteBatch);
        }
        goal.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundImage.dispose();
        mapSprite.getTexture().dispose();
        for(Enemy enemy : enemyList){
            enemy.getTexture().dispose();
            enemy.getVisionCone().getTexture().dispose();
        }
        goal.getTexture().dispose();
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

        for(Enemy enemy : enemyList){
            enemy.move();
            enemy.setVisionConePosition();
        }

        if(player.getBoundingRectangle().overlaps(goal.getBoundingRectangle())){
            System.out.println("You win!");
        }
	}


    private void initializeEnemies() {
        enemyList = new ArrayList<Enemy>();
        Enemy enemyOne = new Enemy(true);
        enemyOne.setSize(1, 1);
        enemyOne.setPosition(10, 18);
        enemyList.add(enemyOne);

        Enemy enemyTwo = new Enemy(true);
        enemyTwo.setSize(1, 1);
        enemyTwo.setPosition(30, 19);
        enemyList.add(enemyTwo);

        Enemy enemyThree = new Enemy(true);
        enemyThree.setSize(1, 1);
        enemyThree.setPosition(20, 28);
        enemyList.add(enemyThree);

        Enemy enemyFour = new Enemy(false);
        enemyFour.setSize(1, 1);
        enemyFour.setPosition(23, 14);
        enemyList.add(enemyFour);

        Enemy enemyFive = new Enemy(true);
        enemyFive.setSize(1, 1);
        enemyFive.setPosition(40, 2);
        enemyList.add(enemyFive);

        Enemy enemySix = new Enemy(false);
        enemySix.setSize(1, 1);
        enemySix.setPosition(32, 7);
        enemyList.add(enemySix);

        Enemy enemySeven = new Enemy(false);
        enemySeven.setSize(1, 1);
        enemySeven.setPosition(49, 38);
        enemyList.add(enemySeven);
    }


    

}
