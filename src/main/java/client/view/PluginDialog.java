package client.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import client.controller.PluginController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.components.DescriptionTextArea;
import client.view.components.TextRowFilter;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.PluginTableModel;
import common.model.Plugin;
import net.miginfocom.swing.MigLayout;

public class PluginDialog extends JDialog {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JPanel contentPane;
	private ApplicationTextField pluginNameTField;
	private JTable pluginsTable;
	private ApplicationTextField pluginSearchField;
	private DescriptionTextArea pluginDescriptionJTArea;
	private Plugin plugin = null;
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private FeatureTableModel ftmodel = new FeatureTableModel();
	private JTable pluginFeaturesTable;
	private AbstractAction saveAction;
	private AbstractAction deleteAction;
	private JPanel pluginDetailsPane;

	public PluginDialog() {
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setModal(true);
		setLocationRelativeTo(null);
	}

	private Container getPluginDetailsPane() {

		if (pluginDetailsPane == null) {

			pluginNameTField = new ApplicationTextField();
			pluginDescriptionJTArea = new DescriptionTextArea();

			pluginDetailsPane = new JPanel(new MigLayout("ins 0", "[right][grow]", "[][]10[grow]"));
			pluginDetailsPane.add(new JLabel("Nome:"));
			pluginDetailsPane.add(pluginNameTField, "grow, wrap");

			pluginDetailsPane.add(new JLabel("Descrição:"), "ay top");
			pluginDetailsPane.add(pluginDescriptionJTArea, "grow, wrap");

			pluginDetailsPane.add(createFeaturesTablePanel(), "spanx, grow");

			pluginDetailsPane.setVisible(false);

		}

		return pluginDetailsPane;
	}

	private Container createContentPane() {

		pluginsTable = new JTable(getPluginTableModel());
		pluginsTable.getSelectionModel().addListSelectionListener(selectPluginListener());
		pluginsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pluginsTable.setAutoCreateRowSorter(true);

		pluginSearchField = new ApplicationTextField();
		pluginSearchField.addKeyListener(createSearchFieldListener());

		contentPane = new JPanel(new MigLayout("ins 10", "[grow][]", "[][grow]15[]"));
		contentPane.add(pluginSearchField, "grow");
		contentPane.add(new JButton(createNewPluginAction()), "ax right, wrap");
		
		contentPane.add(new JScrollPane(pluginsTable), "spanx, grow, wrap");
		
		contentPane.add(getPluginDetailsPane(), "grow, spanx, wrap, hidemode 3");
		contentPane.add(createControlPanel(), "spanx, ax right");

		return contentPane;
	}

	private ListSelectionListener selectPluginListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateActions();
					boolean selected = pluginsTable.getSelectedRowCount() > 0;
					if (selected) {
						fillFields();
					} else {
						clearFields();
					}
				}
			}
		};
	}

	private KeyListener createSearchFieldListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				super.keyReleased(event);
				String text = pluginSearchField.getText();
				if (text.length() == 0) {
					((TableRowSorter) pluginsTable.getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter) pluginsTable.getRowSorter())
							.setRowFilter(new TextRowFilter(text, PluginTableModel.getSearchableColumns()));
				}
			}
		};
	}

	private Component createFeaturesTablePanel() {
		pluginFeaturesTable = new JTable(ftmodel);
		JPanel featuresTablePane = new JPanel(new MigLayout("", "[grow]", "[grow]"));
		featuresTablePane.add(new JScrollPane(pluginFeaturesTable), "grow");
		featuresTablePane.setBorder(BorderFactory.createTitledBorder("Funcionalidades"));
		return featuresTablePane;
	}

	private Component createControlPanel() {

		JButton btnSave = new JButton(getSaveAction());
		JButton btnDelete = new JButton(getDeleteAction());
		JButton btnCancel = new JButton(createCancelAction());

		JPanel controlPane = new JPanel(new MigLayout("", "", ""));
		controlPane.add(btnDelete, "sg btns, ax right");
		controlPane.add(btnSave, "sg btns, ax right");
		controlPane.add(btnCancel, "sg btns, ax right");

		return controlPane;
	}

	private Action createCancelAction() {
		return new AbstractAction("Cancelar") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		};
	}

	private Action createNewPluginAction() {
		return new AbstractAction("Novo") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				clearFields();
				plugin = null;
				getPluginDetailsPane().setVisible(true);
			}
		};
	}

	private Action getSaveAction() {
		if (saveAction == null) {
			saveAction = new AbstractAction("Salvar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					save();
				}
			};
			saveAction.setEnabled(false);
		}
		return saveAction;
	}

	private Action getDeleteAction() {
		if (deleteAction == null) {
			deleteAction = new AbstractAction("Excluir") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					deletePlugin();
				}
			};
			deleteAction.setEnabled(false);
		}
		return deleteAction;
	}

	private void save() {
		// Se a a variável plugin for null, trata-se de um insert
		if (plugin == null) {
			pluginController.save(pluginNameTField.getText(), pluginDescriptionJTArea.getText());
			ptmodel = null;
			pluginsTable.setModel(getPluginTableModel());
			pluginController.showInfo(this, "Plugin salvo com sucesso!");
			clearFields();
		} else {
			// se a variável plugin não for nula, trata-se de um update
			plugin.setName(pluginNameTField.getText());
			plugin.setDescription(pluginDescriptionJTArea.getText());

			updatePlugin(plugin);
			ptmodel = null;
			pluginsTable.setModel(getPluginTableModel());
			clearFields();
		}
	}

	private TableModel getPluginTableModel() {
		if (ptmodel == null) {
			ptmodel = new PluginTableModel();
			ptmodel.setItems(pluginController.listPlugins());
		}
		return ptmodel;
	}

	private void deletePlugin() {
		final int tableIndex = pluginsTable.getSelectedRow();
		plugin = ptmodel.getPlugin(tableIndex);
		pluginController.delete(plugin.getId());
		clearFields();
		ptmodel.removePlugin(tableIndex);
		plugin = null;
		pluginController.showInfo(this, "Plugin removido com sucesso");
	}

	private void updatePlugin(final Plugin p) {
		pluginController.update(p.getId(), p.getName(), p.getDescription());
	}

	private void fillFields() {
		getPluginDetailsPane().setVisible(true);
		plugin = getSelectedPluginFromTable();
		pluginNameTField.setText(plugin.getName());
		pluginDescriptionJTArea.setText(plugin.getDescription());
	}

	private Plugin getSelectedPluginFromTable() {
		return ptmodel.getPlugin(pluginsTable.getSelectedRow());
	}

	private void clearFields() {
		getPluginDetailsPane().setVisible(false);
		pluginNameTField.setText(null);
		pluginDescriptionJTArea.setText(null);
	}

	private void updateActions() {
		boolean selected = pluginsTable.getSelectedRowCount() > 0;
		getSaveAction().setEnabled(selected);
		getDeleteAction().setEnabled(selected);
	}
}