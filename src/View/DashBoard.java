package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashBoard extends HBox {

	private Button startButton;
	private Button exitButton;
	private Button pauseButton;
	private String deathTime;
	private Label playTimer;
	private Slider speedSlider;
	private Controller controller;
	private boolean gameStatus;
	private String playTimeText;
	private int milliseconds;
	private int seconds;
	private int minutes;

	public DashBoard(Stage stage, Controller controller) {
		this.controller = controller;
		this.setBackground(new Background(new BackgroundFill(Color.DIMGREY, null, null)));
		this.setSpacing(20);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setAlignment(Pos.CENTER);

		pauseButton = new Button("Pause");
		pauseButton.setPrefSize(80, 30);
		pauseButton.setOnAction(e -> pauseButtonPressed());

		startButton = new Button("Start");
		startButton.setPrefSize(80, 30);
		startButton.setOnAction(e -> startButtonPressed());

		exitButton = new Button("Exit");
		exitButton.setPrefSize(80, 30);
		exitButton.setOnAction(e -> exitButtonPressed(stage));

		this.getChildren().addAll(startButton, exitButton);
		initSlider();
		intiTimer();
	}

	private void pauseButtonPressed() {
		this.getChildren().remove(0);
		this.getChildren().add(0, startButton);
		gameStatus = false;
	}

	private void startButtonPressed() {
		this.getChildren().remove(0);
		this.getChildren().add(0, pauseButton);
		gameStatus = true;
	}

	private void exitButtonPressed(Stage stage) {
		controller.exitGame(stage);
	}

	private void initSlider() {
		speedSlider = new Slider();
		speedSlider.setMin(1);
		speedSlider.setMax(12);
		speedSlider.setValue(1);
		speedSlider.setShowTickLabels(true);
		speedSlider.setShowTickMarks(true);
		speedSlider.setBlockIncrement(1);
		speedSlider.setMajorTickUnit(1);
		speedSlider.setMinorTickCount(0);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPrefSize(260, 40);
		speedSlider.setBackground(new Background(new BackgroundFill(Color.rgb(169, 169, 169), null, null)));
		speedSlider.setDisable(true);
		speedSlider.setOpacity(1);
		speedSlider.setPadding(new Insets(5, 5, 5, 5));

		this.getChildren().add(speedSlider);
	}

	private void intiTimer() {
		playTimer = new Label("00:00:000");
		playTimer.setBackground(new Background(new BackgroundFill(Color.rgb(169, 169, 169), null, null)));
		playTimer.setPrefSize(110, 48);
		playTimer.setAlignment(Pos.CENTER);

		this.getChildren().add(playTimer);
	}

	public void setDeathTime() {
		deathTime = playTimer.getText();
	}

	public String getDeathTime() {
		return deathTime;
	}

	public Boolean getGameStatus() {
		return gameStatus;
	}

	public void setSpeedSlider(double speed) {
		speedSlider.setValue(speed);
	}

	public int getGameTime() {
		return controller.updateGameTime();
	}

	// Converts game ticks into readable text.
	private void calculateTime() {
		int playTime = getGameTime();
		int x = playTime / 1000;
		milliseconds = playTime - (x * 1000);
		seconds = playTime / 1000;
		minutes = playTime / 1000 / 60;
		int calcSeconds = seconds;
		int y = seconds / 60;
		for (int z = 0; z != y;) {
			calcSeconds = seconds - (y * 60);
			break;
		}
		if (minutes < 10 && calcSeconds < 10) {
			playTimeText = "0" + String.valueOf(minutes) + ":" + "0" + String.valueOf(calcSeconds) + ":"
					+ String.valueOf(milliseconds);
		}
		if (minutes < 10 && calcSeconds >= 10) {
			playTimeText = "0" + String.valueOf(minutes) + ":" + String.valueOf(calcSeconds) + ":"
					+ String.valueOf(milliseconds);
		}
		if (minutes >= 10 && calcSeconds >= 10) {
			playTimeText = String.valueOf(minutes) + ":" + String.valueOf(calcSeconds) + ":"
					+ String.valueOf(milliseconds);
		}
		if (minutes >= 10 && calcSeconds < 10) {
			playTimeText = String.valueOf(minutes) + ":" + "0" + String.valueOf(calcSeconds) + ":"
					+ String.valueOf(milliseconds);
		}
	}

	public void updatePlayTimer() {
		calculateTime();
		playTimer.setText(playTimeText);
	}

}
