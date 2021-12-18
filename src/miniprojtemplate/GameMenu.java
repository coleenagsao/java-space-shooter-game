package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameMenu {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private GameTimer gametimer;
	private GameStage gameStage;

	public final Image title = new Image("images/title.png",800,500,false,false);

	GameMenu(GameTimer gametimer, GameStage gamestage){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gametimer = gametimer;
		this.gameStage = gamestage;
		this.setProperties();

	}

	private void setProperties(){
		this.gc.drawImage(this.title, 0,0);

		VBox choices = this.createVBox();

		pane.getChildren().add(this.canvas);
		pane.getChildren().add(choices);
	}


    private VBox createVBox() {
        VBox vbox = new VBox();

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(35, 0, 0, 300));
        vbox.setSpacing(3);

        Button startbtn = new Button("Start");
        startbtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 18));
        startbtn.setTextFill(Color.WHITE);
        startbtn.setBackground(null);
        this.addEventHandler(startbtn, 1, this.gametimer, this.gameStage);

		Button instbtn = new Button("Instructions");
		instbtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 18));
		instbtn.setTextFill(Color.WHITE);
		instbtn.setBackground(null);
		this.addEventHandler(instbtn, 2, this.gametimer, this.gameStage);

		Button abtbtn = new Button("About");
		abtbtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 18));
		abtbtn.setTextFill(Color.WHITE);
		abtbtn.setBackground(null);
		this.addEventHandler(abtbtn, 3, this.gametimer, this.gameStage);

		Button exitbtn = new Button("Exit");
		exitbtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 18));
		exitbtn.setTextFill(Color.WHITE);
		exitbtn.setBackground(null);
		this.addEventHandler(exitbtn, 4, this.gametimer, this.gameStage);

        vbox.getChildren().addAll(startbtn, instbtn, abtbtn, exitbtn);

        return vbox;
    }

	private void addEventHandler(Button btn, int num, GameTimer gametimer, GameStage gamestage) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				switch (num){
					case 1:
						gameStage.startGame(gametimer);
						break;
					case 2:
						gameStage.setAbout(0, gametimer, gamestage);
						break;
					case 3:
						gameStage.setAbout(1, gametimer, gamestage);
						break;
					case 4:
						System.exit(0);
				}
			}
		});
	}

	Scene getScene(){
		return this.scene;
	}

}
