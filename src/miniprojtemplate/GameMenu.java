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
        vbox.setPadding(new Insets(100));
        vbox.setSpacing(15);

        Button startbtn = new Button("Start");
        this.addEventHandler(startbtn, 1, this.gametimer, this.gameStage);

		Button instbtn = new Button("Instructions");
		this.addEventHandler(instbtn, 2, this.gametimer, this.gameStage);

		Button abtbtn = new Button("About");
		this.addEventHandler(abtbtn, 3, this.gametimer, this.gameStage);

		Button exitbtn = new Button("Exit");
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
