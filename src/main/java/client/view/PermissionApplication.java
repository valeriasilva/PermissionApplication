package client.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import client.controller.ApplicationController;
import client.controller.Controller;
import client.controller.PermissionController;
import client.controller.UserController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.components.TextRowFilter;
import client.view.tablemodels.FeatureTableModel;
import client.view.tablemodels.PluginTableModel;
import client.view.tablemodels.UserTableModel;
import common.model.Feature;
import common.model.User;
import net.miginfocom.swing.MigLayout;

public class PermissionApplication extends JFrame {

	private static final int MIN_HEIGHT = 600;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private final Controller applicationController;
	private final UserController userController;
	private final PermissionController permissionController;

	private JTable usersTable;
	private UserTableModel utmodel;
	private ApplicationTextField searchUserField;
	private JTable featuresPermissionTable;
	private FeatureTableModel ftmodel;
	private User selectedUser;
	private JButton btnIncluir;
	private JButton btnExcluirPermission;

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

		buildGUI();
		resetFields();
	}

	private void resetFields() {
		this.selectedUser = null;
		updateUserControls();
		updateFeatureControls();
	}

	private void updateUserControls() {
		boolean selected = getUsersTable().getSelectedRowCount() > 0;
		btnIncluir.setEnabled(selected);
	}

	private void updateFeatureControls() {
		boolean selected = getFeaturesPermissionTable().getSelectedRowCount() > 0;
		btnExcluirPermission.setEnabled(selected);
	}

	private void buildGUI() {

		setTitle("Gerenciador de Permissões de Funcionalidades de Plugins");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setLocationRelativeTo(null);
		loadData();
	}

	private void loadData() {
		UserTableModel model = (UserTableModel) getUsersTable().getModel();
		model.setItems(userController.listUsers());
	}

	private Container createContentPane() {
		createMenus();

		JPanel contentPane = new JPanel(new MigLayout("ins 10", "[][grow][right]", "[][grow][grow][]"));

		contentPane.add(getSearchUserField(), "spanx, grow");
		contentPane.add(new JScrollPane(getUsersTable()), "spanx, grow");
		contentPane.add(new JScrollPane(getFeaturesPermissionTable()), "spanx, grow");
		contentPane.add(createControlPane(),"spanx, ax right");

		return contentPane;
	}

	private void createMenus() {
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
	}

	private Component createControlPane() {
		final JButton btnClose = new JButton(createActionClose());
		btnIncluir = new JButton(createActionIncludePermission());
		btnExcluirPermission = new JButton(createActionRemovePermission());

		JPanel controlPane = new JPanel(new MigLayout("", "[right]", ""));
		controlPane.add(btnIncluir, "sg btns, ax right");
		controlPane.add(btnExcluirPermission, "sg btns, ax right");		
		controlPane.add(btnClose, "sg btns, ax right");

		return controlPane;
	}

	private ApplicationTextField getSearchUserField() {
		if (searchUserField == null) {
			searchUserField = new ApplicationTextField();
			searchUserField.addKeyListener(createSearchUserFieldListener());
		}
		return searchUserField;
	}


	private KeyListener createSearchUserFieldListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				super.keyReleased(event);
				String text = searchUserField.getText();
				if (text.length() == 0) {
					((TableRowSorter) getUsersTable().getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter) getUsersTable().getRowSorter())
					.setRowFilter(new TextRowFilter(text, UserTableModel.getSearchableColumns()));
				}
			}
		};
	}

	private Action createActionClose() {
		return new AbstractAction("Sair") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		};
	}

	private Action createActionIncludePermission() {
		return new AbstractAction("Incluir Permissão") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				openDialogPermission();
			}
		};
	}

	private Action createActionRemovePermission() {
		return new AbstractAction("Remover permissão") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final int tableFeaturesIndex = featuresPermissionTable.getSelectedRow();
				final int tableUsersIndex = usersTable.getSelectedRow();

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
		};
	}

	private MouseAdapter createMouseListenerForusersTable() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				featuresPermissionTable.setModel(getFeaturesPermittedForSelectedUser());
			}
		};
	}

	private TableModel getUserTableModel() {
		if (utmodel == null) {
			utmodel = new UserTableModel(userController.listUsers());
		}
		return utmodel;
	}

	public void openDialogPermission() {
		final int tableIndex = usersTable.getSelectedRow();

		if (tableIndex < 0) {
			applicationController.showInfo(this, "Selecione o usuário ao qual pretente atribuir nova permissão");
		} else {
			selectedUser = utmodel.getUser(tableIndex);
			final FeaturePermissionChooserDialog permissionInsertDialog = new FeaturePermissionChooserDialog(
					selectedUser);
			permissionInsertDialog.setVisible(true);
			Feature feature = permissionInsertDialog.getCreatedFeature();
			if (feature != null) {
				ftmodel.addFeature(feature);
			}
		}
	}

	public JTable getUsersTable() {
		if (usersTable == null) {
			usersTable = new JTable(getUserTableModel());
			usersTable.setAutoCreateRowSorter(true);
			usersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			usersTable.getSelectionModel().addListSelectionListener(userSelectionListener());
		}
		return usersTable;
	}

	public JTable getFeaturesPermissionTable() {
		if (featuresPermissionTable == null) {
			featuresPermissionTable = new JTable(new FeatureTableModel());
			featuresPermissionTable.setAutoCreateRowSorter(true);
			featuresPermissionTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			featuresPermissionTable.getSelectionModel().addListSelectionListener(featureSelectionListener());
		}
		return featuresPermissionTable;
	}

	private ListSelectionListener featureSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateFeatureControls();
				}
			}
		};
	}

	private ListSelectionListener userSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					featuresPermissionTable.setModel(getFeaturesPermittedForSelectedUser());
					updateUserControls();
				}
			}
		};
	}

	public void setUsersTable(JTable usersTable) {
		this.usersTable = usersTable;
	}

	private TableModel getFeaturesPermittedForSelectedUser() {
		final int tableIndex = usersTable.getSelectedRow();
		List<Feature> featuresPermitted = new ArrayList<Feature>();

		if (tableIndex <= 0) {
			ftmodel = new FeatureTableModel();
		}
		else {
			final User userSelected = utmodel.getUser(tableIndex);
			featuresPermitted = permissionController.listFeaturesPermittedFor(userSelected.getId());
			ftmodel = new FeatureTableModel(featuresPermitted);
			return ftmodel;
		}
		return ftmodel;
	}
}