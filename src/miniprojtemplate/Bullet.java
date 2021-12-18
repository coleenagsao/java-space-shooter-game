package miniprojtemplate;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	private final static int BULLET_SPEED = 10;
	public final static int BULLET_WIDTH = 20;
	public final static Image BULLET_IMAGE = new Image("images/bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);


	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
	}

	public void move(){										//method that will move/change the x position of the bullet
		this.x += Bullet.BULLET_SPEED;  					//change the x position of the bullet depending on the bullet speed.

		if (this.x > GameStage.WINDOW_WIDTH - 50){			//if the x position has reached the right boundary of the screen,
			this.setVisible(false);	   						//set the bullet's visibility to false
		}
	}
}