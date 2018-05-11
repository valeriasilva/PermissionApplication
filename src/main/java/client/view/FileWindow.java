package client.view;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.tbee.javafx.scene.layout.MigPane;

import client.controller.FileController;
import client.util.Util;
import common.model.File;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FileWindow extends JFrame {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JFXPanel fxPanel;
	private RadioButton partNameOption;
	private RadioButton exactNameOption;
	List<File> files;
	private File file;
	private TableView<File> table;
	private final ObservableList<File> data =  FXCollections.observableArrayList();
	private TextField searchField;

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
		add(fxPanel);
		pack();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});
	}

	private void initFX(JFXPanel fxPanel) {
		Scene scene = createContentPane();
		fxPanel.setScene(scene);
	}

	private Scene createContentPane() {
		files = new ArrayList<File>();
		searchField = new TextField();

		Button btnSearch = new Button("Buscar");
		btnSearch.setOnAction(event -> btnSearchAction());

		buildTable();

		MigPane layout = new MigPane("ins 30","[grow][]", "[][][grow]");           

		layout.add(createRadioButtons(), "wrap");
		layout.add(searchField, "grow");
		layout.add(btnSearch, "wrap");
		layout.add(table, "span, grow");

		Scene scene = new Scene(layout);

		return scene;
	}

	private void btnSearchAction() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(partNameOption.isSelected()) {
					cleanTable();
					files.addAll(new FileController().searchFilesbyNamePart(searchField.getText()));

					data.addAll(files);
				}
				else if (exactNameOption.isSelected()){
					cleanTable();
					file = new File();
					file = new FileController().searchSpecificFile(searchField.getText());

					data.add(file);
				}
			}
		});
	}

	private void buildTable() {
		table = new TableView<>();
		TableColumn<File, String> firstNameCol = new TableColumn<>("Arquivo");
		TableColumn<File, String> secondNameCol = new TableColumn<>("Tamanho");

		table.getColumns().addAll(firstNameCol, secondNameCol);

		firstNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		secondNameCol.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asString());

		table.setItems(data);
	}

	public Node createRadioButtons() {
		ToggleGroup t = new ToggleGroup();

		partNameOption = new RadioButton("Parte do nome");
		partNameOption.setToggleGroup(t);
		exactNameOption = new RadioButton("Nome exato");
		exactNameOption.setToggleGroup(t);
		exactNameOption.setSelected(true);
		
		MigPane layout = new MigPane("","[][][][]", "[]");           
		layout.add(partNameOption, "");
		layout.add(exactNameOption, "");

		return layout;
	}

	public void cleanTable() {
		data.clear();
		files = new ArrayList<>();
		file = new File();
		table.setItems(data);
	}
}