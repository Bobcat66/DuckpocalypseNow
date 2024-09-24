package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

public class Enemy extends Sprite{
    private float speed;
    private float delta;
    private float visionAngle; // in radians
    private float visionDistance;

    public Enemy() {
        super(new Texture("duck.png"));
        delta = 1/40f;
        speed = 4f;
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
        Polygon visionCone = getVisionCone();
        return visionCone.contains(player.getX(), player.getY());
    }
    
    public Polygon getVisionCone() {
        float x = getX();
        float y = getY();
        float direction = getRotation() * MathUtils.degreesToRadians;

        float leftAngle = direction - visionAngle / 2;
        float rightAngle = direction + visionAngle / 2;

        float[] vertices = new float[6];
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = x + visionDistance * MathUtils.cos(leftAngle);
        vertices[3] = y + visionDistance * MathUtils.sin(leftAngle);
        vertices[4] = x + visionDistance * MathUtils.cos(rightAngle);
        vertices[5] = y + visionDistance * MathUtils.sin(rightAngle);

        return new Polygon(vertices);
    }


    
}
