package games.anotherBrickInTheCloud.entities.characters;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.anotherBrickInTheCloud.World;
import games.anotherBrickInTheCloud.entities.Weapon;
import games.anotherBrickInTheCloud.util.Collisions;
import games.anotherBrickInTheCloud.util.Movable;
import games.anotherBrickInTheCloud.util.Rectangle;

public class Enemy extends Movable implements Rectangle {

	private World world;
	private double speedMax; // Définit la vitesse max que les ennemis atteindront.
	private int hp; // Points de vie des ennemis, à 1 pour commencer.
	private static int numberEnemy = 0; // On numérote nos ennemis pour pouvoir gérer leur suppression via une liste.
	private int id;
	private int numberFrame = 0;
	private int i; //  Variable pour un compteur
	private int agroDistance = 120;
	private boolean gauche;
	private Image arret, marched, marcheg;

	public Enemy (World world) {
	this.world = world;
	hp = 1; // On donne une valeur à hp
	x = r.nextInt(800-(int)width); // On place l'ennemi à une position aléatoire sur l'axe x
	testXPlayer(); // On vérifie qu'on ne fait pas spawn SUR le joueur (reste à gérer les obstacles)
	y = 600-74; // On pose l'ennemi sur le sol /!\ Gestion de la collision gravité avec les plateformes à débugger.
	accelX = 0.02*r.nextDouble() + 0.005; // Accélération aléatoire définie ici (entre 0.025 et 0.005)
	speedMax = (r.nextInt(14))/500 + 0.2; // vitesse max aléatoire définie ici entre 1 et 15 (ou 14)
	isMoving = true; // Un booleen au cas où le joueur puisse arrêter le déplacement de l'ennemi
	id = numberEnemy; // On donne une id à notre ennemi
	numberEnemy++; // On incrémente le nombre d'ennemis déjà créés
	// arret = AppLoader.loadPicture("D:/Camille/Documents/Coding night/IMAGEEEEEE/tgd-cn-v/mechant_milieu_poignee_verso.png");
	// marcheg = AppLoader.loadPicture("D:/Camille/Documents/Coding night/IMAGEEEEEE/tgd-cn-v/mechant_droite_poignee_verso.png");
	// marched = AppLoader.loadPicture("D:/Camille/Documents/Coding night/IMAGEEEEEE/tgd-cn-v/mechant_gauche_poignee_verso.png");
	arret = AppLoader.loadPicture("/images/anotherBrickInTheCloud/placeholder.png");
	marcheg = AppLoader.loadPicture("/images/anotherBrickInTheCloud/placeholder.png");
	marched = AppLoader.loadPicture("/images/anotherBrickInTheCloud/placeholder.png");
	}

	Random r = new Random(); // On se crée une variable pour utiliser la fonction random

	public void render(GameContainer container, StateBasedGame game, Graphics context) { // gestion de l'affichage
		//context.setColor(Color.magenta); // Rectangle de test type ennemi de couleur magenta (ici on choisit la couleur du pinceau)
		//context.fillRect((float)x, (float)y, (float)width, (float)height); // On créé le rectangle
		context.drawImage(arret, (float)x, (float)y-16);
		/*if (gauche){
			if ((numberFrame/30)%4==0 || (numberFrame/30)%4==2){
				context.drawImage(arret, (float)x, (float)y-16);
			}
			if ((numberFrame/30)%4==1){
				context.drawImage(marcheg, (float)x, (float)y-16);
			}
			else{
				context.drawImage(marched,  (float)x,  (float)y-16);
			}
		}*/
	}

	public void update(GameContainer container, StateBasedGame game, int delta) { // gestion des updates à chaque frame
		accelY=0.004;
		fallHandling();
		if (speedX>=0){
			gauche = false;
		}
		else{
			gauche = true;
		}
		speedY+=accelY;
		moveX(delta);
		moveY(delta);
		limitX();
		damageEnemy();
		deathEnemy();
		backX();
		backY();
		shoot();
		initiative();
		numberFrame++;
	}

