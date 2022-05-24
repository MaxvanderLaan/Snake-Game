package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawPane extends Pane {

	private int width = 760;
	private int height = 600;
	private int rows = 19;
	private int columns = 15;
	private int tileSize = width / rows;
	private GraphicsContext graphicsContext;

	public DrawPane() {
		this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
		Canvas canvas = new Canvas(width, height);
		graphicsContext = canvas.getGraphicsContext2D();

		this.getChildren().add(canvas);
	}
	
	public void drawCanvas(GraphicsContext graphicsContext) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				// Colors all uneven rows
				if ((i + j) % 2 == 0) {
					graphicsContext.setFill(Color.rgb(000, 000, 000));
				} else {
					graphicsContext.setFill(Color.rgb(31, 31, 31));
				}
				// Colors all even rows.
				if (j % 2 != 0) {
					if (i % 2 == 0) {
						graphicsContext.setFill(Color.rgb(31, 31, 31));
					} else {
						graphicsContext.setFill(Color.rgb(50, 50, 50));
					}
				}
				graphicsContext.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
			}
		}
	}

	// Adds item to canvas.
	public void drawItem(Image img, int x, int y) {
		graphicsContext.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize);
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	public int getTileSize() {
		return tileSize;
	}

}
