package miniprojtemplate;


import javafx.scene.image.Image;

public class BossFish extends Fish{
	public final static int BOSS_FISH_WIDTH = 130;
	public final static Image BOSS_FISH_IMAGE = new Image("images/boss.gif",BossFish.BOSS_FISH_WIDTH,BossFish.BOSS_FISH_WIDTH,false,false);
	private int health;

	BossFish(int x, int y) {
		super(x, y);
		this.loadImage(BossFish.BOSS_FISH_IMAGE);
		this.health = 3000;
		super.visible = false;
		super.alive = true;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int str) {
		this.health -= str;
		System.out.println("hit " + this.health);
	}

	public void setMoveRight(boolean value){
		this.moveRight = value;
	}

}
