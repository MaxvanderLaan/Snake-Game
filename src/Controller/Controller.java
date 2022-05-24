package Controller;

import java.util.ArrayList;

import Model.BodyPart;
import Model.Direction;
import Model.Game;
import Model.Spot;
import View.GameOverScene;
import View.GameScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {

	private GameOverScene gameOverScene;
	private GameScene gameScene;
	private BorderPane root;
	private VBox gameOverCanvas;
	private Game game;
	private Stage stage;
	private boolean defeat;

	private ArrayList<Spot> itemCollection;
	private int startingAmound = 3;
	private int itemChance = 15;
	private boolean posFree;
	private boolean gameActive;
	private Timeline timeline;
	private double gameSpeed = 1;
	private double tickSpeed;
	private int tickCounter;
	private int growCounter;
	private int playTime;
	private Direction currentDirection;
	private Direction previousDirection;

	public void doLaunch(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		// init variables
		root = new BorderPane();
		gameOverCanvas = new VBox();
		gameScene = new GameScene(root, stage, this);
		game = new Game(gameScene.getDrawPane().getRows(), gameScene.getDrawPane().getColumns());
		tickCounter = 0;
		growCounter = 0;
		itemCollection = new ArrayList();
		tickSpeed = 600;

		// init game components
		setPreviousDirection(Direction.RIGHT);
		startingItems();
		gameScene.getDrawPane().drawCanvas(gameScene.getDrawPane().getGraphicsContext());
		drawSnake();
		initStage(stage);
		startTimeline(tickSpeed);
	}

	// When called this starts the game loop.
	private void startTimeline(double tickSpeed) {
		timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(tickSpeed),
				e -> gameLoop(gameScene.getDrawPane().getGraphicsContext()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	// When this method is called, a game tick is created.
	private void gameLoop(GraphicsContext graphicsContext) {

		// This part of the loop will only run if the start button has been pressed.
		getGameStatus();
		if (gameActive) {
			if (defeat) {
				gameEnded(stage);
				return;
			}

			// input
			keyListener();

			// game components
			gameScene.getDrawPane().drawCanvas(graphicsContext);
			generateItem();
			drawSnake();
			drawItem();

			// timer
			addGameTime();
			updateGameTime();
			gameScene.getDashBoard().updatePlayTimer();

			// movement
			moveSnakeBody();
			moveSnakeHead();
			setPreviousDirection(currentDirection);

			// collisions
			collisionItem();
			collisionOther();
			checkSnakeSize();

			// speed
			updateSpeed();
		}
	}

	// INPUT //

	private void setPreviousDirection(Direction direction) {
		previousDirection = direction;
	}

	private Direction getCurrentDirection() {
		if (currentDirection != null) {
			return currentDirection;
		}
		return currentDirection.RIGHT; // default

	}

	// INPUT //

	// MOVEMENT //

	// This method is always Active and listens for player input.
	private void keyListener() {

		// getGameStatus();
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode input = event.getCode();

				// Prevents snake from imploding at start of the game.
				if (previousDirection == null) {
					previousDirection = Direction.RIGHT;
				}

				// Key listener, doesn't work if game is paused.
				if (input == KeyCode.RIGHT) {
					if (previousDirection != Direction.LEFT && gameActive) {
						currentDirection = Direction.RIGHT;
					}
				} else if (input == KeyCode.LEFT && gameActive) {
					if (previousDirection != Direction.RIGHT) {
						currentDirection = Direction.LEFT;
					}
				} else if (input == KeyCode.UP && gameActive) {
					if (previousDirection != Direction.DOWN) {
						currentDirection = Direction.UP;
					}
				} else if (input == KeyCode.DOWN && gameActive) {
					if (previousDirection != Direction.UP) {
						currentDirection = Direction.DOWN;
					}
				}
			}
		});
	}

	// TIMER //

	// Keeps track of miliseconds passed while game loop is active.
	private void addGameTime() {
		playTime = (int) (playTime + tickSpeed);
	}

	public int updateGameTime() {
		return playTime;
	}

	// TIMER //

	// COLLISIONS //

	// Will check for collisions between the snake and a item.
	private void collisionItem() {
		for (int i = 0; i < itemCollection.size(); i++) {
			if (game.getSnakehead().x == itemCollection.get(i).getX()
					&& game.getSnakehead().y == itemCollection.get(i).getY()) {

				// Modifies snake according to item type.
				if (itemCollection.get(i).getItemName().equals("MOUSE")) {
					for (int x = 0; x < 5; x++) {
						game.addBodyPart(game.getSnakeBody().get(game.getSnakeBody().size() - 1).x,
								game.getSnakeBody().get(game.getSnakeBody().size() - 1).y);
					}
				}

				if (itemCollection.get(i).getItemName().equals("FIRE")) {
					defeat = true;
				}

				if (itemCollection.get(i).getItemName().equals("BEAR")) {
					int y = game.getSnakeBody().size() / 2;
					for (int x = 0; x < y; x++) {
						game.getSnakeBody().remove(game.getSnakeBody().size() - 1);
						;
					}
				}
				itemCollection.remove(itemCollection.get(i));
			}
		}
	}

	// Checks for collisions between the snake and the edge of the playing field.
	private void collisionOther() {
		if (game.getSnakehead().x >= gameScene.getDrawPane().getRows()) {
			defeat = true;
		}
		if (game.getSnakehead().y >= gameScene.getDrawPane().getColumns()) {
			defeat = true;
		}
		if (game.getSnakehead().x < 0) {
			defeat = true;
		}
		if (game.getSnakehead().y < 0) {
			defeat = true;
		}

		// Checks collisions for snake itself.
		for (int i = 1; i < game.getSnakeBody().size(); i++) {
			if (game.getSnakehead().x == game.getSnakeBody().get(i).x
					&& game.getSnakehead().y == game.getSnakeBody().get(i).y) {
				defeat = true;
				break;
			}
		}
	}

	// Will move the head of the snake according to the currentDirection.
	private void moveSnakeHead() {
		switch (getCurrentDirection()) {
		case RIGHT:
			game.getSnakehead().x++;
			break;
		case LEFT:
			game.getSnakehead().x--;
			break;
		case UP:
			game.getSnakehead().y--;
			break;
		case DOWN:
			game.getSnakehead().y++;
			break;
		}
	}

	private void moveSnakeBody() {
		for (int i = game.getSnakeBody().size() - 1; i >= 1; i--) {
			game.getSnakeBody().get(i).x = game.getSnakeBody().get(i - 1).x;
			game.getSnakeBody().get(i).y = game.getSnakeBody().get(i - 1).y;
		}
	}

	private void checkSnakeSize() {
		// loses the game if the snake only consists of a head.
		if (game.getSnakeBody().size() < 5) {
			defeat = true;
		}
	}

	private void gameEnded(Stage stage) {
		gameScene.getDashBoard().setDeathTime();
		gameOverScene = new GameOverScene(gameOverCanvas, gameScene.getDashBoard().getDeathTime());
		timeline.stop();
		stage.setScene(gameOverScene);
	}

	// COLLISIONS //

	// ITEMS //

	// Draws all items on canvas every game loop.
	private void drawItem() {
		for (Spot s : itemCollection) {
			gameScene.getDrawPane().drawItem(s.getImage(), s.getX(), s.getY());
		}
	}

	// If item can appropriately be generated, add it to itemCollection ArrayList.
	private void generateItem() {
		int i = (int) (Math.random() * 100 + 1);
		posFree = true;

		// Chance of spawning a item every gameloop.
		if (i <= itemChance) {
			Spot tempSpot = game.generateSpot();

			// Check if position is taken by another item.
			for (Spot s : itemCollection) {
				if (tempSpot.getX() == s.getX() && tempSpot.getY() == s.getY()) {
					posFree = false;
				}
			}

			// Check if position is taken by snake.
			for (BodyPart b : game.getSnakeBody()) {
				if (tempSpot.getX() == b.getX() && tempSpot.getY() == b.getY()) {
					posFree = false;
				}
			}

			if (posFree) {
				itemCollection.add(tempSpot);
			}
		}
	}

	// Will generate 3 spots and add it to arrayList at start of game.
	private void startingItems() {
		for (int x = 0; x < startingAmound; x++) {
			Spot tempSpot = game.generateSpot();
			posFree = true;

			// Check if position is taken by another item.
			for (Spot s : itemCollection) {
				if (tempSpot.getX() == s.getX() && tempSpot.getY() == s.getY()) {
					posFree = false;
				}
			}

			// Check if position is taken by snake.
			for (BodyPart b : game.getSnakeBody()) {
				if (tempSpot.getX() == b.getX() && tempSpot.getY() == b.getY()) {
					posFree = false;
				}
			}

			if (posFree) {
				itemCollection.add(tempSpot);
			}
		}
	}

	// ITEMS //

	// BUTTON FUNCTIONALITY //

	public void exitGame(Stage stage) {
		stage.close();
	}

	private void getGameStatus() {
		gameActive = gameScene.getDashBoard().getGameStatus();
	}

	// BUTTON FUNCTIONALITY //

	private void drawSnake() {
		gameScene.getDrawPane().getGraphicsContext().setFill(Color.rgb(254, 000, 000));
		gameScene.getDrawPane().getGraphicsContext().fillRoundRect(
				game.getSnakehead().getX() * gameScene.getDrawPane().getTileSize(),
				game.getSnakehead().getY() * gameScene.getDrawPane().getTileSize(),
				gameScene.getDrawPane().getTileSize() - 1, gameScene.getDrawPane().getTileSize() - 1, 45, 45);

		for (int i = 1; i < game.getSnakeBody().size(); i++) {
			gameScene.getDrawPane().getGraphicsContext().setFill(Color.rgb(255, 166, 000));
			gameScene.getDrawPane().getGraphicsContext().fillRoundRect(
					game.getSnakeBody().get(i).getX() * gameScene.getDrawPane().getTileSize(),
					game.getSnakeBody().get(i).getY() * gameScene.getDrawPane().getTileSize(),
					gameScene.getDrawPane().getTileSize() - 1, gameScene.getDrawPane().getTileSize() - 1, 45, 45);
		}
	}

	// Speeds up the delay between game loops.
	private void updateSpeed() {
		tickCounter++;
		growCounter++;

		// The 10th time this method is called, gamespeed is increased by 1.
		if (tickCounter >= 10 && gameSpeed <= 12) {
			gameSpeed = gameSpeed + 1;
			gameScene.getDashBoard().setSpeedSlider(gameSpeed);
			tickCounter = 0;
			timeline.stop();
			startTimeline(tickSpeed);
			tickSpeed = 600 - (25 * gameSpeed);
		}

		// The 25th time this method is called, the size of the snake is increased by 1.
		if (growCounter >= 25) {
			game.addBodyPart(game.getSnakeBody().get(game.getSnakeBody().size() - 1).x,
					game.getSnakeBody().get(game.getSnakeBody().size() - 1).y);
			growCounter = 0;
		}
	}

	private void initStage(Stage stage) {
		stage.setTitle("PROG4 ASS Snake - Max van der Laan");
		stage.setScene(gameScene);
		stage.show();
		stage.setResizable(false);
	}

}
