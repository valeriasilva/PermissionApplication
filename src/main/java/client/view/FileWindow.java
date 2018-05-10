package client.view;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.tbee.javafx.scene.layout.MigPane;

import client.util.Util;
import common.model.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FileWindow extends JFrame {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JFXPanel fxPanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FileWindow();
			}
		});
	}

	public FileWindow() {
		buildGUI();
	}

	private void buildGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fxPanel = new JFXPanel();
		setVisible(true);
		setLocationRelativeTo(null);
		setTitle("Buscar arquivos");
		setMinimumSize(MIN_SIZE);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

		add(fxPanel);
		pack();
	}

	private static void initFX(JFXPanel fxPanel) {
		Scene scene = createContentPane();
		fxPanel.setScene(scene);
	}

	private static Scene createContentPane() {


		TextField searchField = new TextField();
		Button btnSearch = new Button("Buscar");

		TableView<File> table = new TableView<>();
		table.getColumns().addAll(new TableColumn<File,String>("Arquivo"), new TableColumn<File,String>("Tamanho"));

		MigPane layout = new MigPane("ins 30","[grow][]", "[][][grow]");           

		layout.add(createRadioButtons(), "wrap");
		layout.add(searchField, "grow");
		layout.add(btnSearch, "wrap");
		layout.add(table, "span, grow");

		Scene scene = new Scene(layout);

		return scene;
	}

	private static Node createRadioButtons() {
		
		RadioButton partNameOption = new RadioButton();
		RadioButton exactNameOption = new RadioButton();
		
		MigPane layout = new MigPane("","[][][][]", "[]");           
		layout.add(partNameOption, "");
		layout.add(new Text("Parte do nome"));
		layout.add(exactNameOption, "");
		layout.add(new Text("Nome exato"));

		return layout;
	}
}
