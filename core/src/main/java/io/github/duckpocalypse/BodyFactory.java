package io.github.duckpocalypse;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
 

//BodyFactory is a singleton class
public class BodyFactory {
	
    public enum material {
        
        STEEL(1f,0.3f,0.1f),
        WOOD(1f,0f,1f),
        RUBBER(1f,0.9f,0.01f),
        STONE(7f,0.5f,0.3f);

        private float density;
        private float friction;
        private float restitution;

        material(float _density, float _friction, float _restitution){
            density = _density;
            friction = _friction;
            restitution = _restitution;
        }

        public float getDensity(){
            return density;
        }

        public float getFriction(){
            return friction;
        }

        public float getRestitution(){
            return restitution;
        }
    }


	private static World world;
    private static BodyFactory thisInstance;
		
	private BodyFactory(World world){
		this.world = world;
	}

    public static BodyFactory getInstance(World world){
        if (thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    public static FixtureDef makeFixture(
        material mat,
        Shape shape
    ){
        FixtureDef fixture = new FixtureDef();
        fixture.density = mat.getDensity();
        fixture.friction = mat.getFriction();
        fixture.restitution = mat.getRestitution();
        fixture.shape = shape;
        return fixture;
    }

    public Body makeCirclePolyBody(
        float posx,
        float posy,
        float radius,
        material mat,
        BodyDef.BodyType bodyType,
        boolean fixedRotation
    ){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
	    boxBodyDef.position.y = posy;
	    boxBodyDef.fixedRotation = fixedRotation;
		
	    //create the body to attach said definition
	    Body boxBody = world.createBody(boxBodyDef);
	    CircleShape circleShape = new CircleShape();
	    circleShape.setRadius(radius /2);
        return boxBody; //TODO: FINISH
    }
}
