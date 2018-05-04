package client.view;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import client.util.Util;
import net.miginfocom.swing.MigLayout;

public class ProgressDialog extends JDialog {

	private JLabel counter;
	private final long startTime;

	public ProgressDialog(long startTime) {
		super();
		this.startTime = startTime;
		buildGUI();
	}

	private void buildGUI() {
		setUndecorated(true);
		setContentPane(createContentPane());
		pack();
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
	}

	private Container createContentPane() {

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);

		counter = new JLabel("");

		JPanel contentPane = new JPanel(new MigLayout("ins 30", "[grow]", "[][][]"));
		contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		contentPane.add(new JLabel("Progresso ..."), "wrap");
		contentPane.add(progressBar, "w 200:200:200, growx, wrap");
		contentPane.add(counter);

		return contentPane;
	}

	public void repaintTime() {
		counter.setText(Util.getElapsedTime(startTime));
	}

	public JLabel getCounter() {
		return counter;
	}

	public void setCounter(JLabel counter) {
		this.counter = counter;
	}
}
