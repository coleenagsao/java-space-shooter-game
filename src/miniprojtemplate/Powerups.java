package miniprojtemplate;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class Powerups extends Sprite {

	public final static Image STAR_IMAGE = new Image("images/star.png", Powerups.POWERUP_WIDTH, Powerups.POWERUP_WIDTH, false, false);
	public final static Image PLUS_IMAGE = new Image("images/poro.png", Powerups.POWERUP_WIDTH, Powerups.POWERUP_WIDTH, false, false); //temp pic of poro
	public final static int POWERUP_WIDTH = 50;
	private int powerupID; //1 for star; 2 for health plus
	private boolean alive;
	private long startSpawn; //temp

	public Powerups(int powerupID, int x, int y) {
		super(x, y);
		this.powerupID = powerupID;
		this.alive = true;
		this.startSpawn = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());

		//System.out.println(this.startSpawn);

		if (powerupID == 1) this.loadImage(Powerups.STAR_IMAGE);
		else this.loadImage(Powerups.PLUS_IMAGE);

	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}

	//setter
	public void die(){ //temp
    	this.alive = false;
    }

	public long getStartSpawn(){
		return this.startSpawn;
	}
}
