package client.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import client.controller.PluginController;
import client.util.Util;
import client.view.components.DescriptionTextArea;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.PluginTableModel;
import common.model.Feature;
import common.model.Plugin;
import net.miginfocom.swing.MigLayout;

public class PluginWindow extends JFrame {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private JPanel contentPane;
	private JTextField pluginNameTField;
	private JTable pluginsTable;
	private JTextField pluginSearchJTField;
	private DescriptionTextArea pluginDescriptionJTArea;
	private List<Plugin> pluginList = new PluginController().listPlugins();
	private Plugin plugin = null;
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private FeatureTableModel ftmodel = new FeatureTableModel();
	private JTable pluginFeaturesTable;

	public PluginWindow() {
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setLocationRelativeTo(null);
	}

	private Container createContentPane() {

		pluginNameTField = new JTextField();
		pluginSearchJTField = new JTextField();
		pluginDescriptionJTArea = new DescriptionTextArea();
		JButton btnSearchPlugin = new JButton(createSearchAction());
		pluginsTable = new JTable(getPluginTableModel());
		pluginsTable.addMouseListener(createMouseListenerForTablePlugins());

		contentPane = new JPanel(new MigLayout("ins 10", "[right][grow][right]", "[][grow][][grow][grow][]"));
		contentPane.add(pluginSearchJTField, "spanx 2, grow");
		contentPane.add(btnSearchPlugin, "sg btn1, wrap");

		contentPane.add(new JScrollPane(pluginsTable), "spanx, grow, wrap");

		contentPane.add(new JLabel("Nome"));
		contentPane.add(pluginNameTField, "spanx 2, grow, wrap");

		contentPane.add(new JLabel("Descrição"));
		contentPane.add(pluginDescriptionJTArea, "spanx 2, grow, wrap");

		contentPane.add(createFeaturesTablePanel(), "spanx, grow, wrap");
		contentPane.add(createControlPanel(), "spanx, ax right");

		return contentPane;
	}

	private Component createFeaturesTablePanel() {

		pluginFeaturesTable = new JTable(ftmodel);

		JPanel featuresTablePane = new JPanel(new MigLayout("ins 0", "[grow]", "[grow]"));
		featuresTablePane.add(new JScrollPane(pluginFeaturesTable), "grow");

		featuresTablePane.setBorder(BorderFactory.createTitledBorder("Funcionalidades associadas"));

		return featuresTablePane;
	}

	private Component createControlPanel() {

		JButton btnSave = new JButton(createSaveAction());
		JButton btnDelete = new JButton(createDeleteAction());
		JButton btnNewPlugin = new JButton(createNewPluginAction());
		JButton btnCancel = new JButton(createCancelAction());

		JPanel controlPane = new JPanel(new MigLayout("", "", ""));
		controlPane.add(btnSave, "sg btns, ax right");
		controlPane.add(btnDelete, "sg btns, ax right");
		controlPane.add(btnNewPlugin, "sg btns, ax right");
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
		return new AbstractAction("Novo Plugin") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				newPlugin();
			}
		};
	}

	private Action createSearchAction() {
		return new AbstractAction("Buscar") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (pluginSearchJTField.getText() != "") {
					pluginList = pluginController.searchPluginByName(pluginSearchJTField.getText());
					ptmodel = null;
					pluginsTable.setModel(getPluginTableModel(pluginList));
				}
			}
		};
	}

	private MouseAdapter createMouseListenerForTablePlugins() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				fillFields();
				pluginFeaturesTable.setModel(getFeaturesByPlugin());
			}
		};
	}

	private Action createSaveAction() {
		return new AbstractAction("Salvar") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				onClickSave();
			}
		};
	}

	private Action createDeleteAction() {
		return new AbstractAction("Excluir") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				deletePlugin();
			}
		};
	}

	private void onClickSave() {
		// Se a a variável plugin for null, trata-se de um insert
		if (plugin == null) {
			pluginController.save(pluginNameTField.getText(), pluginDescriptionJTArea.getText());
			ptmodel = null;
			pluginsTable.setModel(getPluginTableModel());

			showMessage("Plugin salvo com sucesso!");
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

	private TableModel getPluginTableModel(final List<Plugin> pList) {
		if (ptmodel == null) {
			ptmodel = new PluginTableModel(pList);
		}
		return ptmodel;
	}

	private TableModel getPluginTableModel() {
		if (ptmodel == null) {
			ptmodel = new PluginTableModel(new PluginController().listPlugins());
		}
		return ptmodel;
	}

	private TableModel getFeaturesByPlugin() {
		final Plugin selectedPlugin = getSelectedPluginFromTable();
		final List<Feature> featuresByPlugin = pluginController.getFeaturesByPlugin(selectedPlugin.getId().longValue());

		ftmodel = new FeatureTableModel(featuresByPlugin);

		return ftmodel;
	}

	private void deletePlugin() {
		final int tableIndex = pluginsTable.getSelectedRow();

		if (tableIndex < 0) {
			showMessage("Selecione um  Plugin para remover");
		} else {
			plugin = ptmodel.getPlugin(tableIndex);
			pluginController.delete(plugin.getId());
			clearFields();
			ptmodel.removePlugin(tableIndex);
			plugin = null;
			showMessage("Plugin removido com sucesso");
		}
	}

	private void showMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg);

	}

	private void updatePlugin(final Plugin p) {
		pluginController.update(p.getId(), p.getName(), p.getDescription());
	}

	private void fillFields() {
		plugin = getSelectedPluginFromTable();

		pluginNameTField.setText(plugin.getName());
		pluginDescriptionJTArea.setText(plugin.getDescription());
	}

	private Plugin getSelectedPluginFromTable() {
		Plugin p = new Plugin();
		final int tableIndex = pluginsTable.getSelectedRow();

		if (tableIndex >= 0) {
			p = ptmodel.getPlugin(tableIndex);
			return p;
		} else
			showMessage("Selecione um Plugin");
		return p;
	}

	private void newPlugin() {
		clearFields();
		plugin = null;
	}

	private void clearFields() {
		pluginNameTField.setText("");
		pluginDescriptionJTArea.setText("");
	}
}