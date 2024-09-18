package io.github.duckpocalypse;

import com.badlogic.gdx.physics.box2d.World;
 

//BodyFactory is a singleton class
public class BodyFactory {
	
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
}
