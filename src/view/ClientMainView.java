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
import java.awt.Color;

public class ClientMainView extends JPanel {

	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
	// rendre globals
	private JTable clienttable;
	private CardLayout cl;
	private JTextField searchclienttextField;

	public ClientMainView(JPanel panel) {
		initialize(panel);
		ClientController.fetchAll(clienttable);
	}

	private void initialize(JPanel panel) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);

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

		JLabel warninglbl = new JLabel("");
		warninglbl.setForeground(new Color(255, 0, 0));
		warninglbl.setHorizontalAlignment(SwingConstants.LEFT);
		warninglbl.setBounds(10, 51, 574, 22);
		this.add(warninglbl);

		JButton buttonRecherche = new JButton("Rechercher");
		buttonRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = searchclienttextField.getText();
				// si le champ de recherche est vide une message doit affiché
				if (!string.isBlank()) {
					ClientController.findClientByName(string, clienttable);
					searchclienttextField.setText("");
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous deuvez remplir le nom de client tu as en train de chercher");
				}
			}
		});
		buttonRecherche.setBounds(594, 11, 128, 36);
		this.add(buttonRecherche);

		JButton nouveauClientButton = new JButton("Nouveau Client");
		nouveauClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir la fenetre créer nouveau client
				NouveauClientPanel nouveauClientPanel = new NouveauClientPanel(panel, clienttable);
				panel.add(nouveauClientPanel, "nouveauClientPanel");
				cl.show(panel, "nouveauClientPanel");
				warninglbl.setText("");
			}
		});
		nouveauClientButton.setBounds(594, 76, 128, 36);
		this.add(nouveauClientButton);

		searchclienttextField = new JTextField();
		searchclienttextField.setBounds(10, 11, 574, 36);
		this.add(searchclienttextField);
		searchclienttextField.setColumns(10);

		JButton modifierClientButton = new JButton("Modifier");
		modifierClientButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// recuperer la ligne du tableau séléctionnée
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = (int) clienttable.getModel().getValueAt(index, 0);
					Client client = ClientController.findClientByCode(code);
					// ouvrir la fenetre modifier client
					ModifierClientPanel modifierClientPanel = new ModifierClientPanel(panel, client, clienttable);
					panel.add(modifierClientPanel, "modifierClientPanel");
					cl.show(panel, "modifierClientPanel");
				} else {
					// si l'utilisateur ne séléctionne aucun ligne de tableau
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour le modifier!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				warninglbl.setText("");
			}
		});
		modifierClientButton.setBounds(594, 170, 128, 36);
		this.add(modifierClientButton);

		JButton actualiserClientButton = new JButton("Actualiser");
		actualiserClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// rafraîchir le tableau des clients
				ClientController.fetchAll(clienttable);
				warninglbl.setText("");
			}
		});
		actualiserClientButton.setBounds(594, 264, 128, 36);
		this.add(actualiserClientButton);

		JButton supprimerClientButton = new JButton("Supprimer");
		supprimerClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				// tester si le client a séléctionné une ligne
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
						ClientController.fetchAll(clienttable);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour le supprimer!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				warninglbl.setText("");
			}
		});
		supprimerClientButton.setBounds(594, 217, 128, 36);
		this.add(supprimerClientButton);

		JButton afficherClientButton = new JButton("Afficher");
		afficherClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = Integer.parseInt(clienttable.getModel().getValueAt(index, 0).toString());
					Client client = ClientController.findClientByCode(code);
					AfficherClientPanel afficherClientPanel = new AfficherClientPanel(panel, client);
					panel.add(afficherClientPanel, "afficherClientPanel");
					cl.show(panel, "afficherClientPanel");
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour l'afficher!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				warninglbl.setText("");
			}
		});
		afficherClientButton.setBounds(594, 123, 128, 36);
		this.add(afficherClientButton);
	}

}
