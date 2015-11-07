package fr.entity.weapon;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Movable;
import fr.util.Rectangle;
import fr.world.World;

public class Weapon extends Movable implements Rectangle {
	
    public Weapon(double X, double Y, boolean direction) {
    	y=Y+10;
    	if ( direction ){
    		x = X+32;
    		speedX = 1;
    	}
    	if ( !direction ){
    		x = X;
    		speedX = -1;
    	}
    	
    	width = 11;
		height = 5;
		World.getProjectiles().add(this);
		
		isMoving = true;
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillRect((float) x, (float) y, (float) width, (float) height);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		moveX(delta);
	}

}
