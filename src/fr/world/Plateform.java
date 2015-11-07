package fr.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Rectangle;

public class Plateform extends BasicGameState implements Rectangle {
	
	private double sizeX;	//nombre de tuiles suivant x
	private double sizeY;	//nombre de tuiles suivant y
	private double positionX;	//en pixel
	private double positionY;	//en pixel
	Image texture;
	
	public double getX()
	{
		return positionX;
		
	}
	
	public double getY()
	{
		return positionY;
	}
	
	public double getWidth()
	{
		return sizeX*32;
		
	}
	
	public double getHeight()
	{
		return sizeY*32;
	}
	
	public void setPosition(float newPositionX,float newPositionY)
	{
		positionX = newPositionX;
		positionY = newPositionY;
	}
	
	public void setSize(float newSizeX, float newSizeY)
	{
		sizeX = newSizeX;
		sizeY = newSizeY;
	}
	
	
	

	public Plateform(float sizeX, float sizeY, float positionX, float positionY, Image tex) {
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.positionX = positionX;
		this.positionY = positionY;
		texture=tex;
		World.getPlateforms().add(this);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//TODO
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//TODO
		// affichage de toute les tuiles composant la plateforme
		for (int x = 0; x < sizeX; ++x)
			for (int y = 0; y < sizeY; ++y)
				g.drawImage(texture, (float)positionX + 32 * x, (float)positionY + 32 * y);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//TODO
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
