package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScene extends Scene {

	private VBox gameOverCanvas;
	private Label gameOverText;
	private Label deathLabel;

	public GameOverScene(VBox gameOverCanvas, String deathTimer) {
		super(gameOverCanvas);

		gameOverText = new Label("Game Over");
		gameOverText.setFont(new Font("Arial", 30));
		gameOverText.setTextFill(Color.BLACK);
		deathLabel = new Label();

		deathLabel.setText(deathTimer);
		deathLabel.setFont(new Font("Arial", 40));
		deathLabel.setTextFill(Color.WHITE);
		
		initCanvas();
	}

	private void initCanvas() {
		gameOverCanvas = new VBox();
		gameOverCanvas.setPrefSize(760, 600);
		gameOverCanvas.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, null, null)));
		gameOverCanvas.setAlignment(Pos.CENTER);

		this.setRoot(gameOverCanvas);
		gameOverCanvas.getChildren().addAll(gameOverText, deathLabel);
	}
}
