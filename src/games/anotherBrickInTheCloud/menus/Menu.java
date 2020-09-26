package games.anotherBrickInTheCloud.menus;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

public class Menu extends BasicGameState{

	private int ID;

	Image buttonPlay;
	Image buttonExit;
	Image pointeur;
	int selection;
	int nbrOption;
	static StateBasedGame game;
	static GameContainer container;

	public Menu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		buttonPlay = AppLoader.loadPicture("/images/anotherBrickInTheCloud/buttonJouer.png");
		buttonExit = AppLoader.loadPicture("/images/anotherBrickInTheCloud/buttonExit.png");
		pointeur = AppLoader.loadPicture("/images/anotherBrickInTheCloud/pointer.png");
		selection = 0;
		nbrOption = 2;
		this.game = game;
		this.container = container;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawString("Very Bad WEI", 100, 50);
		buttonPlay.draw(150, 170);
		buttonExit.draw(150, 270);
		pointeur.draw(80,200+selection*100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
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
			game.enterState(0 /* Welcome */, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

	public void execOption() {
		switch (selection) {
		case 0:
			game.enterState(3 /* World */, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			game.enterState(0 /* Welcome */, new FadeOutTransition(), new FadeInTransition());
		}
	}

}
