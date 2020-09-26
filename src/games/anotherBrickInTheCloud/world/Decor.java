package games.anotherBrickInTheCloud.world;

import java.util.ArrayList;
import java.util.List;//ajouté
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Decor {

	public enum Direction {HAUT,DROITE,BAS,GAUCHE};

	private List<Plateform> plateforms;
	private int characterPosX;
	private int characterPosY;
	private Direction cameraDirection;
	private boolean cameraMove;
	private Image plateformTexture;
	private Background background;
	private Cloud backgroundCloud;
	private Cloud backgroundCloud2;

	private int negativeLimit;
	private int positiveLimit;

	public Decor(String plateformTexturePath, String backgroundTexturePath, String cloudTexturePath) {
		plateforms = new ArrayList<Plateform>();	//ensemble des plateformes crées
		characterPosX=0;	//variable servant pour la caméra
		characterPosY=0;	//variable servant pour la caméra
		cameraDirection = Direction.HAUT;	//Direction du défilement de caméra
		cameraMove = false;		// caméra en train de bouger dans cameraDirection
		plateformTexture = AppLoader.loadPicture(plateformTexturePath);	// chargement image pour plateforme
		background = new Background(characterPosX, characterPosY, AppLoader.loadPicture(backgroundTexturePath));
		backgroundCloud = new Cloud(characterPosX, characterPosY, AppLoader.loadPicture(cloudTexturePath), 0.1f);
		backgroundCloud2 = new Cloud(characterPosX - 100, characterPosY, AppLoader.loadPicture(cloudTexturePath), 0.12f);
		createPlateform(-100,600-32,100, 1);	//sol
		generatePlateform(-800);
		generatePlateform(0);
		generatePlateform(800);

		negativeLimit = -1;
		positiveLimit = 1;
	}

	public void createPlateform(float posX, float posY, float sizeX, float sizeY)
	{
		//ajout d'une plateforme
		plateforms.add(new Plateform(sizeX, sizeY , posX , posY, plateformTexture));
	}

	public List<Plateform> getObstacles()
	{
		return plateforms;
	}

	public void updateCharacterPosition(int posX, int posY)
	{
		characterPosX = posX;
		characterPosY = posY;
	}

	public void generatePlateform(float charPosX)
	{
		Random rand = new Random();
		List<Integer> positions = new ArrayList<Integer>();
		List<Integer> sizes = new ArrayList<Integer>();

		for (int i = 0; i < 10; ++i)
		{
			boolean ok = false;

			while (!ok)
			{
				int posX = rand.nextInt(25) * 32 + (int)charPosX;
				int posY = rand.nextInt(16) * 32;
				int sizeX = rand.nextInt(8) + 3;
				int sizeY = rand.nextInt(2) + 1;

				for (int j = 0; j < positions.size()/2; ++j)
				{
					if (Math.abs(posX - positions.get(j * 2)) < (sizeX + sizes.get(j * 2))*32 / 2 &&
							Math.abs(posY - positions.get(j * 2 + 1)) < (sizeY + sizes.get(j * 2 + 1))*32 / 2)
					{
						ok = true;
						break;
					}
				}

				if (!ok)
				{
					positions.add(posX);
					positions.add(posY);
					sizes.add(sizeX);
					sizes.add(sizeY);
					createPlateform(posX, posY , sizeX, sizeY);
					ok = true;
				}
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		//TODO
		//translation de la caméra (fixé sur le personnage)
		context.translate(container.getWidth() / 2 - characterPosX, container.getHeight() / 2 - characterPosY);

		//rendu du fond d'écran
		background.render(container, game, context);
		backgroundCloud.render(container, game, context);
		backgroundCloud2.render(container, game, context);

		//rendu des plateformes
		for(int i = 0; i<plateforms.size();i++){
			plateforms.get(i).render(container, game, context);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		//TODO
		float speed = 1f;
		/*if (cameraMove)
		{
			//modif de la position de la caméra
			switch (cameraDirection) {
		        case HAUT: characterPosY-=delta*speed;    break;
		        case GAUCHE: characterPosX-=delta*speed;  break;
		        case BAS:  characterPosY+=delta*speed; break;
		        case DROITE: characterPosX+=delta*speed; break;
		    }
		}*/
		//maj du fond d'écran
		background.setPosition(characterPosX, characterPosY);
		backgroundCloud.setPosition(backgroundCloud.getX(), characterPosY + 20);
		backgroundCloud.setCharacterPosition(characterPosX, characterPosY);
		backgroundCloud.update(container, game, delta);
		backgroundCloud2.setPosition(backgroundCloud2.getX(), characterPosY + 100);
		backgroundCloud2.setCharacterPosition(characterPosX, characterPosY);
		backgroundCloud2.update(container, game, delta);

		if (characterPosX > positiveLimit * 800)
		{
			positiveLimit++;
			generatePlateform(800 * positiveLimit);
		}
		else if (characterPosX < (negativeLimit + 1) * 800)
		{
			negativeLimit--;
			generatePlateform(800 * negativeLimit);
		}
	}

	public void keyReleased(int key, char c) {
		//TODO
		//annulation de la caméra
		switch (key) {
        case Input.KEY_UP: cameraMove=false;    break;
        case Input.KEY_LEFT: cameraMove=false; break;
        case Input.KEY_DOWN:  cameraMove=false; break;
        case Input.KEY_RIGHT: cameraMove=false; break;
    }
	}

	public void keyPressed(int key, char c) {
		//TODO
		//caméra en fonction des touches
		float speed = 1f;
		switch (key) {
	        case Input.KEY_UP: cameraDirection=Direction.HAUT; cameraMove=true;    break;
	        case Input.KEY_LEFT: cameraDirection=Direction.GAUCHE; cameraMove=true; break;
	        case Input.KEY_DOWN:  cameraDirection=Direction.BAS;cameraMove=true; break;
	        case Input.KEY_RIGHT: cameraDirection=Direction.DROITE;cameraMove=true; break;
	    }
	}

}
