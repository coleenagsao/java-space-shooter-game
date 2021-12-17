package miniprojtemplate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private StatusBar statusbar;


	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.statusbar = new StatusBar();
		//this.gametimer = new GameTimer(this.gc,this.scene,this.statusbar, this);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;

		this.setMenu(this.gametimer, this);
		//set stage elements here

		this.root.getChildren().add(canvas);
		this.root.getChildren().add(statusbar.getTimeText());
		this.root.getChildren().add(statusbar.getStrengthText());
		this.root.getChildren().add(statusbar.getScoreText());

		this.stage.setTitle("Mini Ship Shooting Game");
		this.stage.setScene(this.scene);

		//invoke the start method of the animation timer

		//this.gametimer.start();

		this.stage.show();


	}

	void setGameOver(int num, StatusBar statusbar){ //1 for win; 0 for lose
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage(num, statusbar);
				stage.setScene(gameover.getScene());
			}
		});
	}

	void setMenu(GameTimer gametimer, GameStage gamestage){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				GameMenu gamemenu = new GameMenu(gametimer, gamestage);
				stage.setScene(gamemenu.getScene());
			}
		});
	}

	void setAbout(int num, GameTimer gametimer, GameStage gamestage){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				About about = new About(num, gamestage, gametimer);
				stage.setScene(about.getScene());
			}
		});
	}

	void startGame(GameTimer gametimer){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				GameTimer gametimer = createGameTimer();
				stage.setScene(gametimer.getScene());
				gametimer.start();
			}
		});
	}

	GameTimer createGameTimer(){
		GameTimer gametimer = new GameTimer(this.gc,this.scene,this.statusbar, this);
		return gametimer;
	}
}