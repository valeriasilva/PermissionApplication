package client.view.components;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ApplicationTextField extends JTextField {

	public ApplicationTextField() {
		super();
		config();
	}

	public ApplicationTextField(String text, int columns) {
		super(text, columns);
		config();
	}

	public ApplicationTextField(String text) {
		super(text);
		config();
	}

	private void config() {
		setMinimumSize(new Dimension(0, new JComboBox<>().getPreferredSize().height));
	}

}
