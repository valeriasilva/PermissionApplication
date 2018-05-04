package client.view;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import client.util.Util;
import net.miginfocom.swing.MigLayout;

public class ProgressDialog extends JDialog {

	private static final int MIN_HEIGHT = 100;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JProgressBar progressBar;
	private JLabel counter;

	public ProgressDialog() {
		buildGUI();

	}

	private void buildGUI() {
		setTitle("Progresso ...");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
	}

	private Container createContentPane() {
		progressBar = new JProgressBar(0, 100);	
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);

		counter = new JLabel("");

		JPanel contentPane = new JPanel(new MigLayout("ins 10", "[]", "[][]"));

		contentPane.add(progressBar, "wrap");
		contentPane.add(counter);

		return contentPane;
	}

	public void repaintTime(long startTime) {
		long diffTime = System.currentTimeMillis() - startTime;  
		int seconds = (int)(diffTime / 1000 % 60);  
		int minutes = (int)(diffTime / 60000 % 60);  
		int hours = (int)(diffTime / 3600000);  
		String s = String.format("%d:%02d:%02d", hours, minutes, seconds);  
		counter.setText(s); 
//
//		Calendar now = Calendar.getInstance();
//		int h = now.get(Calendar.HOUR_OF_DAY);
//		int m = now.get(Calendar.MINUTE);
//		int s = now.get(Calendar.SECOND);
//
//		counter.setText(String.format("%1$tH:%1$tM:%1$tS", now));
	}

	public JLabel getCounter() {
		return counter;
	}

	public void setCounter(JLabel counter) {
		this.counter = counter;
	}
}
