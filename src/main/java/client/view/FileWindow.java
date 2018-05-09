package client.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileWindow extends JFrame {

	public FileWindow() {
		buildGUI();
	}

	private void buildGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPane());
		pack();
		setLocationRelativeTo(null);
	}

	private Container createContentPane() {
		return new JPanel();
	}

}
