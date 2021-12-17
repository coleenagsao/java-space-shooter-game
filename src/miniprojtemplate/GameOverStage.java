package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverStage {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private StatusBar statusbar;
	private Text score;

	public final Image win = new Image("images/win.png",800,500,false,false);
	public final Image lose = new Image("images/lose.png",800,500,false,false);
	public final static Image SCORE_IMAGE = new Image("images/boss.gif",45,45,false,false);

	GameOverStage(int num, StatusBar statusbar){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.statusbar = statusbar;
		this.setProperties(num);
		this.score = new Text(250,400, "SCORE");

	}

	private void setProperties(int num){
		if (num == 0){	//if user wins
			this.gc.drawImage(this.lose, 0,0);
		} else {		//if user loses
			this.gc.drawImage(this.win, 0,0);
		}

		//TODO: Add text here
		this.statusbar.RenderScore();




		Button exitbtn = new Button("Exit");
		exitbtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 18));
		this.addEventHandler(exitbtn);
		exitbtn.setTextFill(Color.WHITE);
		exitbtn.setBackground(null);

		pane.getChildren().add(this.canvas);
		pane.getChildren().add(statusbar.getScoreText());
		pane.getChildren().add(exitbtn);
	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});

	}

	Scene getScene(){
		return this.scene;
	}
}
