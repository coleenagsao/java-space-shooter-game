package miniprojtemplate;


import javafx.scene.image.Image;

public class Boss extends Enemy{
	public final static int BOSS_WIDTH = 120;
	public final static Image BOSS_IMAGE = new Image("images/boss3.png",Boss.BOSS_WIDTH,Boss.BOSS_WIDTH,false,false);
	private int health;

	Boss(int x, int y) {
		super(x, y);
		this.loadImage(Boss.BOSS_IMAGE);
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
