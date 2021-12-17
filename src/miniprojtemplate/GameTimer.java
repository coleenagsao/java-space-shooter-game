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
	private ArrayList<Fish> fishes;
	public static final int INITIAL_NUM_FISHES = 7;
	public static final int NUM_FISHES = 3;

	private long startSpawn;
	private long elapsedTime;
	private ArrayList<BossFish> boss;
	private long startSpawn1;

	private ArrayList <Powerups> stars;			//temp
	private ArrayList <Powerups> plusHealths;	//temp

	private StatusBar statusbar;
	protected GameStage gameStage;		//temp

	public final Image bg = new Image("images/bg.png",800,500,false,false);

	GameTimer(GraphicsContext gc, Scene theScene, StatusBar status, GameStage stage){
		this.gc = gc;

		this.theScene = theScene;
		this.myShip = new Ship("Going merry",150,250);
		this.gameStage = stage;

		this.startSpawn1 = System.nanoTime();

		this.statusbar = status;
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.boss = new ArrayList<BossFish>();

		this.startSpawn = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		this.elapsedTime = 5;

		this.stars = new ArrayList<Powerups>();			//temp
		this.plusHealths = new ArrayList<Powerups>();	//temp

		//call the spawnFishes method
		this.spawnFishes(GameTimer.INITIAL_NUM_FISHES);

		this.spawnPowerups(1);
		this.spawnPowerups(2);

		this.spawnBossFish();

		this.spawnFishesInterval();
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
		this.moveFishes();

		//render the ship, fishes, bullets, and powerups
		this.myShip.render(this.gc);
		this.renderBullets();
		this.renderFishes();

		this.renderPowerups(1);
		this.renderPowerups(2);

		//checks if powerups are collected
		this.elapsedTime = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);

		this.checkPowerups(stars, 1, this.elapsedTime);
		this.checkPowerups(plusHealths, 2, this.elapsedTime);

		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn1);

		BossFish boss = this.boss.get(0);

		if((currentSec - startSec) > 5){
			if(boss.isAlive() == true){
				boss.setVisible(true);
				this.moveBoss();
				this.renderBossFish();
			}
		}

		if((currentSec - startSec) >= 60){
			gameStage.setGameOver(1);
			this.stop();
		}

		this.renderStatusBar(this.statusbar);
		this.statusbar.setStatusStrength(this.myShip.getStrength());
		this.statusbar.renderText();

		//check strength
		if (myShip.getStrength() <= 0){
			gameStage.setGameOver(0);
			this.stop();
		}

	}

	//method that will render/draw the fishes to the canvas
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}

	private void renderPowerups(int ID){
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

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		for (Bullet b: this.myShip.getBullets()){  //loop through the bullets arraylist of myShip
			b.render(this.gc);					   //render each bullet to the canvas
		}
	}

	//method that will spawn/instantiate three fishes at a random x,y location
	private void spawnFishes(int num){
		Random r = new Random();

		for(int i=0;i<num;i++){
			int x = r.nextInt(GameStage.WINDOW_WIDTH - 50);
			int y = (r.nextInt(GameStage.WINDOW_HEIGHT-100)+50);

			this.fishes.add(new Fish(x,y)); //Add a new object Fish to the fishes arraylist
		}
	}

	private void spawnFishesInterval(){
		Timer timer = new Timer(3000, new ActionListener(){
            		@Override
            		public void actionPerformed(ActionEvent e) {
            			spawnFishes(GameTimer.NUM_FISHES);
            			//System.out.println("Spawning fishes...");
            		}
        	});

        timer.start();
	}

	//method for the timer in statusbar
	private void runningTime(){
		Timer timer = new Timer(1000, new ActionListener(){
            		@Override
            		public void actionPerformed(ActionEvent e) {
            			statusbar.setStatusTime(1);
            		}
        	});

        timer.start();
	}

	//method that will spawn bossfish on the right
	private void spawnBossFish(){
		Random r = new Random();
		int x = (r.nextInt(GameStage.WINDOW_WIDTH/2) + 400);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT - 100);

		this.boss.add(new BossFish(x,y));
	}


	private void renderBossFish() {
		BossFish boss = this.boss.get(0);
		boss.render(this.gc);
	}


	//method that will spawn a powerup at a random left location
	private void spawnPowerupsR(int ID){
		Random r = new Random();

		for (int i = 0; i < 1; i++){
			int x = r.nextInt((GameStage.WINDOW_WIDTH - 50)/2); 		//to spawn in left location only
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - 50);

			if (ID == 1) this.stars.add(new Powerups(ID,x,y));
			else this.plusHealths.add(new Powerups(ID,x,y));
		}
	}

	//method that will call spawnPowerup every 10-seconds
	private void spawnPowerups(int ID){
        Timer timer = new Timer(10000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	spawnPowerupsR(ID);
            }
        });

        timer.start();
	}

	//method that will move the bullets shot by a ship
	private void moveBullets(StatusBar status){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			if (b.isVisible()){ 							  //if a bullet is visible, move the bullet
				b.move();

				//check if fishes collided with a bullet
				for(int j = 0; j < this.fishes.size(); j++){  //iterates through the fish arraylist
					Fish f = this.fishes.get(j);

					if (f.isVisible()){						 //if fish is visible, actively checks if it collided with a bullet
						if (b.collidesWith(f)){
							f.die();
							b.setVisible(false);
							status.setStatusScore(1);
						}
					}
				}
				BossFish boss = this.boss.get(0);
				if(boss.isVisible()){
					if (b.collidesWith(boss)){
						boss.setHealth(this.myShip.getStrength());
						b.setVisible(false);
						if(boss.getHealth() <= 0){
							boss.die();
						}
					}
				}

			} else {				//else, remove the bullet from the bullet array list
				bList.remove(i);
			}
		}
	}

	//method that will move the fishes
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);

			if(f.isAlive() == true){ //if a fish is alive, move the fish.
				//check if fish collides with a ship
				if (f.collidesWith(myShip)){
					f.die();
					if (myShip.getIsImmortal() == false){
						myShip.setStrength(30);
						if (myShip.getStrength() <= 0){
							System.out.println("You died!");
						}
					}
					System.out.println(myShip.getStrength());
				}
				f.move();

			} else { //else, remove the fish from the fishes arraylist
				this.fishes.remove(f);
			}
		}
	}

	private void moveBoss(){
		BossFish boss = this.boss.get(0);
		if(boss.isAlive() == true){
			boss.move();
			if (boss.collidesWith(this.myShip)){
				if (myShip.getIsImmortal() == false){
					myShip.setStrength(15);
					boss.setMoveRight(true);
				}
				boss.move();
				System.out.println(myShip.getStrength());
			}
		} else {
			this.boss.remove(0);
		}
	}

	//HOW TO CHECK COLLISSION WITH SHIP

	private void checkPowerups(ArrayList<Powerups> array, int ID, long elapsedTime){
		//check immortality
		if ((elapsedTime - myShip.getImmortalityStart()) == 3 && myShip.getIsImmortal() == true){  //NOT EXPIRING SOMETIMES, FOR THE 2ND TIME
			System.out.println("[IMMORTALITY EXPIRED] 3 seconds has passed");
			myShip.setIsImmortal(false);	//set the isImmortal as false
			myShip.setImmortalityStart(0); 	//reset the start timer to 0 again
		}
		for (int i = 0; i < array.size(); i++){
			Powerups p = array.get(i);
			if (p.visible == true){ 			//if still visible check if ship collides
				if (p.collidesWith(myShip)){
					p.setVisible(false);
					if (ID == 2){
						System.out.println("Original: " + myShip.getStrength());
						myShip.setStrength(-30);
						System.out.println("New Strength:" + myShip.getStrength()); //temp
					} else {
						System.out.println("[IMMORTALITY GRANTED]");
						myShip.setIsImmortal(true);
						myShip.setImmortalityStart(elapsedTime);
						//System.out.println("Start of Immortality:" + myShip.getImmortalityStart());
					}
				} if (elapsedTime - p.getStartSpawn() == 5){
					p.setVisible(false);
				}
			} else array.remove(p);
		}
	}

	private void renderStatusBar(StatusBar status){
		status.getTime().render(this.gc);
		status.getStrength().render(this.gc);
		status.getScore().render(this.gc);
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
		if(ke==KeyCode.UP) this.myShip.setDY(-3); //changed from 3 to 10

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