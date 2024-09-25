package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends Sprite{
    private float speed;
    private float delta;
    private Sprite visionCone;

    public Enemy() {
        super(new Texture("duck.png"));
        delta = 1/40f;
        speed = 4f;

        visionCone = new Sprite(new Texture("visionCone.jpg"));
        visionCone.setSize(3, 1);

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
        return this.getVisionCone().getBoundingRectangle().overlaps(player.getBoundingRectangle());
    }

    public void setVisionConePosition() {
        visionCone.setPosition(this.getX() + this.getWidth(), this.getY());
    }

    public Sprite getVisionCone() {
        return visionCone;
    }

}
