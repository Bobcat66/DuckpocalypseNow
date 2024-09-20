package io.github.duckpocalypse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture backgroundImage;
    private Sprite mapSprite;
    private Sprite playerSprite;
    private FitViewport viewport;

    @Override
    public void create() {
        backgroundImage = new Texture("libgdx.png");
        mapSprite = new Sprite(new Texture("grey.jpg"));
        mapSprite.setSize(
            Constants.WorldConstants.WORLD_WIDTH,
            Constants.WorldConstants.WORLD_HEIGHT
        );
        playerSprite = new Sprite(new Texture("player.png"));
        playerSprite.setSize(5, 5);
        viewport = new FitViewport(Constants.WorldConstants.WORLD_WIDTH, Constants.WorldConstants.WORLD_HEIGHT);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        handleInput();
        viewport.apply();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        draw();
    }

    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundImage.dispose();
        mapSprite.getTexture().dispose();
        playerSprite.getTexture().dispose();
    }

    private void handleInput() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerSprite.translateX(-speed * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerSprite.translateX(speed * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			playerSprite.translateY(-speed * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerSprite.translateY(speed * delta);
		}
	}

     private void draw() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        mapSprite.draw(spriteBatch);
        playerSprite.draw(spriteBatch);

        spriteBatch.end();
    }


    

}
