package io.github.duckpocalypse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture playerTexture;
    private World world;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        playerTexture = new Texture("Donald_Trump_mug_shot.jpg"); //Trump mugshot is a placeholder texture
        world = new World(new Vector2(0,0),true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.draw(playerTexture,140,210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        playerTexture.dispose();
    }

    //Game logic goes here
    /**
     * Steps forward in game logic
     * @param delta time in milliseconds
     */
    public void logicStep(float delta) {
        
    }
}
