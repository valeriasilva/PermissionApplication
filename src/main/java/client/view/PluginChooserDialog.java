package client.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import client.controller.PluginController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.components.TextRowFilter;
import client.view.tablemodels.PluginTableModel;
import common.model.Plugin;
import net.miginfocom.swing.MigLayout;

public class PluginChooserDialog extends JDialog {

	private static final int MIN_HEIGHT = 300;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private ApplicationTextField searchField;
	private JTable pluginsTable;
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private Action confirmAction;
	private Plugin selectedPlugin;

	public PluginChooserDialog() {
		buildGUI();
		resetFields();
	}

	private void resetFields() {
		this.selectedPlugin = null;
		updateControls();
	}

	private void buildGUI() {
		setTitle("Definir Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPanel());
		loadData();
		setModal(true);
		setMinimumSize(MIN_SIZE);
		setPreferredSize(MIN_SIZE);
		pack();
		setLocationRelativeTo(null);
	}

	private void loadData() {
		PluginTableModel model = (PluginTableModel) getPluginsTable().getModel();
		model.setItems(pluginController.listPlugins());
	}

	private KeyListener createSearchFieldListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				super.keyReleased(event);
				String text = searchField.getText();
				if (text.length() == 0) {
					((TableRowSorter) getPluginsTable().getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter) getPluginsTable().getRowSorter())
							.setRowFilter(new TextRowFilter(text, PluginTableModel.getSearchableColumns()));
				}
			}
		};
	}

	private JTable getPluginsTable() {
		if (pluginsTable == null) {
			pluginsTable = new JTable(new PluginTableModel());
			pluginsTable.setAutoCreateRowSorter(true);
			pluginsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			pluginsTable.getSelectionModel().addListSelectionListener(pluginSelectionListener());
		}
		return pluginsTable;
	}

	private ListSelectionListener pluginSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateControls();
				}
			}

		};
	}

	private void updateControls() {
		boolean selected = getPluginsTable().getSelectedRowCount() > 0;
		getConfirmAction().setEnabled(selected);
	}

	private JPanel createContentPanel() {
		JPanel contentPane = new JPanel(new MigLayout("", "[grow]", "[][grow][]"));
		contentPane.add(getSearchField(), "grow, wrap");
		contentPane.add(new JScrollPane(getPluginsTable()), "grow, wrap");
		contentPane.add(new JButton(getConfirmAction()), "ax right");
		return contentPane;
	}

	private ApplicationTextField getSearchField() {
		if (searchField == null) {
			searchField = new ApplicationTextField();
			searchField.addKeyListener(createSearchFieldListener());
		}
		return searchField;
	}

	private Action getConfirmAction() {
		if (confirmAction == null) {
			confirmAction = new AbstractAction("Confirmar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					choosePlugin();
				}
			};
		}
		return confirmAction;
	}

	private void choosePlugin() {
		selectedPlugin = ptmodel.getPlugin(getPluginsTable().getSelectedRow());
		this.setVisible(false);
	}

	public Plugin getSelectedPlugin() {
		return selectedPlugin;
	}

}
