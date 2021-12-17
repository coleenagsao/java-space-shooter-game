package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Ship extends Sprite{
	private String name;
	private int strength;
	private boolean alive;

	private boolean isImmortal; //temp
	private long ImmortalityStart; //temp

	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE = new Image("images/ship.gif",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH,false,false);
	private final static int SHIP_WIDTH = 70;

	public Ship(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(151) +100;

		this.isImmortal = false;
		this.ImmortalityStart = 0;

		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE);
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width+20);
		int y = (int) ((this.y + this.height/2) - 10);

		//instantiate a new bullet
		Bullet bullet = new Bullet(x,y);

		//add to the bullets arraylist of ship
		this.getBullets().add(bullet);
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */

		int currentX = this.x + this.dx;
		int currentY = this.y + this.dy;

		if (currentX < (GameStage.WINDOW_WIDTH - 50) && currentY < (GameStage.WINDOW_HEIGHT - 50) && currentX > 0 && currentY > 0){
			this.x = currentX;
			this.y = currentY;
			this.setVisible(true);
		} else this.setVisible(false);
	}

	//getters
	public int getStrength(){
		return this.strength;
	}

	public boolean getIsImmortal(){
		return this.isImmortal;
	}

	public long getImmortalityStart(){
		return this.ImmortalityStart;
	}

	//setter
	public void setStrength(int value){
		this.strength -= value;
	}

	public void setIsImmortal(boolean value){
		this.isImmortal = value;
	}

	public void setImmortalityStart(long value){
		this.ImmortalityStart = value;
	}

}
