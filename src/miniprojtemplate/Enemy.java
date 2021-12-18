package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Enemy extends Sprite {
	public static final int MAX_ENEMY_SPEED = 5;
	public final static Image ENEMY_IMAGE = new Image("images/boss2.png",Enemy.FISH_WIDTH,Enemy.FISH_WIDTH,false,false);
	public final static int FISH_WIDTH = 80;
	protected boolean alive;
	//attribute that will determine if a fish will initially move to the right
	protected boolean moveRight;
	protected int speed;


	Enemy(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Enemy.ENEMY_IMAGE);
		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt(5) + 1;
		this.moveRight = false;
	}

	//method that changes the x position of the fish
	protected void move(){
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
	public boolean isAlive() {
		return this.alive;
	}

	//setter
	protected void die(){ //temp
    	this.alive = false;
    }

	public void setDirection(boolean value){
		this.moveRight = value;
	}
}
