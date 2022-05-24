package Model;

import javafx.scene.image.Image;

public class Spot {

	private String itemName;
	private Image bearImage;
	private Image mouseImage;
	private Image fireImage;
	private int x;
	private int y;
	Marker marker;

	public Spot(int rows, int columns) {

		// Every new spot has a random Marker asigned to it and a random X and Y.
		marker = Marker.getRandomSpot();

		//init Images
		itemName = marker.toString();
		bearImage = new Image("/img/bear.png");
		mouseImage = new Image("/img/mouse.png");
		fireImage = new Image("/img/fire.png");

		x = (int) (Math.random() * rows);
		y = (int) (Math.random() * columns);
	}

	public Image getImage() {
			switch (marker) {

			case BEAR :
				return bearImage;

			case MOUSE:
				return mouseImage;

			case FIRE:
				return fireImage;
			}
			return null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getItemName() {
		return itemName;
	}

}
