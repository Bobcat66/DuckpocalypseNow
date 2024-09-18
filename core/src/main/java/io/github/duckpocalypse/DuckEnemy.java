package io.github.duckpocalypse;

public class DuckEnemy {
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
    }

    
}
