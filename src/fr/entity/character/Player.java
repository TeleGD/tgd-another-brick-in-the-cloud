package fr.entity.character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.weapon.Weapon;
import fr.util.Movable;
import fr.util.Rectangle;

public class Player extends Movable implements Rectangle {
	
	private boolean keyPressedLeft;
	private boolean keyPressedRight;
	private boolean lastKeyPressed;
	private int stillPressed;
	 
	
	public Player() {
		x = 384;
		y = 536;
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = 0.06;
		width = 32;
		height = 32;
		jump = false;
		jumpMax = 1;
		jumpLeft = jumpMax;
		jumpPower = 0.065;
		isMoving = true;
		keyPressedLeft = false;
		keyPressedRight = false;
		lastKeyPressed = false; // false = gauche true = droite
		stillPressed = 0;// 0 = rien      1 = gauche toujours presse      2 = droite toujours presse
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.fillRect((float) x, (float) y, (float) width, (float) height);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		speedY += accelY;
		stillPressed = 0;
		if (lastKeyPressed) {
			if(keyPressedLeft ){
				stillPressed = 1;
			}
			keyPressedLeft = false;
		} else {
			if(keyPressedRight ){
				stillPressed = 2;
			}
			keyPressedRight = false;
		}
		if (keyPressedLeft)  { speedX = -0.5; }
		if (keyPressedRight) {	speedX = 0.5; }
		
		switch(stillPressed){
		case 1 :
			keyPressedLeft = true;
			break;
		case 2 :
			keyPressedRight = true;
			break;
		}
		
		moveX(delta);
		if (x > 800 - width) {
			x = 800 - width;
		}
		if (x < 0) {
			x = 0;
		}

		moveY(delta);

		if (jump) {
			jump(delta);
			jump = false;
		}

		if (y >= 600 - height) {// le perso est au sol
			y = 600 - height;
			jumpLeft = jumpMax;
		}
		if (y < 0) {// le perso a mal a la tete
			y = 0;
		}
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_LEFT:
			speedX = 0;
			keyPressedLeft = false;
			lastKeyPressed = true;
			break;
		case Input.KEY_RIGHT:
			speedX = 0;
			keyPressedRight = false;
			lastKeyPressed = false;
			break;
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		
		//deplacements
		case Input.KEY_UP:
			jump = true;
			break;
		case Input.KEY_LEFT:
			keyPressedLeft = true;
			lastKeyPressed = false;
			break;
		case Input.KEY_RIGHT:
			keyPressedRight = true;
			lastKeyPressed = true;
			break;
			
		//saut
		case Input.KEY_T:
			jumpPower = 4;
			break;
		case Input.KEY_H:
			jumpPower = 0.06;
			break;
			
			
		//attaque
		case Input.KEY_A:
			new Weapon(this.x,this.y);
			break;
		}
	}
	
}
