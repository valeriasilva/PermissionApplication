package client.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import client.controller.FeatureController;
import client.controller.PermissionController;
import client.view.tablemodels.FeatureTableModel;
import common.model.Feature;
import common.model.User;

public class FeaturePermissionChooserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField text_featureSearch;
	private FeatureTableModel ftmodel;
	private JTable table_features;
	private List<Feature> featureList;
	private final FeatureController featureController;
	private final PermissionController permissionController;
	private PermissionApplication mainWindow;
	private Feature selectedFeature;
	private User userSelected = new User();
	private JLabel label_userSelected;

	/**
	 * Create the dialog.
	 */
	public FeaturePermissionChooserDialog(final User userSeletedParameter, final PermissionApplication principalFrame) {

		this.featureController = new FeatureController(this);
		this.permissionController = new PermissionController(this);

		setTitle("Atribuir Nova Permissão");

		userSelected = userSeletedParameter;
		mainWindow = principalFrame;

		setBounds(100, 100, 613, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			final JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(20, 86, 554, 155);
			contentPanel.add(scrollPane);

			table_features = new JTable(getFeatureTableModel());
			scrollPane.setViewportView(table_features);
		}

		text_featureSearch = new JTextField();
		text_featureSearch.setColumns(10);
		text_featureSearch.setBounds(20, 55, 455, 20);
		contentPanel.add(text_featureSearch);

		final JButton button = new JButton("Buscar");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				featureList = new ArrayList<Feature>(
						featureController.searchFeatureByName(text_featureSearch.getText()));
				ftmodel = null;
				table_features.setModel(getFeatureTableModel(featureList));
			}
		});
		button.setBounds(485, 52, 89, 23);
		contentPanel.add(button);

		label_userSelected = new JLabel();
		label_userSelected.setBounds(20, 23, 554, 14);
		label_userSelected.setText("Definir nova permissão para: " + userSeletedParameter.getFullname());
		contentPanel.add(label_userSelected);
		{
			final JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final JButton okButton = new JButton("Salvar");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final int tableIndex = table_features.getSelectedRow();
						selectedFeature = new Feature();

						if (tableIndex < 0) {
							JOptionPane.showMessageDialog(null, "Selecione uma Funcionalidade");
						} else {
							selectedFeature = ftmodel.getFeature(tableIndex);
							try {
								permissionController.save(userSelected.getId(), selectedFeature.getId());

								// Atualiza Tabela de Funcionalidades permitidas da Tela Main
								ftmodel = new FeatureTableModel(
										permissionController.listFeaturesPermittedFor(userSelected.getId()));
								mainWindow.getTable_featuresPermission().setModel(ftmodel);
							} catch (final ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							setVisible(false);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				final JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private FeatureTableModel getFeatureTableModel() {
		if (ftmodel == null) {
			ftmodel = new FeatureTableModel(featureController.getAllFeatures());
		}
		return ftmodel;
	}

	private TableModel getFeatureTableModel(final List<Feature> featureList) {
		if (ftmodel == null) {
			ftmodel = new FeatureTableModel(featureList);
		}
		return ftmodel;
	}

	public PermissionApplication getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(final PermissionApplication mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Feature getSelectedFeature() {
		return selectedFeature;
	}

	public void setSelectedFeature(final Feature selectedFeature) {
		this.selectedFeature = selectedFeature;
	}

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(final User userSelected) {
		this.userSelected = userSelected;
	}

	public JLabel getLabel_userSelected() {
		return label_userSelected;
	}

	public void setLabel_userSelected(final JLabel label_userSelected) {
		this.label_userSelected = label_userSelected;
	}
}
