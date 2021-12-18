package miniprojtemplate;

import java.util.concurrent.TimeUnit;
import javafx.scene.image.Image;

public class Powerups extends Sprite {
	private int powerupID;
	private long startSpawn; //temp

	public final static int POWERUP_WIDTH = 50;
	public final static Image STAR_IMAGE = new Image("images/star.gif", Powerups.POWERUP_WIDTH, Powerups.POWERUP_WIDTH, false, false);
	public final static Image PLUS_IMAGE = new Image("images/heart.gif", Powerups.POWERUP_WIDTH, Powerups.POWERUP_WIDTH, false, false); //temp pic of poro

	public Powerups(int powerupID, int x, int y) {
		super(x, y);
		this.powerupID = powerupID;
		this.startSpawn = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());

		if (powerupID == 1) this.loadImage(Powerups.STAR_IMAGE);
		else this.loadImage(Powerups.PLUS_IMAGE);

	}

	//getters
	protected long getStartSpawn(){
		return this.startSpawn;
	}
}
