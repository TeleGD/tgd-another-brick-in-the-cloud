package games.anotherBrickInTheCloud.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import games.anotherBrickInTheCloud.util.Rectangle;

public class Plateform implements Rectangle {

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
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		//TODO
		// affichage de toute les tuiles composant la plateforme
		for (int x = 0; x < sizeX; ++x)
			for (int y = 0; y < sizeY; ++y)
				context.drawImage(texture, (float)positionX + 32 * x, (float)positionY + 32 * y);
	}

}
