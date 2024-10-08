package io.github.duckpocalypse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
            Constants.PhysicsConstants.POSITION_ITERATIONS
        );
    }

    private void createObject(){
		
	    //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
 
 
        // add it to the world
        Body bodyd = world.createBody(bodyDef);
 
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);
 
        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
 
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0.0f);
 
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
}
}
