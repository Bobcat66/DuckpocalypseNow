Sure, here's a step-by-step plan to check for collisions between a sprite and a tile map layer using LibGDX:

Load the Tile Map: Load the tile map using TmxMapLoader.
Get the Tile Layer: Retrieve the specific tile layer from the map.
Create a Rectangle for the Sprite: Create a bounding rectangle for the sprite.
Check for Collision: Iterate through the tiles in the layer and check if the sprite's rectangle intersects with any tile's rectangle.
Here's the code to achieve this:

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite sprite;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        batch = new SpriteBatch();

        // Load the sprite
        sprite = new Sprite(new Texture(Gdx.files.internal("sprite.png")));
        sprite.setPosition(100, 100);

        // Load the tile map
        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        // Check for collision
        if (checkCollision(sprite, tiledMap.getLayers().get("Tile Layer 1"))) {
            System.out.println("Collision detected!");
        }
    }

    private boolean checkCollision(Sprite sprite, MapLayer layer) {
        Rectangle spriteRect = sprite.getBoundingRectangle();
        MapObjects objects = layer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle tileRect = rectangleObject.getRectangle();
            if (spriteRect.overlaps(tileRect)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        sprite.getTexture().dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}

Explanation:
Initialization: The create method initializes the camera, sprite, and tile map.
Rendering: The render method clears the screen, updates the camera, renders the tile map, and draws the sprite.
Collision Detection: The checkCollision method checks if the sprite's bounding rectangle overlaps with any tile's rectangle in the specified layer.
Disposal: The dispose method cleans up resources.
Make sure you have the necessary assets (sprite.png and map.tmx) in your assets folder.