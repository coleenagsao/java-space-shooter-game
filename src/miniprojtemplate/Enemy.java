package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Enemy extends Sprite {
	protected boolean alive;
	protected boolean moveRight;
	protected int speed;

	public final static int MAX_ENEMY_SPEED = 5;
	public final static int ENEMY_WIDTH = 80;
	public final static Image ENEMY_IMAGE = new Image("images/boss2.png",Enemy.ENEMY_WIDTH,Enemy.ENEMY_WIDTH,false,false);

	Enemy(int x, int y){
		super(x,y);
		this.alive = true;
		this.moveRight = false;

		Random r = new Random();
		this.speed = r.nextInt(5) + 1;  //set movement between 1 - 5 only

		this.loadImage(Enemy.ENEMY_IMAGE);
	}

	protected void move(){									//method that changes the x position of the fish
		if(this.moveRight == false && this.x >= 0){
			this.x -= this.speed;
			if(this.x <= 0){
				this.moveRight = true;
				this.move();
			}
		} else {
			this.x += this.speed;
			if(this.x >= (GameStage.WINDOW_WIDTH - 80)){
				this.moveRight = false;
				this.move();
			}
		}
	}

	//getter
	protected boolean isAlive() {
		return this.alive;
	}

	//setter
	protected void die(){
    	this.alive = false;
    }

}
