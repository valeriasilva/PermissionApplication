package client.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import client.controller.FeatureController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.components.TextRowFilter;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.PluginTableModel;
import common.model.Feature;
import net.miginfocom.swing.MigLayout;


public class FeatureDialog extends JDialog {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private final FeatureController featureController;

	private ApplicationTextField featureNameField;
	private ApplicationTextField searchFeatureField;
	private JTextArea featureDescriptionArea;
	private JTable table;
	private FeatureTableModel ftmodel;
	private ApplicationTextField pluginOfFeature;
	private Feature feature = null;
	private PluginChooserDialog pluginChooser;
	private AbstractAction saveAction;
	private JButton BtnSaveFeature;
	private JButton btnExcluir;
	private JPanel featureDetailsPane;

	/**
	 * Create the frame.
	 */
	public FeatureDialog() {
		featureController = new FeatureController(this);
		pluginChooser = new PluginChooserDialog();
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Funcionalidades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setLocationRelativeTo(null);
		setModal(true);
		updateControls(Boolean.FALSE);
	}

	private Container createContentPane() {
		JPanel contentPane = new JPanel(new MigLayout("ins 10", "[grow][]", "[][grow]15[]"));
		contentPane.add(getSearchFeatureField(), "spanx 4, grow, split 2");
		contentPane.add(new JButton(getNewFeature()), "wrap, sg btn1");

		contentPane.add(new JScrollPane(getTable()), "grow, wrap, spanx");

		contentPane.add(getFeatureDetailsPane(), "grow, spanx, wrap, hidemode 3");

		contentPane.add(createControlPanel(), "spanx, ax right");

		return contentPane;
	}

	public ApplicationTextField getSearchFeatureField() {
		if (searchFeatureField == null) {
			searchFeatureField = new ApplicationTextField();
			searchFeatureField.addKeyListener(createSearchFeatureListener());
		}
		return searchFeatureField;
	}

	private KeyListener createSearchFeatureListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				super.keyReleased(event);
				String text = searchFeatureField.getText();
				if (text.length() == 0) {
					((TableRowSorter) getTable().getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter) getTable().getRowSorter())
					.setRowFilter(new TextRowFilter(text, FeatureTableModel.getSearchableColumns()));
				}
			}
		};
	}

	public void setSearchFeatureField(ApplicationTextField searchFeatureField) {
		this.searchFeatureField = searchFeatureField;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable((getFeatureTableModel()));
			table.setAutoCreateRowSorter(true);
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getSelectionModel().addListSelectionListener(featureSelectionListener());
		}
		return table;
	}

	private ListSelectionListener featureSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					boolean selected = getTable().getSelectedRowCount() > 0;
					if(selected) {
						updateControls(selected);
						fillFields();
					}else {
						updateControls(selected);
						clearFields();
					}
				}
			}
		};
	}

	private void updateControls(boolean state) {
		BtnSaveFeature.setEnabled(state);
		btnExcluir.setEnabled(state);
		getFeatureDetailsPane().setVisible(state);
	}

	private Component createControlPanel() {
		BtnSaveFeature = new JButton(getSaveFeatureAction());
		btnExcluir = new JButton(createDeleteAction());
		final JButton cancelBtn = new JButton("Cancelar");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel controlPane = new JPanel(new MigLayout("", "", ""));
		controlPane.add(btnExcluir, "sg btns, ax right");
		controlPane.add(BtnSaveFeature, "sg btns, ax right");
		controlPane.add(cancelBtn, "sg btns, ax right");

		return controlPane;
	}

	private Action createDeleteAction() {
		return new AbstractAction("Excluir") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				deleteFeature();
			}
		};
	}

	private Action getSaveFeatureAction() {
		if (saveAction == null) {
			saveAction = new AbstractAction("Salvar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					onClickSave();
				}
			};
		}
		return saveAction;
	}

	private void fillFields() {
		final int tableIndex = table.getSelectedRow();
		if (tableIndex >= 0) {
			feature = ftmodel.getFeature(tableIndex);
			featureNameField.setText(feature.getName());
			featureDescriptionArea.setText(feature.getDescription());
			pluginOfFeature.setText(feature.getPlugin().getName());
		}
	}

	public Action getNewFeature() {
		return new AbstractAction("Novo") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createNewFeature();
			}
		};
	}

	private void createNewFeature() {
		clearFields();
		feature = null;
		updateControls(Boolean.TRUE);
		getFeatureDetailsPane().setVisible(true);
	}

	private Component getFeatureDetailsPane() {
		if (featureDetailsPane == null) {
			featureNameField = new ApplicationTextField();
			featureDescriptionArea = new JTextArea();
			featureDescriptionArea.setLineWrap(true);
			pluginOfFeature = new ApplicationTextField("");
			pluginOfFeature.setEditable(false);

			featureDetailsPane = new JPanel(new MigLayout("ins 0", "[right][grow][]", "[][][grow][]"));

			featureDetailsPane.add(new JLabel("Nome:"));
			featureDetailsPane.add(featureNameField, "grow, wrap");

			featureDetailsPane.add(new JLabel("Descrição:"), "ay top");
			featureDetailsPane.add(new JScrollPane(featureDescriptionArea), "h 60:60:60, spanx, grow, wrap");

			featureDetailsPane.add(new JLabel("Plugin:"));
			featureDetailsPane.add(pluginOfFeature, "grow, split 2");
			featureDetailsPane.add(new JButton(getPluginChooserDialog()), "wrap");

			featureDetailsPane.setVisible(false);
		}
		return featureDetailsPane;
	}

	private Action getPluginChooserDialog() {
		return new AbstractAction("...") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				pluginChooser = new PluginChooserDialog();
				pluginChooser.setVisible(true);
				pluginOfFeature.setText(pluginChooser.getSelectedPlugin().getName());
			}
		};
	}

	private void clearFields() {
		getFeatureDetailsPane().setVisible(false);
		featureNameField.setText(null);
		featureDescriptionArea.setText(null);
		pluginOfFeature.setText("-");
	}
	
	private void deleteFeature() {
		final int tableIndex = table.getSelectedRow();

		if (tableIndex < 0) {
			featureController.showInfo("Selecione uma Funcionalidade para remover");
		} else {
			feature = ftmodel.getFeature(tableIndex);

			featureController.delete(feature.getId());
			clearFields();
			ftmodel.removeFeature(tableIndex);
			featureController.showInfo("Funcionalidade removida com sucesso");
		}
	}
	
	private FeatureTableModel getFeatureTableModel() {
		if (ftmodel == null) {
			ftmodel = new FeatureTableModel(featureController.getAllFeatures());
		}
		return ftmodel;
	}

	private void onClickSave() {
		/* Se a a variável feature for null, trata-se de um insert */
		if (feature == null) {
			if (pluginChooser.getSelectedPlugin() == null)
				featureController.showInfo("Informe o Plugin ao qual esta Funcionalidade pertence");
			else {
				featureController.save(featureNameField.getText(), featureDescriptionArea.getText(), pluginChooser.getSelectedPlugin());
				ftmodel = null;
				table.setModel(getFeatureTableModel());
				featureController.showInfo("Funcionalidade salva com sucesso!");
				clearFields();
			}
		} else {
			/* se a variável feature não for nula, trata-se de um update */
			feature.setName(featureNameField.getText());
			feature.setDescription(featureDescriptionArea.getText());
			feature.setPlugin(pluginChooser.getSelectedPlugin());
			featureController.update(feature);

			clearFields();
			ftmodel = null;
			table.setModel(getFeatureTableModel());
		}
	}
	
	public void setTable(JTable table) {
		this.table = table;
	}
}
