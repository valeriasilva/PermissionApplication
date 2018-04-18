package client.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import client.controller.PluginController;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.PluginTableModel;
import common.model.Feature;
import common.model.Plugin;

public class PluginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField name_plugin;
	private JTable table_plugins;
	private JTextField search_plugin;
	private JTextArea description_plugin;
	private List<Plugin> pluginList = new PluginController().listPlugins();
	private Plugin plugin = null;
	private PluginController pluginController = new PluginController();
	private PluginTableModel ptmodel;
	private FeatureTableModel ftmodel = new FeatureTableModel();
	private JTable table_featuresOfPlugin;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final PluginWindow frame = new PluginWindow();
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PluginWindow() {
		setTitle("Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 894, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JLabel lblLogin = new JLabel("Nome");
		lblLogin.setBounds(20, 111, 46, 14);
		contentPane.add(lblLogin);

		final JLabel lblName = new JLabel("Descrição");
		lblName.setBounds(20, 157, 70, 14);
		contentPane.add(lblName);

		name_plugin = new JTextField();
		name_plugin.setBounds(20, 126, 188, 20);
		contentPane.add(name_plugin);
		name_plugin.setColumns(10);

		final JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				onClickSave();
			}

		});
		btnSalvar.setBounds(20, 244, 89, 23);
		contentPane.add(btnSalvar);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(263, 76, 508, 165);
		contentPane.add(scrollPane);

		table_plugins = new JTable(getPluginTableModel());
		scrollPane.setViewportView(table_plugins);

		table_plugins.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				fillFields();
				table_featuresOfPlugin.setModel(getFeaturesByPlugin());
			}
		});

		final JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				deletePlugin();
			}
		});
		btnExcluir.setBounds(776, 76, 87, 23);
		contentPane.add(btnExcluir);

		search_plugin = new JTextField();
		search_plugin.setBounds(263, 45, 409, 20);
		contentPane.add(search_plugin);
		search_plugin.setColumns(10);

		final JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (search_plugin.getText() != "") {
					pluginList = pluginController.searchPluginByName(search_plugin.getText());
					ptmodel = null;
					table_plugins.setModel(getPluginTableModel(pluginList));
				}
			}
		});
		btnBuscar.setBounds(682, 44, 89, 23);
		contentPane.add(btnBuscar);

		final JLabel lblPlugins = new JLabel("Plugins");
		lblPlugins.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlugins.setBounds(410, 0, 58, 28);
		contentPane.add(lblPlugins);

		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 171, 188, 62);
		contentPane.add(scrollPane_1);

		description_plugin = new JTextArea();
		scrollPane_1.setViewportView(description_plugin);
		description_plugin.setLineWrap(true);

		final JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				newPlugin();
			}
		});
		btnNovo.setBounds(20, 62, 89, 23);
		contentPane.add(btnNovo);

		final JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(233, 44, 20, 419);
		contentPane.add(separator);

		final JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(263, 281, 508, 152);
		contentPane.add(scrollPane_2);

		table_featuresOfPlugin = new JTable(ftmodel);
		scrollPane_2.setViewportView(table_featuresOfPlugin);

		final JLabel label = new JLabel("Funcionalidades do Plugin selecionado:");
		label.setBounds(263, 264, 286, 14);
		contentPane.add(label);

		final JButton btnSair = new JButton("Cancelar");
		btnSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		});
		btnSair.setBounds(774, 451, 89, 23);
		contentPane.add(btnSair);
	}

	private void onClickSave() {
		// Se a a variável plugin for null, trata-se de um insert
		if (plugin == null) {
			pluginController.save(name_plugin.getText(), description_plugin.getText());
			ptmodel = null;
			table_plugins.setModel(getPluginTableModel());

			showMessage("Plugin salvo com sucesso!");
			clearFields();
		} else {
			// se a variável plugin não for nula, trata-se de um update
			plugin.setName(name_plugin.getText());
			plugin.setDescription(description_plugin.getText());

			updatePlugin(plugin);
			ptmodel = null;
			table_plugins.setModel(getPluginTableModel());
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
		final int tableIndex = table_plugins.getSelectedRow();

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

		name_plugin.setText(plugin.getName());
		description_plugin.setText(plugin.getDescription());
	}

	private Plugin getSelectedPluginFromTable() {
		Plugin p = new Plugin();
		final int tableIndex = table_plugins.getSelectedRow();

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
		name_plugin.setText("");
		description_plugin.setText("");
	}
}
