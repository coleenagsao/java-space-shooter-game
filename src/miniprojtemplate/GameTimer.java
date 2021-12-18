package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private ArrayList<Enemy> enemies;
	public static final int INITIAL_NUM_ENEMIES = 7;
	public static final int NUM_ENEMIES = 3;

	private long startSpawn;
	private long elapsedTime;
	private ArrayList<Boss> boss;
	private long startSpawn1;

	private ArrayList <Powerups> stars;			//temp
	private ArrayList <Powerups> plusHealths;	//temp

	private StatusBar statusbar;
	protected GameStage gameStage;		//temp

	public final Image bg = new Image("images/bg3.png",800,500,false,false);

	GameTimer(GraphicsContext gc, Scene theScene, StatusBar status, GameStage stage){
		this.gc = gc;

		this.theScene = theScene;
		this.myShip = new Ship("Going merry",150,250);
		this.gameStage = stage;

		this.startSpawn1 = System.nanoTime();

		this.statusbar = status;
		//instantiate the ArrayList of Fish
		this.enemies = new ArrayList<Enemy>();
		this.boss = new ArrayList<Boss>();

		this.startSpawn = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		this.elapsedTime = 5;

		this.stars = new ArrayList<Powerups>();			//temp
		this.plusHealths = new ArrayList<Powerups>();	//temp

		//call the spawnFishes method
		this.spawnEnemies(GameTimer.INITIAL_NUM_ENEMIES);

		this.spawnPowerups(1);
		this.spawnPowerups(2);

		this.spawnBoss();

		this.spawnEnemiesInterval();
		//call method to handle mouse click event
		this.handleKeyPressEvent();

		//this.timer = new Timer();
		this.runningTime();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc.drawImage(this.bg, 0, 0);

		//calls the move for ship, bullets, and fishes
		this.myShip.move();
		this.moveBullets(this.statusbar);
		this.moveEnemies();

		//render the ship, fishes, bullets, and powerups
		this.myShip.render(this.gc);
		this.renderBullets();
		this.renderEnemies();

		this.renderPowerups(1);
		this.renderPowerups(2);

		//checks if powerups are collected
		this.elapsedTime = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);

		this.checkPowerups(stars, 1, this.elapsedTime);
		this.checkPowerups(plusHealths, 2, this.elapsedTime);

		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn1);

		//set boss timer spawn
		Boss boss = this.boss.get(0);

		if((currentSec - startSec) > 30){
			if(boss.isAlive() == true){
				boss.setVisible(true);
				this.moveBoss();
				this.renderBoss();
			}
		}

		//win after 60 seconds
		if((currentSec - startSec) >= 60){
			gameStage.setGameOver(1, this.statusbar);
			this.stop();
		}

		//implement statusbar
		this.renderStatusBar(this.statusbar);
		this.statusbar.setStatusStrength(this.myShip.getStrength());
		this.statusbar.renderText();

		//check strength
		if (myShip.getStrength() <= 0){
			gameStage.setGameOver(0, this.statusbar);
			this.stop();
		}

	}

	//RENDER METHODS
	private void renderEnemies() {							//method that will render/draw the enemies to the canvas
		for (Enemy f : this.enemies){
			f.render(this.gc);
		}
	}

	private void renderPowerups(int ID){					//method that will render powerup depending on ID. 1 for star, 0 for plus
		if (ID == 1){
			for (Powerups stars : this.stars){
				stars.render(gc);
			}
		} else {
			for (Powerups plusHealths : this.plusHealths){
				plusHealths.render(gc);
			}
		}
	}

	private void renderBullets() {							//method that will render/draw the bullets to the canvas
		for (Bullet b: this.myShip.getBullets()){
			b.render(this.gc);
		}
	}

	private void renderBoss() {
		Boss boss = this.boss.get(0);
		boss.render(this.gc);
	}

	private void renderStatusBar(StatusBar status){
		status.getTime().render(this.gc);
		status.getStrength().render(this.gc);
		status.getScore().render(this.gc);
	}

	private void runningTime(){								//method for the timer in statusbar
		Timer timer = new Timer(1000, new ActionListener(){
	            	@Override
	            	public void actionPerformed(ActionEvent e) {
	            		statusbar.setStatusTime(1);
	            	}
	        });
	    timer.start();
	}

	//SPAWN METHODS
	private void spawnEnemies(int num){					  					//method that will spawn/instantiate three enemies at a random x,y location
		Random r = new Random();

		for(int i = 0; i < num; i++){
			int x = r.nextInt((GameStage.WINDOW_WIDTH/2) - 100) + 400;		//x should be on the right portion only
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - 130) + 50;

			this.enemies.add(new Enemy(x,y));
		}
	}

	private void spawnEnemiesInterval(){									//method that calls spawnEnemies per 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
            		@Override
            		public void actionPerformed(ActionEvent e) {
            			spawnEnemies(GameTimer.NUM_ENEMIES);
            		}
        	});

        timer.start();
	}

	private void spawnBoss(){												//method that will spawn bossfish on the right
		Random r = new Random();
		int x = r.nextInt((GameStage.WINDOW_WIDTH/2) - 100) + 400;
		int y = r.nextInt(GameStage.WINDOW_HEIGHT - 130) + 50;

		this.boss.add(new Boss(x,y));
	}

	private void spawnPowerupsR(int ID){								   //method that will spawn a powerup at a random left location
		Random r = new Random();

		for (int i = 0; i < 1; i++){
			int x = r.nextInt((GameStage.WINDOW_WIDTH - 100)/2) + 10; 	   //to spawn in left location only
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - 100) + 50;

			if (ID == 1) this.stars.add(new Powerups(ID,x,y));
			else this.plusHealths.add(new Powerups(ID,x,y));
		}
	}

	private void spawnPowerups(int ID){									 //method that will call spawnPowerup every 10 seconds
        Timer timer = new Timer(10000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	spawnPowerupsR(ID);
            }
        });
        timer.start();
	}

	//MOVE METHODS
	private void moveBullets(StatusBar status){							//method that will move the bullets shot by a ship
		ArrayList<Bullet> bList = this.myShip.getBullets();				//create a local arraylist of Bullets for the bullets 'shot' by the ship

		for(int i = 0; i < bList.size(); i++){							//loop through the bullet list and check whether a bullet is still visible.
			Bullet b = bList.get(i);
			if (b.isVisible()){ 							  			//if a bullet is visible, move the bullet
				b.move();

				//check if fishes collided with a bullet
				for(int j = 0; j < this.enemies.size(); j++){  			//iterates through the fish arraylist
					Enemy f = this.enemies.get(j);

					if (f.isVisible()){						 			//if fish is visible, actively checks if it collided with a bullet
						if (b.collidesWith(f)){
							f.die();
							b.setVisible(false);
							status.setStatusScore(1);					//update the score in the statusbar
						}
					}
				}
				Boss boss = this.boss.get(0);
				if(boss.isVisible()){
					if (b.collidesWith(boss)){
						boss.setHealth(this.myShip.getStrength());
						b.setVisible(false);
						if(boss.getHealth() <= 0){
							boss.die();
						}
					}
				}
			} else {													//else, remove the bullet from the bullet array list
				bList.remove(i);
			}
		}
	}

	private void moveEnemies(){											//method that will move the enemies
		for(int i = 0; i < this.enemies.size(); i++){					//loop through the enemies arraylist
			Enemy f = this.enemies.get(i);

			if(f.isAlive() == true){ 									//if a fish is alive, move the fish.
				if (f.collidesWith(myShip)){							//check if fish collides with a ship
					f.die();											//either ship is immortal or not, the enemy dies when hit
					if (myShip.getIsImmortal() == false){				//if ship is not immortal, 30 is substracted to ship
						myShip.setStrength(30);
					}
				}
				f.move();
			} else { 													//if enemy is dead, remove from the arraylist
				this.enemies.remove(f);
			}
		}
	}

	private void moveBoss(){
		Boss boss = this.boss.get(0);
		if(boss.isAlive() == true){
			boss.moveBoss();
			if (boss.collidesWith(this.myShip)){
				if (myShip.getIsImmortal() == false){
					myShip.setStrength(50);								//reduce the ship's strength by 50 when hit by boss
					boss.setMoveRight(true);
				}
			}
		} else {
			this.boss.remove(0);
		}
	}


	private void checkPowerups(ArrayList<Powerups> array, int ID, long elapsedTime){				//method that actively checks powerups
		if ((elapsedTime - myShip.getImmortalityStart()) == 3 && myShip.getIsImmortal() == true){ 	//checks if 3 second already passed after immortality is granted
			myShip.setIsImmortal(false);															//set the isImmortal as false
			myShip.setImmortalityStart(0); 															//reset the start timer to 0 again
			System.out.println("[IMMORTALITY EXPIRED] 3 seconds has passed");
		}

		for (int i = 0; i < array.size(); i++){
			Powerups p = array.get(i);
			if (p.visible == true){ 												//if still visible, check if ship collides with it
				if (p.collidesWith(myShip)){
					p.setVisible(false);
					if (ID == 2){
						myShip.setStrength(-30);									//add 30 to the strength of ship
					} else {
						myShip.setIsImmortal(true);
						myShip.setImmortalityStart(elapsedTime);
						System.out.println("[IMMORTALITY GRANTED] Immortality star has been collected.");
					}
				} if (elapsedTime - p.getStartSpawn() == 5){						//checks if 5 seconds already passed after powerups is spawned
					p.setVisible(false);											//set to invisible
				}
			} else array.remove(p);
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }



	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) this.myShip.setDY(-3);

		if(ke==KeyCode.LEFT) this.myShip.setDX(-3);

		if(ke==KeyCode.DOWN) this.myShip.setDY(3);

		if(ke==KeyCode.RIGHT) this.myShip.setDX(3);

		if(ke==KeyCode.SPACE) this.myShip.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	public Ship getShip(){
		return this.myShip;
	}

	Scene getScene(){
		return this.theScene;
	}
}