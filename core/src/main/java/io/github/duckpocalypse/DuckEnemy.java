package io.github.duckpocalypse;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public class DuckEnemy {
    //Duck Sprite
    private Sprite duckSprite;

    //Sprite dimensions
    private final int width;
    private final int height;

    //Duck velocity
    private int speed;

    //Duck type
    private final String type;

    //Duck position?

    public DuckEnemy(int width, int height, int speed, String type) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;

        setSprite(type);
    }

    public void setSprite(String type) {
        if (type.equals("duck")) {
            duckSprite = new Sprite(new Texture("duck.png"));
        } 
        /*else if (type.equals("boss")) {
            duckSprite = new Sprite(new Texture("boss.png"));
        } 
        */
    }

    
}
