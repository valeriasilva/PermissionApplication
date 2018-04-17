package client.util;

import java.sql.Timestamp;

public class Util {

	public static final float GOLDEN_RATIO = 1.61f;

	public static Timestamp getCurrentDateFormated() {
		return new Timestamp(System.currentTimeMillis());
	}
}
