package client.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import client.controller.FeatureController;
import client.util.Util;
import client.view.components.ApplicationTextField;
import client.view.tablemodels.FeatureTableModel;
import common.model.Feature;
import net.miginfocom.swing.MigLayout;

public class FeatureDialog extends JDialog {

	private static final int MIN_HEIGHT = 500;
	private static final int MIN_WIDTH = (int) (MIN_HEIGHT * Util.GOLDEN_RATIO);
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

	private final FeatureController featureController;

	private ApplicationTextField featureNameField;
	private ApplicationTextField searchFeatureField;
	private JTextArea featureDescriptionArea;
	private JTable table;
	private FeatureTableModel ftmodel;
	private ApplicationTextField pluginOfFeature;
	private Feature feature = null;
	private PluginChooserDialog pluginChooser;
	private AbstractAction saveAction;

	/**
	 * Create the frame.
	 */
	public FeatureDialog() {
		featureController = new FeatureController(this);
		pluginChooser = new PluginChooserDialog();
		buildGUI();
	}

	private void buildGUI() {
		setTitle("Funcionalidades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createContentPane());
		setMinimumSize(MIN_SIZE);
		setLocationRelativeTo(null);
		setModal(true);
	}

	private Container createContentPane() {

		searchFeatureField = new ApplicationTextField();

		table = new JTable(getFeatureTableModel());
		table.addMouseListener(createMouseListenerForTable());

		featureNameField = new ApplicationTextField();

		featureDescriptionArea = new JTextArea();
		featureDescriptionArea.setLineWrap(true);

		pluginOfFeature = new ApplicationTextField("");
		pluginOfFeature.setEditable(false);

		final JButton btnPlugin = new JButton("...");
		btnPlugin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				pluginChooser = new PluginChooserDialog();
				pluginChooser.setVisible(true);
			}
		});

		final JButton newfeatureBtn = new JButton("Novo");
		newfeatureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				newFeature();
			}
		});

		JPanel contentPane = new JPanel(new MigLayout("ins 10", "[right][grow][right][grow]", "[][grow][][grow][][]"));
		contentPane.add(searchFeatureField, "spanx 4, grow, split 2");
		contentPane.add(newfeatureBtn, "wrap, sg btn1");
		contentPane.add(new JScrollPane(table), "grow, wrap, spanx");
		contentPane.add(new JLabel("Nome:"));
		contentPane.add(featureNameField, "grow");
		contentPane.add(new JLabel("Plugin:"));
		contentPane.add(pluginOfFeature, "grow, split 2");
		contentPane.add(btnPlugin, "wrap");
		contentPane.add(new JLabel("Descrição:"), "ay top");
		contentPane.add(new JScrollPane(featureDescriptionArea), "h 60:60:60, spanx, grow, wrap");
		contentPane.add(createControlPanel(), "spanx, ax right");

		return contentPane;
	}

	private Component createControlPanel() {

		final JButton saveFeatureBtn = new JButton(getSaveFeatureAction());
		final JButton btnExcluir = new JButton(createDeleteAction());
		final JButton cancelBtn = new JButton("Cancelar");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel controlPane = new JPanel(new MigLayout("", "", ""));
		controlPane.add(btnExcluir, "sg btns, ax right");
		controlPane.add(saveFeatureBtn, "sg btns, ax right");
		controlPane.add(cancelBtn, "sg btns, ax right");

		return controlPane;
	}

	private Action createDeleteAction() {
		return new AbstractAction("Excluir") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				deleteFeature();
			}
		};
	}

	private MouseAdapter createMouseListenerForTable() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				fillFields();
			}
		};
	}

	private Action getSaveFeatureAction() {
		if (saveAction == null) {
			saveAction = new AbstractAction("Salvar") {
				@Override
				public void actionPerformed(final ActionEvent e) {
					onClickSave();
				}
			};
		}
		return saveAction;
	}

	private FeatureTableModel getFeatureTableModel() {
		if (ftmodel == null) {
			ftmodel = new FeatureTableModel(featureController.getAllFeatures());
		}
		return ftmodel;
	}

	private void onClickSave() {
		/* Se a a variável feature for null, trata-se de um insert */
		if (feature == null) {
			if (pluginChooser.getSelectedPlugin() == null)
				featureController.showInfo("Informe o Plugin ao qual esta Funcionalidade pertence");
			else {
				final Long idPlugin = pluginChooser.getSelectedPlugin().getId();
				featureController.save(featureNameField.getText(), featureDescriptionArea.getText(), idPlugin);
				ftmodel = null;
				table.setModel(getFeatureTableModel());
				featureController.showInfo("Funcionalidade salva com sucesso!");
				clearFields();
			}
		} else {
			/* se a variável feature não for nula, trata-se de um update */
			feature.setName(featureNameField.getText());
			feature.setDescription(featureDescriptionArea.getText());

			updateFeature(feature);
			clearFields();
			ftmodel = null;
			table.setModel(getFeatureTableModel());
		}
	}

	private void deleteFeature() {
		final int tableIndex = table.getSelectedRow();

		if (tableIndex < 0) {
			featureController.showInfo("Selecione uma Funcionalidade para remover");
		} else {
			feature = ftmodel.getFeature(tableIndex);

			featureController.delete(feature.getId());
			clearFields();
			ftmodel.removeFeature(tableIndex);
			featureController.showInfo("Funcionalidade removida com sucesso");
		}
	}

	private void updateFeature(final Feature f) {
		featureController.update(f.getId(), f.getName(), f.getDescription());
	}

	private void fillFields() {
		final int tableIndex = table.getSelectedRow();
		if (tableIndex >= 0) {
			feature = ftmodel.getFeature(tableIndex);
			featureNameField.setText(feature.getName());
			featureDescriptionArea.setText(feature.getDescription());
			pluginOfFeature.setText(feature.getPlugin().getName());
		}
	}

	private void newFeature() {
		clearFields();
		feature = null;
	}

	private void clearFields() {
		featureNameField.setText("");
		featureDescriptionArea.setText("");
		pluginOfFeature.setText("-");
	}
}
