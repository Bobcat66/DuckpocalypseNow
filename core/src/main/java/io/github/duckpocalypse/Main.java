package io.github.duckpocalypse;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture backgroundImage;
    private Sprite mapSprite;
    private Player player;
    private OrthographicCamera camera;
    private VisionConeRenderer visionConeRenderer;
    private Enemy enemy;
    private TiledMap map;
    private MapLayer collisionLayer; 
    private OrthogonalTiledMapRenderer renderer;
    private ShapeRenderer shapeRenderer;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        map = new TmxMapLoader().load("TileMap/duckmapCollisions.tmx");
        backgroundImage = new Texture("libgdx.png");
        mapSprite = new Sprite(new Texture("grey.jpg"));
        mapSprite.setSize(
            Constants.WorldConstants.WORLD_WIDTH,
            Constants.WorldConstants.WORLD_HEIGHT
        );
        player = new Player();
        player.setSize(0.5f, 0.5f);
        player.translate(0.1f,0.1f);
        enemy = new Enemy();
        enemy.setSize(0.5f, 0.5f);
        
        float unitScale = 1 / 32f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        visionConeRenderer = new VisionConeRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30,20);
        renderer.setView(camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
        collisionLayer = map.getLayers().get("CollisionWallLayer");
        spriteBatch = new SpriteBatch();
        shapeRenderer.setProjectionMatrix(camera.combined);
        
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        shapeRenderer.setProjectionMatrix(camera.combined);
        visionConeRenderer.render(enemy);
        handleInput();
        camera.update();
        renderer.setView(camera);
        renderer.render();

		spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        player.draw(spriteBatch);
        enemy.draw(spriteBatch);
        spriteBatch.end();
        
        shapeRenderer.begin(ShapeType.Line);
        Rectangle pRect = player.getBoundingRectangle();
        shapeRenderer.rect(pRect.getX(),pRect.getY(),pRect.getWidth(),pRect.getHeight());
        for (Rectangle tileRect : tiles){
            shapeRenderer.rect(tileRect.getX(),tileRect.getY(),tileRect.getWidth(),tileRect.getHeight());
        }
        shapeRenderer.end();
        

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundImage.dispose();
        mapSprite.getTexture().dispose();
        map.dispose();
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
            player.translate(-0.3f,0);
            if (checkCollision(player)){
                player.translate(0.3f,0);
                camera.translate(0.3f,0);
                System.out.println("Collision Detected!");
                System.out.println("x " + player.getX() + "y " + player.getY());
            }
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(0.3f, 0, 0);
            player.translate(0.3f,0);
            if (checkCollision(player)){
                player.translate(-0.3f,0);
                camera.translate(-0.3f,0);
                System.out.println("Collision Detected!");
                System.out.println("x " + player.getX() + "y " + player.getY());
            }
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -0.3f, 0);
            player.translate(0,-0.3f);
            if (checkCollision(player)){
                player.translate(0,0.3f);
                camera.translate(0,0.3f);
                System.out.println("Collision Detected!");
                System.out.println("x " + player.getX() + "y " + player.getY());
            }
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 0.3f, 0);
            player.translate(0,0.3f);
            if (checkCollision(player)){
                player.translate(0,-0.3f);
                camera.translate(0,-0.3f);
                System.out.println("Collision Detected!");
                System.out.println("x " + player.getX() + "y " + player.getY());
            }
		}

		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;


		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth/2f, 60-effectiveViewportWidth/2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight/2f, 40-effectiveViewportHeight/2f);
	}

    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Tile Layer 2");
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

    private boolean checkCollision(Sprite sprite) {
        Rectangle spriteRect = sprite.getBoundingRectangle();
        //spriteRect.setSize(0.1f);
        int startX = (int) (spriteRect.getX());
        int endX =(int) (startX + spriteRect.getWidth());
        int startY = (int) (spriteRect.getY());
        int endY =(int) (startY + spriteRect.getHeight());
        getTiles(startX, startY, endX, endY, tiles);
        System.out.println(spriteRect.height);
        System.out.println(spriteRect.x);
        System.out.println(spriteRect.width);
        System.out.println(spriteRect.y);
        for (Rectangle tile : tiles) {
            if (spriteRect.overlaps(tile)) {
                return true;
            }
        }
        return false;
    }


    

}
