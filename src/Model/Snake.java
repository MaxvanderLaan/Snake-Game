package Model;

import java.util.ArrayList;
import java.util.List;

public class Snake {

	private int rows;
	private int columns;
	private List<BodyPart> snakeModel;
	private BodyPart snakeHead;

	public Snake(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		snakeModel = new ArrayList();
		initSnakeLength();
		snakeHead = snakeModel.get(0);
	}

	// Starting length of the snake at the beginning of the game.
	private void initSnakeLength() {
		for (int i = 0; i < 5; i++) {
			snakeModel.add(new BodyPart(rows / 2 - i, columns / 2));
		}
	}

	public List<BodyPart> getSnakeBody() {
		return snakeModel;
	}

	public BodyPart getSnakeHead() {
		return snakeHead;
	}

	public void addBodyPart(int x, int y) {
		snakeModel.add(new BodyPart(x, y));
	}
}
