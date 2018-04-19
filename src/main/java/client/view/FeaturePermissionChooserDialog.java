package client.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.controller.FeatureController;
import client.controller.PermissionController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.tablemodels.FeatureTableModel;
import common.model.Feature;
import common.model.User;
import net.miginfocom.swing.MigLayout;

public class FeaturePermissionChooserDialog extends JDialog {

	private static final int MIN_HEIGHT = 300;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private ApplicationTextField featureSearchField;
	private FeatureTableModel featureTableModel;
	private JTable featuresTable;
	private final FeatureController featureController;
	private final PermissionController permissionController;
	private Feature selectedFeature;
	private User selectedUser = new User();
	private JLabel selectedUserLabel;
	private Action confirmAction;

	public FeaturePermissionChooserDialog(final User selectedUser) {
		this.featureController = new FeatureController(this);
		this.permissionController = new PermissionController(this);
		this.selectedUser = selectedUser;
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Atribuir Nova Permissão");
		setModal(true);
		setContentPane(createContentPanel());
		resetFields();
		setMinimumSize(MIN_SIZE);
		setPreferredSize(MIN_SIZE);
		pack();
		setLocationRelativeTo(null);
	}

	private void resetFields() {
		updateControls();
	}

	private void updateControls() {
		boolean selected = featuresTable.getSelectedRowCount() > 0;
		getConfirmAction().setEnabled(selected);
	}

	private Container createContentPanel() {
		featuresTable = new JTable(getFeatureTableModel());
		featuresTable.getSelectionModel().addListSelectionListener(createTableSelectionListener());
		featureSearchField = new ApplicationTextField();
		selectedUserLabel = new JLabel("Atribuição de permissão para: " + selectedUser.getFullname());

		final JButton okButton = new JButton(getConfirmAction());

		final JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel contentPanel = new JPanel(new MigLayout("", "[grow]", "[][][grow][]"));
		contentPanel.add(selectedUserLabel, "grow, wrap");
		contentPanel.add(featureSearchField, "grow, wrap");
		contentPanel.add(new JScrollPane(featuresTable), "grow, wrap");
		contentPanel.add(okButton, "ax right, split 2, sg");
		contentPanel.add(cancelButton, "ax right, sg");

		return contentPanel;
	}

	private ListSelectionListener createTableSelectionListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateControls();
				}
			}
		};
	}

	private Action getConfirmAction() {
		if (confirmAction == null) {
			confirmAction = new AbstractAction("Confirmar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final int tableIndex = featuresTable.getSelectedRow();
					selectedFeature = featureTableModel.getFeature(tableIndex);
					try {
						permissionController.save(selectedUser.getId(), selectedFeature.getId());
					} catch (final ParseException e1) {
						e1.printStackTrace();
					}
					setVisible(false);
				}
			};
		}
		return confirmAction;
	}

	private FeatureTableModel getFeatureTableModel() {
		if (featureTableModel == null) {
			featureTableModel = new FeatureTableModel(featureController.getAllFeatures());
		}
		return featureTableModel;
	}

	public Feature getCreatedFeature() {
		return selectedFeature;
	}

}
