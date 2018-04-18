package client.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import client.controller.ApplicationController;
import client.controller.Controller;
import client.controller.PermissionController;
import client.controller.UserController;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.UserTableModel;
import common.model.Feature;
import common.model.User;

public class PermissionApplication extends JFrame {

	private final Controller applicationController;
	private final UserController userController;
	private final PermissionController permissionController;

	private JTable table_users;
	private UserTableModel utmodel;
	private JTextField text_search_users;
	private JTable table_featuresPermission;
	private JLabel lblFuncionalidadesPermitidas;
	private FeatureTableModel ftmodel = new FeatureTableModel();

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PermissionApplication().setVisible(true);
			}
		});
	}

	public PermissionApplication() {
		this.applicationController = new ApplicationController();
		this.userController = new UserController(this);
		this.permissionController = new PermissionController(this);
		initialize();
	}

	private void initialize() {
		setTitle("Permissões de Funcionalidades");
		setBounds(100, 100, 610, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		final JButton btnSave = new JButton("Fechar");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(1);
			}
		});
		btnSave.setBounds(500, 390, 89, 23);
		getContentPane().add(btnSave);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 243, 381, -151);
		getContentPane().add(scrollPane);

		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(29, 75, 534, 128);
		getContentPane().add(scrollPane_1);

		table_users = new JTable(getUserTableModel());
		table_users.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(table_users);

		table_users.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				table_featuresPermission.setModel(getFeaturesPermittedForSelectedUser());
			}
		});

		lblFuncionalidadesPermitidas = new JLabel("Funcionalidades permitidas: ");
		lblFuncionalidadesPermitidas.setBounds(29, 214, 195, 14);
		getContentPane().add(lblFuncionalidadesPermitidas);

		final JLabel lblAtribuirPermisses = new JLabel("Permissões de Funcionalidades");
		lblAtribuirPermisses.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAtribuirPermisses.setBounds(193, 0, 247, 23);
		getContentPane().add(lblAtribuirPermisses);

		final JButton btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				List<User> usersList = new ArrayList<User>();

				if (text_search_users.getText() != "") {
					usersList = userController.listUsersByName(text_search_users.getText());
					utmodel = null;
					table_users.setModel(getUserTableModel(usersList));
				}
			}
		});
		btnNewButton_1.setBounds(474, 47, 89, 23);
		getContentPane().add(btnNewButton_1);

		text_search_users = new JTextField();
		text_search_users.setBounds(29, 48, 435, 20);
		getContentPane().add(text_search_users);
		text_search_users.setColumns(10);

		final JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(29, 231, 534, 105);
		getContentPane().add(scrollPane_2);

		table_featuresPermission = new JTable(ftmodel);
		scrollPane_2.setViewportView(table_featuresPermission);

		final JButton btnIncluir = new JButton("Incluir Permissão");
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				openDialogPermission();
			}
		});
		btnIncluir.setBounds(29, 344, 144, 23);
		getContentPane().add(btnIncluir);

		final JButton btnExcluirPermisso = new JButton("Excluir Permissão");
		btnExcluirPermisso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final int tableFeaturesIndex = table_featuresPermission.getSelectedRow();
				final int tableUsersIndex = table_users.getSelectedRow();

				if (tableFeaturesIndex < 0) {
					applicationController.showInfo(PermissionApplication.this,
							"Selecione a funcionalidade a qual deseja retirar permissão");
				} else {
					final Long featureId = ftmodel.getFeature(tableFeaturesIndex).getId();
					final Long userId = utmodel.getUser(tableUsersIndex).getId();
					permissionController.delete(featureId, userId);
					ftmodel.removeFeature(tableFeaturesIndex);
					applicationController.showInfo(PermissionApplication.this, "Permissão removida com sucesso");
				}
			}
		});
		btnExcluirPermisso.setBounds(419, 347, 144, 23);
		getContentPane().add(btnExcluirPermisso);

		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);

		final JMenuItem mntmPlugins = new JMenuItem("Plugins");
		mntmPlugins.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final PluginWindow pluginWindow = new PluginWindow();
				pluginWindow.setVisible(true);
			}
		});
		mnArquivo.add(mntmPlugins);

		final JMenuItem mntmFuncionalidades = new JMenuItem("Funcionalidades");
		mntmFuncionalidades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final FeatureWindow featureWindow = new FeatureWindow();
				featureWindow.setVisible(true);
			}
		});
		mnArquivo.add(mntmFuncionalidades);

		final JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnArquivo.add(mntmSair);

	}

	private TableModel getUserTableModel() {
		if (utmodel == null) {
			utmodel = new UserTableModel(userController.listUsers());
		}
		return utmodel;
	}

	private TableModel getUserTableModel(final List<User> users) {
		if (utmodel == null) {
			utmodel = new UserTableModel(users);
		}
		return utmodel;
	}

	public void openDialogPermission() {
		final int tableIndex = table_users.getSelectedRow();

		if (tableIndex < 0) {
			applicationController.showInfo(this, "Selecione o usuário ao qual pretente atribuir nova permissão");
		} else {
			final User userSelected = utmodel.getUser(tableIndex);
			final FeaturePermissionChooserDialog windowToSetPermission = new FeaturePermissionChooserDialog(
					userSelected, this);
			windowToSetPermission.setVisible(true);
		}
	}

	private TableModel getFeaturesPermittedForSelectedUser() {
		final int tableIndex = table_users.getSelectedRow();
		List<Feature> featuresPermitted = new ArrayList<Feature>();

		if (tableIndex >= 0) {
			final User userSelected = utmodel.getUser(tableIndex);
			featuresPermitted = permissionController.listFeaturesPermittedFor(userSelected.getId());
			ftmodel = new FeatureTableModel(featuresPermitted);
			return ftmodel;
		}
		return ftmodel;
	}

	public JTable getTable_users() {
		return table_users;
	}

	public void setTable_users(final JTable table_users) {
		this.table_users = table_users;
	}

	public JLabel getLabel_permissoes() {
		return lblFuncionalidadesPermitidas;
	}

	public void setLabel_permissoes(final JLabel label_permissoes) {
		this.lblFuncionalidadesPermitidas = label_permissoes;
	}

	public JTable getTable_featuresPermission() {
		return table_featuresPermission;
	}

	public void setTable_featuresPermission(final JTable table_featuresPermission) {
		this.table_featuresPermission = table_featuresPermission;
	}

	public FeatureTableModel getFtmodel() {
		return ftmodel;
	}

	public void setFtmodel(final FeatureTableModel ftmodel) {
		this.ftmodel = ftmodel;
	}
}
