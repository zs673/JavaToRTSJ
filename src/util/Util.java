package util;

public class Util {

	public static void timeConsumer(long milliseconds) {

		long currentTime = System.currentTimeMillis();

		while (System.currentTimeMillis() - currentTime < milliseconds) {

		}

	}

}
