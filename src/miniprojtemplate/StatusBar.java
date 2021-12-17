package miniprojtemplate;

import javafx.scene.text.Text;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StatusBar {

	private Text time;
	private Text strength;
	private Text score;
	private int timeRemaining;
	private int elapsedTime;
	private int currentScore;
	private int currentStrength;

	private Sprite Time;
	private Sprite Strength;
	private Sprite Score;
	public final static Image TIME_IMAGE = new Image("images/timer.png",50,55,false,false);
	public final static Image STRENGTH_IMAGE = new Image("images/health.png",50,45,false,false);
	public final static Image SCORE_IMAGE = new Image("images/boss2.png",45,45,false,false);

	StatusBar(){
		this.timeRemaining = 60;
		this.elapsedTime = 0;
		this.currentScore = 0;
		this.time = new Text(350, 40, "");
		this.strength = new Text(500, 38, "");
		this.score = new Text(670, 40, "");
		this.Time = new Sprite(300,0);
		this.Time.loadImage(TIME_IMAGE);
		this.Strength = new Sprite(450,0);
		this.Strength.loadImage(STRENGTH_IMAGE);
		this.Score = new Sprite(620,0);
		this.Score.loadImage(SCORE_IMAGE);
	}

	protected Text getTimeText(){
		return this.time;
	}

	protected Text getStrengthText(){
		return this.strength;
	}

	protected Text getScoreText(){
		return this.score;
	}

	protected int getElapsedTime(){
		return this.elapsedTime;
	}

	protected void renderText(){
		this.time.setText(" "+ this.timeRemaining);
		this.time.setFill(Color.WHITE);
		this.time.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 40));
		this.strength.setText(""+ this.currentStrength);
		this.strength.setFill(Color.WHITE);
		this.strength.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 40));
		this.score.setText(" "+ this.currentScore);
		this.score.setFill(Color.WHITE);
		this.score.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 40));
	}

	protected void RenderScore(){
		this.score.setText("SCORE "+ this.currentScore);
		this.score.setFill(Color.WHITE);
		this.score.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 30));
	}

	protected void setStatusTime(int x){
		this.elapsedTime += x;
		this.timeRemaining -= x;
	}

	protected void setStatusScore(int x){
		this.currentScore += x;
	}

	protected void setStatusStrength(int x){
		this.currentStrength = x;

		if(this.currentStrength <= 0){
			this.currentStrength = 0;
		}
	}

	protected int getStatusStrength(){
		return this.currentStrength;
	}

	public Sprite getTime(){
		return this.Time;
	}

	public Sprite getStrength(){
		return this.Strength;
	}

	public Sprite getScore(){
		return this.Score;
	}

	public int getCurrentScore(){
		return this.currentScore;
	}
}

