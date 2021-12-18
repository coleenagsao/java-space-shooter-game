package miniprojtemplate;


import javafx.scene.image.Image;

public class Boss extends Enemy{
	public final static int BOSS_WIDTH = 120;
	public final static Image BOSS_IMAGE = new Image("images/boss3.png",Boss.BOSS_WIDTH,Boss.BOSS_WIDTH,false,false);
	private int health;

	Boss(int x, int y) {
		super(x, y);
		this.health = 3000;
		super.visible = false;
		super.alive = true;
		this.loadImage(Boss.BOSS_IMAGE);
	}

	protected void moveBoss(){									//method similar with move of Enemy but different rightmost boundary
		if(this.moveRight == false && this.x >= 0){
			this.x -= this.speed;
			if(this.x <= 0){
				this.moveRight = true;
				this.move();
			}
		} else {
			this.x += this.speed;
			if(this.x >= (GameStage.WINDOW_WIDTH - 130)){	 //if the rightmost boundary is reached, move to the left
				this.moveRight = false;
				this.move();
			}
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int str) {
		this.health -= str;
	}

	public void setMoveRight(boolean value){
		this.moveRight = value;
	}
}
