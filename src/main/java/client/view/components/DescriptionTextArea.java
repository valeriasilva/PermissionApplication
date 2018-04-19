package client.view.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DescriptionTextArea extends JScrollPane {

	private static final Dimension MIN_SIZE = new Dimension(0, 60);
	private JTextArea textArea;

	public DescriptionTextArea() {
		createTextArea();
		setViewportView(textArea);
		setMinimumSize(MIN_SIZE);
		setPreferredSize(MIN_SIZE);
	}

	private Component createTextArea() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		return textArea;
	}

	public String getText() {
		return textArea.getText();
	}

	public void setText(String text) {
		this.textArea.setText(text);
	}

}
