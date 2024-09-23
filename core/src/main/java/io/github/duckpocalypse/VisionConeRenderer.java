package io.github.duckpocalypse;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class VisionConeRenderer {
    private ShapeRenderer shapeRenderer;

    public VisionConeRenderer() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(Enemy enemy) {
        Polygon visionCone = enemy.getVisionCone();
        float[] vertices = visionCone.getVertices();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.polygon(vertices);
        shapeRenderer.end();
    }
}
