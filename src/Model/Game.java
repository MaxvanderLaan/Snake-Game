package Model;

import java.util.List;

public class Game {
	
	private Snake snake;
	private int rows;
	private int columns;
		
	public Game(int rows, int columns) {
		snake = new Snake(rows, columns);
		this.rows = rows;
		this.columns = columns;
	}
	
	public Spot generateSpot() {
		Spot spot = new Spot(rows, columns);
		return spot;
	}
	
	public void addBodyPart(int x, int y) {
		snake.addBodyPart(x, y);
	}
	
	public List<BodyPart> getSnakeBody() {
		return snake.getSnakeBody();
	}
	
	public BodyPart getSnakehead(){
		return snake.getSnakeHead();
	}
	
	
}
