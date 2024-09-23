package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends Sprite{
    private float speed;
    private float delta;

    public Enemy() {
        super(new Texture("duck.png"));
        delta = 1/40f;
        speed = 4f;
    }

    

    
}
