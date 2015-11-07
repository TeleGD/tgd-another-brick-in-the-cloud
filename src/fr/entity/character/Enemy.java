package fr.entity.character;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.weapon.Weapon;
import fr.util.Collisions;
import fr.util.Movable;
import fr.util.Rectangle;
import fr.world.World;

public class Enemy extends Movable implements Rectangle {
	
	private double speedMax; // Définit la vitesse max que les ennemis atteindront.
	private int hp; // Points de vie des ennemis, à 1 pour commencer.
	private static int numberEnemy = 0; // On numérote nos ennemis pour pouvoir gérer leur suppression via une liste.
	private int id;
	private int numberFrame = 0;
	private int i; //  Variable pour un compteur
	private int agroDistance = 90;
	private int agroShootDistance = 400;
	private boolean gauche;
	
	public Enemy () {
	super(); // récupère les attributs de la classe Movable
	hp = 2; // On donne une valeur à hp
	x = r.nextInt(800-(int)width); // On place l'ennemi à une position aléatoire sur l'axe x
	testXPlayer(); // On vérifie qu'on ne fait pas spawn SUR le joueur (reste à gérer les obstacles)
	y = 600-32-(int)height; // On pose l'ennemi sur le sol
	accelX = 0.02*r.nextDouble() + 0.005; // Accélération aléatoire définie ici (entre 0.025 et 0.005)
	speedMax = (r.nextInt(14))/500 + 0.2; // vitesse max aléatoire définie ici entre 1 et 15 (ou 14)
	isMoving = true; // Un booleen au cas où le joueur puisse arrêter le déplacement de l'ennemi
	World.getEnemies().add(this); //On ajoute l'ennemi actuel à la liste des ennemis
	id = numberEnemy; // On donne une id à notre ennemi
	numberEnemy++; // On incrémente le nombre d'ennemis déjà créés
	}
	
	Random r = new Random(); // On se crée une variable pour utiliser la fonction random
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException { // gestion de l'affichage
		g.setColor(Color.magenta); // Rectangle de test type ennemi de couleur magenta (ici on choisit la couleur du pinceau)
		g.fillRect((float)x, (float)y, (float)width, (float)height); // On créé le rectangle
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException { // gestion des updates à chaque frame
		moveX(delta);
		limitX();
		shoot();
		damageEnemy();
		deathEnemy();
		backX();
		initiative();
		numberFrame++;
	}
	
	public void testXPlayer(){ //permet de reset position au spawn si collision ac joueur : OK
		while (Collisions.isCollisionRectRect(this,World.getPlayer())){ // Tant qu'il y a collision (gérer le cas "apparition next le joueur")
			x = r.nextInt(800-(int)width); // On remet un X aléatoire
		}
	}
	
	public void limitX(){ //limite la vitesse OK
		if (Math.abs(speedX)<speedMax) //Tant qu'on ne va pas trop vite
		{
			speedX+=accelX; // On accélère
		}
	}
	
	public void damageEnemy(){ //diminue les points de vie si on subit des dégâts OK
		if (Collisions.isCollisionRectRect(this, World.getPlayer())) // Si on collisionne le player
		{
			if (y> World.getPlayer().getY()) // Si on est en dessous du player
			{
				hp--; // On perd un hp
			}
		}
		for (i = 0; i< World.getProjectiles().size(); i++)
		{
			if (Collisions.isCollisionRectRect(this,World.getProjectiles().get(i)))
			{
				hp--;
			}
		}
		if (y>600) // Si on sort de l'écran par le bas
		{
			hp = 0; // On passe à 0 hp
			
		}
	}
	
	public void deathEnemy(){ //on meurt si on a plus de points de vie, supprime l'entité OK
		if (hp <= 0)
		{
			i = 0;
			while (World.getEnemies().get(i).id!=id) // On parcourt la liste des ennemis
			{
				i++;
			}
			World.getEnemies().remove(World.getEnemies().get(i));// Enlève l'ennemi avec l'id "id" de la liste ennemis
		}
	}

	
	public void backX(){ //gère la collision sur les bords de la map OK
		if (x>768 || x<0)
		{
			accelX=-accelX; // On change de direction en cas de frappage de bord de map
			speedX=-speedX; // Pour la vitesse et l'accélération
		}
	}
	
	public void initiative(){ //l'ennemi se dirige vers le joueur en dessous d'une certaine distance OK
		if (Math.sqrt((Math.pow(x-World.getPlayer().getX(),2)+Math.pow(y-World.getPlayer().getY(),2))) <= agroDistance){ // Si la distance en module est inférieure à la distance d'agro
			if (x-World.getPlayer().getX() <= 0){ // Si on est à gauche du joueur
				speedX=Math.abs(speedX); // On se dirige vers la droite
				accelX=Math.abs(accelX); // On accélère vers la droite
			}
			else{//Sinon
				speedX=-Math.abs(speedX); // On se dirige vers la gauche
				accelX=-Math.abs(accelX); // On accélère vers la gauche
			}
				
		}
	}
	
	public void fallHandling(){ //gestion de la chute (on attend la liste des obstacles)
		{
			
		}
	}
	
	public void shoot(){ //tire OK
		if (numberFrame%100==0){
			if(speedX>0)
			{
			   new Weapon(this.x+1,this.y+10, !gauche);
			}
			if (speedX<=0)
			{
				new Weapon(this.x-33,this.y+10,gauche);
			}
		}
	}
}
