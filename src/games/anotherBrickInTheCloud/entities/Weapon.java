package games.anotherBrickInTheCloud.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.anotherBrickInTheCloud.util.Movable;
import games.anotherBrickInTheCloud.util.Rectangle;

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

		isMoving = true;
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.red);
		context.fillRect((float) x, (float) y, (float) width, (float) height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		moveX(delta);
	}

}
