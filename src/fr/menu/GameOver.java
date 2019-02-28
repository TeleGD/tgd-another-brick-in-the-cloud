package fr.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.world.World;

public class GameOver extends BasicGameState{

	Image buttonReplay;
	Image buttonExit;
	Image pointeur;
	int selection;
	int nbrOption;
	static StateBasedGame game;
	static GameContainer container;
	public static int ID = 2;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		buttonReplay = new Image("images/buttonRejouer.png");
		buttonExit = new Image("images/buttonExit.png");
		pointeur = new Image("images/pointer.png");
		selection = 0;
		nbrOption = 2;

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		g.drawString("Game Over", 100, 50);
		g.drawString("C'était mieux avant !!!",100,70);
		buttonReplay.draw(150, 170);
		buttonExit.draw(150, 270);
		pointeur.draw(80,200+selection*100);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_DOWN: case Input.KEY_S:
			if (selection < nbrOption - 1)
				selection++;
			else
				selection = 0;
			break;
		case Input.KEY_UP: case Input.KEY_Z:
			if (selection > 0)
				selection--;
			else
				selection = nbrOption - 1;
			break;
		case Input.KEY_ENTER: case Input.KEY_SPACE:
			execOption();
			break;
		case Input.KEY_ESCAPE:
		container.exit();
			break;
		}
	}

	public void execOption() {
		switch (selection) {
		case 0:
			game.enterState(World.ID);
			break;
		case 1:
			container.exit();
		}
	}







}
