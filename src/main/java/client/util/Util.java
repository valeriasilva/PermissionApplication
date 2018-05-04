package client.util;

import java.awt.Component;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

public class Util {

	public static final long SEC = 1000;
	private static final long MIN = SEC * 60;
	private static final long HOUR = MIN * 60;

	public static final float GOLDEN_RATIO = 1.61f;
	private static final String MSG_TITLE = "Sistema de Controle de Permissões";

	public static Timestamp getCurrentDateFormated() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static void showMessage(final Component parentComponent, int messageType, final String msg) {
		JOptionPane.showMessageDialog(parentComponent, msg, MSG_TITLE, messageType);
	}

	public static void showErrorMessage(Component parentComponent, String msg) {
		showMessage(parentComponent, JOptionPane.ERROR_MESSAGE, msg);
	}

	public static void showInfoMessage(Component parentComponent, String msg) {
		showMessage(parentComponent, JOptionPane.INFORMATION_MESSAGE, msg);
	}

	public static String getElapsedTime(final long startTime) {
		long diffTime = System.currentTimeMillis() - startTime;

		long hours = diffTime / HOUR;
		diffTime %= HOUR;

		long minutes = diffTime / MIN;
		diffTime %= MIN;

		long seconds = diffTime / SEC;

		return String.format("%d:%02d:%02d", hours, minutes, seconds);
	}

}
