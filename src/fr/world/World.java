package fr.world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.character.Enemy;
import fr.entity.character.Player;
import fr.entity.weapon.Weapon;

public class World extends BasicGameState{
	
	public enum direction {HAUT,DROITE,BAS,GAUCHE};
	private static Player player;
	private static ArrayList<Enemy> enemies;
	private static Enemy enemyTest;
	private static ArrayList<Weapon> projectiles;
	private static ArrayList<Plateform> plateforms;
	private Decor decor;
	public static int ID = 0;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		player = new Player();
		enemies = new ArrayList<Enemy>();
		enemyTest = new Enemy();
		projectiles = new ArrayList<Weapon>();
		plateforms = new ArrayList<Plateform>();
		decor = new Decor("assets/brick.png","assets/background.png", "assets/cloud.png");
		decor.init(container,game);
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		decor.render(container,game,g);
		player.render(container, game, g);
		for(int i = 0; i<enemies.size();i++){
			enemies.get(i).render(container, game, g);
		}
		for(int i = 0; i<projectiles.size();i++){
			projectiles.get(i).render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
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
	
	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
		decor.keyReleased(key,c);
	}


	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
		decor.keyPressed(key,c);
	}

	@Override
	public int getID() {
		return ID;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player playerP) {
		player = playerP;
	}
	
	public static ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	public static ArrayList<Weapon> getProjectiles(){
		return projectiles;
	}
	public static ArrayList<Plateform> getPlateforms(){
		return plateforms;
	}	
}

