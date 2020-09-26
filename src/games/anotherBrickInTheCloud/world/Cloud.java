package games.anotherBrickInTheCloud.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class Cloud {

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

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		//TODO
		int origin = (int)charX / 800;
		for (int i = -1 + origin; i < 2 + origin; ++i)
			context.drawImage(texture, (float)positionX + 800*i, (float)positionY-300);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		//TODO
		positionX += delta * speed;

		positionX = (int)positionX % 800;
	}

}
