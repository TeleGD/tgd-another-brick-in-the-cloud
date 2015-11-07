package fr.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Cloud extends BasicGameState{
	private float positionX;
	private float positionY;
	private float charX;
	private float charY;
	private Image texture;
	float speed;
	
	float getX()
	{
		return positionX;
		
	}
	
	float getY()
	{
		return positionY;
		
	}
	
	void setPosition(float newPositionX,float newPositionY)
	{
		positionX = newPositionX;
		positionY = newPositionY;
	}
	
	void setCharacterPosition(float newCharX,float newCharY)
	{
		charX = newCharX;
		charY = newCharY;
	}

	public Cloud(float positionX, float positionY, Image tex, float speed){
		
		this.positionX = positionX;
		this.positionY = positionY;
		this.texture = tex;
		this.speed = speed;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//TODO
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//TODO
		int origin = (int)charX / 800;
		for (int i = -1 + origin; i < 2 + origin; ++i)
			g.drawImage(texture, (float)positionX + 800*i, (float)positionY-300);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//TODO
		positionX += delta * speed;
		
		positionX = (int)positionX % 800;
	}
	
	public void keyReleased(int key, char c) {
		//TODO
	}


	public void keyPressed(int key, char c) {
		//TODO
	}

	@Override
	public int getID() {
		return 0;
	}
}
