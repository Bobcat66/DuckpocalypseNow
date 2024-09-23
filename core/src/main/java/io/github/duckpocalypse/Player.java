package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite{
    private static float speed;
    private static float delta;

    public Player() {
        super(new Texture("player.png"));
        delta = 1/40f;
        speed = 4f;
    }

    public void move(String direction) {
        if (direction.equals("up")) {
            this.translateY(-speed * delta);
        } else if (direction.equals("down")) {
            this.translateY(speed * delta);
        } else if (direction.equals("left")) {
            this.translateX(-speed * delta);
        } else if (direction.equals("right")) {
            this.translateX(speed * delta);
        }
    }
    
}
