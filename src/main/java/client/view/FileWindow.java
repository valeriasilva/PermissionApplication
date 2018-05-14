package client.view;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.tbee.javafx.scene.layout.MigPane;

import client.controller.FileController;
import client.util.Util;
import client.view.components.FileTable;
import common.model.File;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FileWindow extends JFrame {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JFXPanel fxPanel;
	private RadioButton partNameOption;
	private RadioButton exactNameOption;
	private List<File> files;
	private File file;
	private FileTable table;
	private final ObservableList<File> data =  FXCollections.observableArrayList();
	private ObservableList<File> numberRowsSelected;
	private TextField searchField;
	private Button btnSearch;
	private Button btnDownload;


	public FileWindow() {
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Pesquisar arquivos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fxPanel = new JFXPanel();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

		setMinimumSize(MIN_SIZE);
		setLocationRelativeTo(null);
		add(fxPanel);
	}

	private void initFX(JFXPanel fxPanel) {
		Scene scene = createContentPane();
		fxPanel.setScene(scene);
	}

	private Scene createContentPane() {
		createSearchField();
		btnSearch = new Button("Buscar");
		btnSearch.setOnAction(event -> searchAction());
		btnDownload = new Button("Download");
		createTable();
		bindingDownloadButton();

		MigPane layout = new MigPane("ins 30","[grow][]", "[][][grow]10[]");           

		layout.add(createRadioButtons(), "wrap");
		layout.add(searchField, "grow");
		layout.add(btnSearch, "wrap");
		layout.add(table, "span, grow");
		layout.add(btnDownload, " span, right");

		Scene scene = new Scene(layout);

		return scene;
	}

	private void bindingDownloadButton() {
		numberRowsSelected = table.getSelectionModel().getSelectedItems();
		btnDownload.disableProperty().bind(Bindings.isEmpty((numberRowsSelected)));
	}

	private void createSearchField() {
		searchField = new TextField();

		searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER)  {
					searchAction();
				}
			}
		});
	}

	private void searchAction() {
		if(partNameOption.isSelected() && (!searchField.getText().equals(""))) {
			cleanTable();
			files = new ArrayList<>();
			files.addAll(new FileController().searchFilesbyNamePart(searchField.getText()));
			data.addAll(files);
		}
		else if (exactNameOption.isSelected() && (!searchField.getText().equals(""))){
			cleanTable();
			file = new File();
			file = new FileController().searchSpecificFile(searchField.getText());
			if(file != null)
				data.add(file);
		}
	}

	private void createTable() {
		table = new FileTable();

		table.setItems(data);
	}

	public Node createRadioButtons() {
		ToggleGroup toggle = new ToggleGroup();

		partNameOption = new RadioButton("Parte do nome");
		partNameOption.setToggleGroup(toggle);

		exactNameOption = new RadioButton("Nome exato");
		exactNameOption.setToggleGroup(toggle);
		exactNameOption.setSelected(true);

		MigPane layout = new MigPane("","[][][][]", "[]");           
		layout.add(partNameOption, "");
		layout.add(exactNameOption, "");

		return layout;
	}

	public void cleanTable() {
		data.clear();
		table.setItems(data);
	}
}