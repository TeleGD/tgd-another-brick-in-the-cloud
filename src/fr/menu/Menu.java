package fr.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.world.World;

public class Menu extends BasicGameState{
	
	Image buttonPlay;
	Image buttonExit;
	Image pointeur;
	int selection;
	int nbrOption;
	static StateBasedGame game;
	static GameContainer container;
	public static int ID = 1;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		buttonPlay = new Image("assets/buttonJouer.png");
		buttonExit = new Image("assets/buttonExit.png");
		pointeur = new Image("assets/pointer.png");
		selection = 0;
		nbrOption = 2;
		this.game = game;
		this.container = container;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("Very Bad WEI", 100, 50);
		buttonPlay.draw(150, 170);
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
