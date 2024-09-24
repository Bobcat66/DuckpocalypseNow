package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

public class Enemy extends Sprite{
    private float speed;
    private float delta;
    private Sprite visionCone;
    private float visionAngle; // in radians
    private float visionDistance;

    public Enemy() {
        super(new Texture("duck.png"));
        delta = 1/40f;
        speed = 4f;

        visionCone = new Sprite(new Texture("visionCone.jpg"));
        visionCone.setSize(3, 1);

        visionAngle = MathUtils.PI / 4; // 45 degrees
        visionDistance = 25f; // Example distance
    }

    public void moveVertically()
    {
            this.translateY(speed * delta);
    }
    public void moveHorizontally()
    {
            this.translateX(speed * delta);
    }
    public boolean checkCollision(Player player)
    {
        return this.getBoundingRectangle().overlaps(player.getBoundingRectangle());
    }

    public boolean checkVisionCone(Player player) {
        return false;
    }

    public void setVisionConePosition() {
        visionCone.setPosition(this.getX() + this.getWidth(), this.getY());
    }

    public Sprite getVisionCone() {
        return visionCone;
    }


    
}
