package games.anotherBrickInTheCloud;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import games.anotherBrickInTheCloud.entities.Weapon;
import games.anotherBrickInTheCloud.entities.characters.Enemy;
import games.anotherBrickInTheCloud.entities.characters.Player;
import games.anotherBrickInTheCloud.world.Decor;
import games.anotherBrickInTheCloud.world.Plateform;

public class World extends BasicGameState{

	private int ID;
	private int state;

	public enum direction {HAUT,DROITE,BAS,GAUCHE};
	private Player player;
	private List<Enemy> enemies;
	private List<Weapon> projectiles;
	private Decor decor;

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2 /* Pause */, new FadeOutTransition(), new FadeInTransition());
		}
		decor.render(container, game, context);
		player.render(container, game, context);
		for(int i = 0; i<enemies.size();i++){
			enemies.get(i).render(container, game, context);
		}
		for(int i = 0; i<projectiles.size();i++){
			projectiles.get(i).render(container, game, context);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		decor.updateCharacterPosition((int)player.getX(), (int)player.getY());

		decor.update(container,game,delta);
		player.update(container, game, delta);
		for(int i = 0; i<enemies.size();i++){
			enemies.get(i).update(container, game, delta);
		}
		for(int i = 0; i<projectiles.size();i++){
			projectiles.get(i).update(container, game, delta);
		}
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		player = new Player(this);
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(this));
		projectiles = new ArrayList<Weapon>();
		decor = new Decor("/images/anotherBrickInTheCloud/brick.png","/images/anotherBrickInTheCloud/background.png", "/images/anotherBrickInTheCloud/cloud.png");
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
		decor.keyReleased(key,c);
	}

	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
		decor.keyPressed(key,c);
	}

	public Player getPlayer() {
		return player;
	}

	public List<Enemy> getEnemies(){
		return enemies;
	}

	public List<Weapon> getProjectiles(){
		return projectiles;
	}

	public List<Plateform> getPlateforms(){
		return decor.getObstacles();
	}

}
