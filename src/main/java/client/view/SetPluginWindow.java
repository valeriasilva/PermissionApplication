package client.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import client.controller.PluginController;
import client.view.tablemodels.PluginTableModel;
import common.model.Plugin;
import net.miginfocom.swing.MigLayout;

public class SetPluginWindow extends JDialog {

	private JPanel contentPane;
	private JTextField search_plugin_feature;
	private JTable table_plugins;
	private List<Plugin> pluginList = new ArrayList<Plugin>();
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private Plugin pluginSelected;
	private Plugin plugin = null;
	private Action loadTableAction;
	private Action searchAction;
	private Action saveAction;

	public SetPluginWindow() {
		buildGUI();
	}

	private void buildGUI() {

		setTitle("Definir Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPanel());
		setLocationRelativeTo(null);
		setModal(true);
		pack();
	}

	private JPanel createContentPanel() {

		search_plugin_feature = new JTextField();

		final JButton searchBtn = new JButton(getSearchAction());

		table_plugins = new JTable(getPluginTableModel());

		final JButton loadButton = new JButton(getLoadTableAction());

		final JButton selectBtn = new JButton(getSaveAction());

		contentPane = new JPanel(new MigLayout("", "[grow][][]", "[][grow][]"));
		contentPane.add(search_plugin_feature, "grow");
		contentPane.add(searchBtn, "sg btns");
		contentPane.add(loadButton, "sg btns, wrap");
		contentPane.add(new JScrollPane(table_plugins), "grow, wrap, spanx");
		contentPane.add(selectBtn, "sg btns, skip 2");

		return contentPane;
	}

	private Action getSaveAction() {
		if (saveAction == null) {
			saveAction = new AbstractAction("Salvar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					choosePlugin();
				}
			};
		}
		return saveAction;
	}

	private Action getSearchAction() {
		if (searchAction == null) {
			searchAction = new AbstractAction("Buscar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final String searchString = search_plugin_feature.getText();
					if (searchString.isEmpty() != true) {
						pluginList = pluginController.searchPluginByName(searchString);
						ptmodel = null;
						table_plugins.setModel(getPluginTableModel(pluginList));
					}
				}
			};
		}
		return searchAction;
	}

	private Action getLoadTableAction() {
		if (loadTableAction == null) {
			loadTableAction = new AbstractAction("Carregar tabela") {
				@Override
				public void actionPerformed(ActionEvent e) {
					ptmodel = null;
					table_plugins.setModel(getPluginTableModel());
				}
			};
		}
		return loadTableAction;
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

	private void choosePlugin() {
		final int tableIndex = table_plugins.getSelectedRow();

		if (tableIndex < 0) {
			JOptionPane.showMessageDialog(null, "Selecione um Plugin");
		} else {
			plugin = ptmodel.getPlugin(tableIndex);
			this.setVisible(false);
		}
	}

	public Plugin getPluginSelected() {
		return pluginSelected;
	}

	public void setPluginSelected(final Plugin pluginSelected) {
		this.pluginSelected = pluginSelected;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(final Plugin plugin) {
		this.plugin = plugin;
	}
}
