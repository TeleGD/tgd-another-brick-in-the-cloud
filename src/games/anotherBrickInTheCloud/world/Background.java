package games.anotherBrickInTheCloud.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class Background {

	private float positionX;
	private float positionY;
	private Image texture;

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

	public Background(float positionX, float positionY, Image tex){
		this.positionX = positionX;
		this.positionY = positionY;
		this.texture = tex;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		//TODO
		/*int idImage=(int)(positionX/1.2f/800);
		for (int i=idImage-2;i<idImage+2;i++)
			context.drawImage(texture, (float)positionX/1.2f+i*800, (float)positionY-300);*/

		for(int i = (int)((positionX/800)-2);i<(positionX/800)+1;i++){
			context.drawImage(texture, (800*i), (float)positionY-300);
		}
	}

}
