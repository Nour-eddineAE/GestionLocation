package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import model.Client;

public class ClientMainView extends JPanel {
	
	private JTable clienttable;
	private CardLayout cl;
	private JTextField clienttextField;

	public ClientMainView (JPanel panel) {
		initialize(panel);
		ClientController.fetchAll(clienttable);
	}
	
	
		private void initialize(JPanel panel) {
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		
		
		JScrollPane clientscrollPane = new JScrollPane();
		clientscrollPane.setBounds(10, 76, 574, 478);
		this.add(clientscrollPane);

		clienttable = new JTable();
		clienttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] clientcolumns = { "id", "nom", "prenom", "numTel" };
		DefaultTableModel clientmodel = new DefaultTableModel();
		clientmodel.setColumnCount(4);
		clientmodel.setColumnIdentifiers(clientcolumns);
		clienttable.setModel(clientmodel);
		clientscrollPane.setViewportView(clienttable);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(10, 11, 574, 90);
		this.add(lblNewLabel_1);

		JButton clientbtnNewButton = new JButton("Rechercher");
		clientbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = clienttextField.getText();
				if (!string.isBlank()) {
				ClientController.findClientByName(string, clienttable);
				clienttextField.setText("");
				} else {
					lblNewLabel_1.setText("*Tu dois remplir le nom de client tu as en train de chercher");
				}
			}
		});
		clientbtnNewButton.setBounds(594, 11, 128, 36);
		this.add(clientbtnNewButton);

		JButton clientbtnNewButton_1 = new JButton("Nouveau Client");
		clientbtnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NouveauClientPanel nouveauClientPanel = new NouveauClientPanel(panel,clienttable);
				panel.add(nouveauClientPanel, "nouveauClientPanel");
				cl.show(panel, "nouveauClientPanel");
			}
		});
		clientbtnNewButton_1.setBounds(594, 76, 128, 36);
		this.add(clientbtnNewButton_1);

		clienttextField = new JTextField();
		clienttextField.setBounds(10, 11, 574, 36);
		this.add(clienttextField);
		clienttextField.setColumns(10);

		JButton clientbtnNewButton_2 = new JButton("Modifier");
		clientbtnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = (int) clienttable.getModel().getValueAt(index, 0);
					Client client = ClientController.findClientByCode(code);
					ModifierClientPanel modifierClientPanel = new ModifierClientPanel(panel, client, clienttable);
					panel.add(modifierClientPanel, "modifierClientPanel");
					cl.show(panel, "modifierClientPanel");
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois séléctionnée un élément du tableau pour le modifier!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_2.setBounds(594, 170, 128, 36);
		this.add(clientbtnNewButton_2);

		JButton clientbtnNewButton_3 = new JButton("Actualiser");
		clientbtnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_3.setBounds(594, 264, 128, 36);
		this.add(clientbtnNewButton_3);

		JButton clientbtnNewButton_4 = new JButton("Supprimer");
		clientbtnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
						ClientController.fetchAll(clienttable);
					}
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois séléctionnée un élément du tableau pour le supprimer!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_4.setBounds(594, 217, 128, 36);
		this.add(clientbtnNewButton_4);

		JButton clientbtnNewButton_5 = new JButton("Afficher");
		clientbtnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = Integer.parseInt(clienttable.getModel().getValueAt(index, 0).toString());
					Client client = ClientController.findClientByCode(code);
					AfficherClientPanel afficherClientPanel = new AfficherClientPanel(panel, client);
					panel.add(afficherClientPanel, "afficherClientPanel");
					cl.show(panel, "afficherClientPanel");
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois séléctionnée un élément du tableau pour l'afficher!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_5.setBounds(594, 123, 128, 36);
		this.add(clientbtnNewButton_5);
	}

}
