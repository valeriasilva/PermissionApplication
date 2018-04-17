package client.util;

import java.sql.Timestamp;

import javax.swing.JOptionPane;

public class Util {

	public static final float GOLDEN_RATIO = 1.61f;

	public static Timestamp getCurrentDateFormated() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static void showMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
}
