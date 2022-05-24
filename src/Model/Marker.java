package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Marker {

	MOUSE, BEAR, FIRE;

	private static final List<Marker> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	// When method is called, a random marker is returned.
	public static Marker getRandomSpot() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
