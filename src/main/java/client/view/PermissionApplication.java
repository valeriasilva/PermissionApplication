package client.view;

import java.awt.EventQueue;
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
import javax.swing.table.TableModel;

import client.controller.ApplicationController;
import client.controller.Controller;
import client.controller.PermissionController;
import client.controller.UserController;
import client.view.components.ApplicationTextField;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.UserTableModel;
import common.model.Feature;
import common.model.User;

public class PermissionApplication extends JFrame {

	private final Controller applicationController;
	private final UserController userController;
	private final PermissionController permissionController;

	private JTable usersTables;
	private UserTableModel utmodel;
	private ApplicationTextField searchUserField;
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
		setLocationRelativeTo(null);
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

		usersTables = new JTable(getUserTableModel());
		usersTables.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(usersTables);

		usersTables.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				table_featuresPermission.setModel(getFeaturesPermittedForSelectedUser());
			}
		});

		lblFuncionalidadesPermitidas = new JLabel("Permissões: ");
		lblFuncionalidadesPermitidas.setBounds(29, 214, 195, 14);
		getContentPane().add(lblFuncionalidadesPermitidas);

		searchUserField = new ApplicationTextField();
		searchUserField.setBounds(29, 48, 435, 20);
		getContentPane().add(searchUserField);
		searchUserField.setColumns(10);

		final JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(29, 231, 534, 105);
		getContentPane().add(scrollPane_2);

		table_featuresPermission = new JTable(ftmodel);
		scrollPane_2.setViewportView(table_featuresPermission);

		final JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				openDialogPermission();
			}
		});
		btnIncluir.setBounds(29, 344, 144, 23);
		getContentPane().add(btnIncluir);

		final JButton btnExcluirPermisso = new JButton("Excluir");
		btnExcluirPermisso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final int tableFeaturesIndex = table_featuresPermission.getSelectedRow();
				final int tableUsersIndex = usersTables.getSelectedRow();

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
				final PluginDialog pluginWindow = new PluginDialog();
				pluginWindow.setVisible(true);
			}
		});
		mnArquivo.add(mntmPlugins);

		final JMenuItem mntmFuncionalidades = new JMenuItem("Funcionalidades");
		mntmFuncionalidades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final FeatureDialog featureWindow = new FeatureDialog();
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

	public void openDialogPermission() {
		final int tableIndex = usersTables.getSelectedRow();

		if (tableIndex < 0) {
			applicationController.showInfo(this, "Selecione o usuário ao qual pretente atribuir nova permissão");
		} else {
			final User selectedUser = utmodel.getUser(tableIndex);
			final FeaturePermissionChooserDialog permissionInsertDialog = new FeaturePermissionChooserDialog(
					selectedUser);
			permissionInsertDialog.setVisible(true);
			Feature feature = permissionInsertDialog.getCreatedFeature();
			if (feature != null) {
				ftmodel.addFeature(feature);
			}
		}
	}

	private TableModel getFeaturesPermittedForSelectedUser() {
		final int tableIndex = usersTables.getSelectedRow();
		List<Feature> featuresPermitted = new ArrayList<Feature>();

		if (tableIndex >= 0) {
			final User userSelected = utmodel.getUser(tableIndex);
			featuresPermitted = permissionController.listFeaturesPermittedFor(userSelected.getId());
			ftmodel = new FeatureTableModel(featuresPermitted);
			return ftmodel;
		}
		return ftmodel;
	}

}
