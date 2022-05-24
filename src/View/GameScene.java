package View;

import Controller.Controller;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameScene extends Scene {
	
	private DrawPane drawPane;
	private DashBoard dashBoard;	

	public GameScene(BorderPane root, Stage stage, Controller controller) {
		super(root);

		drawPane = new DrawPane();
		dashBoard = new DashBoard(stage, controller);
		
		this.setRoot(root);
		root.setCenter(drawPane);
		root.setBottom(dashBoard);
				
	}
	
	public DashBoard getDashBoard() {
		return dashBoard;
	}
	
	public DrawPane getDrawPane() {
		return drawPane;
	}
	

}
