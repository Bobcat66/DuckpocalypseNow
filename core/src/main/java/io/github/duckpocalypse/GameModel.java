package io.github.duckpocalypse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

//Handles all game logic
public class GameModel {
    public World world;

    public GameModel(){
        world = new World(new Vector2(0,0),true);
    }

    //Game logic step goes here
    /**
     * Steps forward 1/60th of a second
     */
    public void logicStep() {
        world.step(
            Constants.PhysicsConstants.TIME_STEP,
            Constants.PhysicsConstants.VELOCITY_ITERATIONS,
            Constants.PhysicsConstants.POSITION_INTERATIONS
        );
    }
}