	public void testXPlayer(){ //permet de reset position au spawn si collision ac joueur : OK
		while (Collisions.isCollisionRectRect(this,world.getPlayer())){ // Tant qu'il y a collision (gérer le cas "apparition next le joueur")
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
		if (Collisions.isCollisionRectRect(this, world.getPlayer())) // Si on collisionne le player
		{
			if (y> world.getPlayer().getY()) // Si on est en dessous du player
			{
				hp--; // On perd un hp
			}
		}
		for (i = 0; i< world.getProjectiles().size(); i++)
		{
			if (Collisions.isCollisionRectRect(this,world.getProjectiles().get(i)))
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
			while (world.getEnemies().get(i).id!=id) // On parcourt la liste des ennemis
			{
				i++;
			}
			world.getEnemies().remove(world.getEnemies().get(i));// Enlève l'ennemi avec l'id "id" de la liste ennemis
		}
	}

	public void backX(){ //gère la collision sur les bords de la map OK
		if (x>768 || x<0)
		{
			accelX=-accelX; // On change de direction en cas de frappage de bord de map
			speedX=-speedX; // Pour la vitesse et l'accélération
		}
		for (i = 0 ; i <= world.getPlateforms().size() - 1 ; i++){ //change de sens si collision contre plateforme
			if (Collisions.isCollisionRectRect(this,world.getPlateforms().get(i))){
				if (x+this.width<=world.getPlateforms().get(i).getX() || x>=world.getPlateforms().get(i).getX()+world.getPlateforms().get(i).getWidth()){
					speedX=-speedX;
					accelX=-accelX;
				}
			}
		}
	}

	public void initiative(){ //l'ennemi se dirige vers le joueur en dessous d'une certaine distance OK
		if (Math.sqrt((Math.pow(x-world.getPlayer().getX(),2)+Math.pow(y-world.getPlayer().getY(),2))) <= agroDistance){ // Si la distance en module est inférieure à la distance d'agro
			if (x-world.getPlayer().getX() <= 0){ // Si on est à gauche du joueur
				speedX=Math.abs(speedX); // On se dirige vers la droite
				accelX=Math.abs(accelX); // On accélère vers la droite
			}
			else{//Sinon
				speedX=-Math.abs(speedX); // On se dirige vers la gauche
				accelX=-Math.abs(accelX); // On accélère vers la gauche
			}
		}
	}

	public void fallHandling(){ //gestion de la chute sur les obstacles
		{
			/*for (i = 0 ; i <= world.getPlateforms().size()-1 ; i++){
				if(Collisions.isCollisionRectRect(this,world.getPlateforms().get(i))){
					if(world.getPlateforms().get(i).getY()+world.getPlateforms().get(i).getHeight()>y){ //collision lors d'un saut
						speedY=-speedY;
					}
					if(world.getPlateforms().get(i).getY()<y+height-7){ //collision gravité
						accelY=0;
						y--;
						speedY=0;
					}
				}
			}*/
			for (i=0;i<=world.getPlateforms().size()-1;i++){
				if (Collisions.isCollisionRectRect(this, world.getPlateforms().get(i))){
					if (y+height-7<=world.getPlateforms().get(i).getY()-1){
						accelY=0;
						speedY=0;
					}
				}
			}
		}
	}

	public void shoot(){ //tire OK
		if (numberFrame%100==0){
			if(speedX>0)
			{
			   world.getProjectiles().add(new Weapon(this.x+33,this.y+20, !gauche));
			}
			if (speedX<=0)
			{
				world.getProjectiles().add(new Weapon(this.x-1,this.y+20,gauche));
			}
		}
	}

	public void backY(){ //on touche plus au sol (enfin si mais bref)
		if (y >= 600 - height - world.getPlateforms().get(0).getHeight() - 7){
			y = 600 - height - world.getPlateforms().get(0).getHeight()- 7 - 1;
		}
	}

}
