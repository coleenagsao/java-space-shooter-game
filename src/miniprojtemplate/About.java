package miniprojtemplate;

import javafx.event.EventHandler;
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

public class About {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private GameStage gamestage;
	private GameTimer gametimer;

	public final Image about = new Image("images/about.png",800,500,false,false);
	public final Image instructions = new Image("images/instructions.png",800,500,false,false);

	About(int num, GameStage gamestage,GameTimer gametimer){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gamestage = gamestage;
		this.gametimer = gametimer;
		this.setProperties(num);

	}

	private void setProperties(int num){
		if (num == 0){
			this.gc.drawImage(this.instructions, 0,0);
		} else {
			this.gc.drawImage(this.about, 0,0);
		}

		VBox menu = this.createVBox();

		pane.getChildren().add(this.canvas);
		pane.getChildren().add(menu);
	}


	private void addEventHandler(Button btn, GameStage gamestage, GameTimer gametimer) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				gamestage.setMenu(gametimer, gamestage);
			}
		});

	}

	private VBox createVBox() {
        VBox vbox = new VBox();

        vbox.setAlignment(Pos.BOTTOM_RIGHT);
        vbox.setSpacing(3);

		Button menubtn = new Button("Menu");
		menubtn.setFont(Font.loadFont("file:resources/fonts/ARCADECLASSIC.ttf", 20));
		menubtn.setTextFill(Color.WHITE);
		menubtn.setBackground(null);
		this.addEventHandler(menubtn, this.gamestage, this.gametimer);

		vbox.getChildren().add(menubtn);
        return vbox;
    }

	Scene getScene(){
		return this.scene;
	}
}

