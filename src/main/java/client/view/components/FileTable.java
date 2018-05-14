package client.view.components;

import common.model.File;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FileTable extends TableView<File> {
	
	private static final String FIRSTCOL_NAME = "Arquivo";
	private static final String SECCOL_NAME = "Tamanho";
	
	TableColumn<File, String> firstCol;
	TableColumn<File, Long> secondCol;

	public FileTable() {
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);	
		createColumns();
		addColumns();
		setCells();
	}
	
	public void createColumns(){
		firstCol = new TableColumn<>(FIRSTCOL_NAME);
		secondCol = new TableColumn<>(SECCOL_NAME);
	}
	
	public void addColumns() {
		this.getColumns().add(firstCol);
		this.getColumns().add(secondCol);
	}
	
	public void setCells() {
		firstCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
		secondCol.setCellValueFactory(cell -> cell.getValue().sizeProperty().asObject());
	}
}
