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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

	private JPanel contentPane;
	private ApplicationTextField searchField;
	private JTable pluginsTable;
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private Plugin pluginSelected;
	private Plugin plugin = null;
	private Action saveAction;

	public PluginChooserDialog() {
		buildGUI();
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
		PluginTableModel model = (PluginTableModel) pluginsTable.getModel();
		model.setItems(pluginController.listPlugins());
	}

	private KeyListener createSearchFieldListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				super.keyReleased(event);
				String text = searchField.getText();
				if (text.length() == 0) {
					((TableRowSorter) pluginsTable.getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter) pluginsTable.getRowSorter())
							.setRowFilter(new TextRowFilter(text, PluginTableModel.getSearchableColumns()));
				}
			}
		};
	}

	private JPanel createContentPanel() {

		pluginsTable = new JTable(new PluginTableModel());
		pluginsTable.setAutoCreateRowSorter(true);

		searchField = new ApplicationTextField();
		searchField.addKeyListener(createSearchFieldListener());

		contentPane = new JPanel(new MigLayout("", "[grow]", "[][grow][]"));
		contentPane.add(searchField, "grow, wrap");
		contentPane.add(new JScrollPane(pluginsTable), "grow, wrap");
		contentPane.add(new JButton(getConfirmAction()), "ax right");

		return contentPane;
	}

	private Action getConfirmAction() {
		if (saveAction == null) {
			saveAction = new AbstractAction("Confirmar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					choosePlugin();
				}
			};
		}
		return saveAction;
	}

	private void choosePlugin() {
		final int tableIndex = pluginsTable.getSelectedRow();

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
