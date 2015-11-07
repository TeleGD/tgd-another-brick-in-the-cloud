package fr.entity.character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.weapon.Weapon;
import fr.util.Collisions;
import fr.util.Movable;
import fr.util.Rectangle;
import fr.world.World;

public class Player extends Movable implements Rectangle {
	
	private boolean keyPressedLeft;
	private boolean keyPressedRight;
	private boolean lastKeyPressed;
	private boolean direction;
	private boolean noDamage; // pour la frame d'invulnérabilité
	private boolean end;
	private boolean collision; 
	private int stillPressed;
	private int stockWeapon;
	private int life;
	private int compteurFrame;
	
	public Player() {
		x = 384;
		y = 0;
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = 0.06;
		life = 3;   
		width = 32;
		height = 32;
		jump = false;
		jumpMax = 2;
		jumpLeft = jumpMax;
		jumpPower = 0.065;
		isMoving = true;
		keyPressedLeft = false;
		keyPressedRight = false;
		lastKeyPressed = false; // false = gauche true = droite
		stillPressed = 0;// 0 = rien      1 = gauche toujours presse      2 = droite toujours presse
		direction = true;  // true = droite false = gauche
		stockWeapon = 777;
		noDamage = false;
		compteurFrame=0;
		end = false;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.fillRect((float) x, (float) y, (float) width, (float) height);
		g.setColor(Color.white);
		g.drawString(Integer.toString(life), 300, 300);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		speedY += accelY;
		
		// mouvements droite gauche
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
		if (keyPressedLeft)  { speedX = -0.5; direction = false; }
		if (keyPressedRight) {	speedX = 0.5; direction = true; }
		
		switch(stillPressed){
		case 1 :
			keyPressedLeft = true;
			break;
		case 2 :
			keyPressedRight = true;
			break;
		}
		
		moveX(delta);
		damagePlayer();
		collision = false;	
		for(int i = 0;i<World.getPlateforms().size();i++){	
			if(Collisions.isCollisionRectRect(this, World.getPlateforms().get(i)))
				collision = true;
		}
		if(collision){
			x = oldX;
		}else{
		    oldX = x;
		}

		moveY(delta);
		collision = false;	
		for(int i = 0;i<World.getPlateforms().size();i++){	
			if(Collisions.isCollisionRectRect(this, World.getPlateforms().get(i))){
				collision = true;
				if(y < World.getPlateforms().get(i).getY()){
					jumpLeft = jumpMax;
				}else{
					jumpLeft = 0;
				}
			}
		}
		
		if(collision){
			y = oldY;
			speedY = 0;
		}else{
			oldY = y;
		}
		
		

		//saut
		if (jump) {
			jump(delta);
			jump = false;
		}
		if (y >= 600 - height) {// le perso est au sol
			y = 600 - height;
			jumpLeft = jumpMax;
		}
		
		
		// dégats
		if ( noDamage ){
			compteurFrame+=1;
		}		
		if(compteurFrame==120){
			noDamage = false;
			compteurFrame = 0;
		}	
		
		//game over
		if ( end ){
			container.exit();
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
			if(stockWeapon>0){
				new Weapon(this.x,this.y,direction);
				stockWeapon -= 1;
			}
			break;
		}
	}
	
	void damagePlayer(){
		//degats ennemis
		for (int i = 0; i< World.getEnemies().size(); i++)
		{
			if (Collisions.isCollisionRectRect(this, World.getEnemies().get(i)) && !noDamage){
				life -= 1;
				noDamage = true;
				System.out.println("Degat");
			}
		}
		
		//degats projectiles 
		for (int i = 0; i< World.getProjectiles().size(); i++)
		{
			if (Collisions.isCollisionRectRect(this, World.getProjectiles().get(i)) && !noDamage){
				life -= 1;
				noDamage = true;
			}
		}
		
		if ( life == 0 ){
			end = true;
		}
	}
	
	
	
}
