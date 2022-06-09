package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ClientController;
import interfaces.MainInterface;
import model.Client;

public class NouveauClientPanel extends JPanel {

	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
	// rendre globals
	private static JTextField nomTextField;
	private static JTextField prenomTextField;
	private static JTextField teleTextField;
	private JTextField adresseTextField;
	private CardLayout cl;

	public NouveauClientPanel(JPanel panel, JTable table) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);

		nomTextField = new JTextField();
		nomTextField.setBounds(217, 40, 408, 20);
		this.add(nomTextField);
		nomTextField.setColumns(10);

		prenomTextField = new JTextField();
		prenomTextField.setBounds(217, 85, 408, 20);
		this.add(prenomTextField);
		prenomTextField.setColumns(10);

		teleTextField = new JTextField();
		teleTextField.setBounds(217, 131, 408, 20);
		this.add(teleTextField);
		teleTextField.setColumns(10);

		JLabel nomClientlbl = new JLabel("nom client");
		nomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomClientlbl.setBounds(0, 43, 197, 14);
		this.add(nomClientlbl);

		JLabel prenomClientlbl = new JLabel("prenom client");
		prenomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClientlbl.setBounds(10, 88, 197, 14);
		this.add(prenomClientlbl);

		JLabel teleClientlbl = new JLabel("num Tel client");
		teleClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		teleClientlbl.setBounds(10, 134, 197, 14);
		this.add(teleClientlbl);

		JLabel imagePath = new JLabel("image de taille 179x217");
		imagePath.setBounds(336, 240, 289, 14);
		this.add(imagePath);

		JLabel permisPath = new JLabel("");
		permisPath.setBounds(336, 292, 289, 14);
		this.add(permisPath);

		JButton buttonEffacer = new JButton("Effacer");
		buttonEffacer.setBackground(viewSettings.SECONDARY);
		buttonEffacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// effacer tous les informations pour les redéfinir
				nomTextField.setText("");
				prenomTextField.setText("");
				teleTextField.setText("");
				imagePath.setText("image de taille 179x217");
				permisPath.setText("");
			}
		});
		buttonEffacer.setBounds(272, 346, 129, 43);
		this.add(buttonEffacer);

		JButton buttonRetour = new JButton("Retour");
		buttonRetour.setBackground(viewSettings.SECONDARY);
		buttonRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		buttonRetour.setBounds(68, 346, 129, 43);
		this.add(buttonRetour);

		JLabel adresseClientlbl = new JLabel("Adresse Client");
		adresseClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		adresseClientlbl.setBounds(10, 188, 187, 14);
		this.add(adresseClientlbl);

		adresseTextField = new JTextField();
		adresseTextField.setBounds(217, 185, 408, 20);
		this.add(adresseTextField);
		adresseTextField.setColumns(10);

		JLabel imageClientlbl = new JLabel("Image Client");
		imageClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		imageClientlbl.setBounds(10, 240, 187, 14);
		this.add(imageClientlbl);

		JLabel permisClientlbl = new JLabel("Permis Scan\u00E9e de Client");
		permisClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		permisClientlbl.setBounds(10, 292, 187, 14);
		this.add(permisClientlbl);

		JButton imageButton = new JButton("choisir un fichier");
		imageButton.setBackground(viewSettings.SECONDARY);
		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir une fenetre pour séléctionné l'image
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				// si l'utisitaeur ne séléctionne aucune image
				if (file != null) {
					imagePath.setText(file.getAbsolutePath());
				} else {
					JOptionPane.showConfirmDialog(null, "tu dois séléctionnée une image", "Echoue",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		imageButton.setBounds(217, 236, 109, 23);
		this.add(imageButton);

		JButton permisButton = new JButton("choisir un fichier");
		permisButton.setBackground(viewSettings.SECONDARY);
		permisButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				if (file != null) {
					permisPath.setText(file.getAbsolutePath());
				} else {
					JOptionPane.showConfirmDialog(null, "tu dois séléctionnée une image", "Echoue",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		permisButton.setBounds(217, 288, 109, 23);
		this.add(permisButton);

		JButton buttonSauvgarder = new JButton("Sauvgarder");
		buttonSauvgarder.setBackground(viewSettings.SECONDARY);
		buttonSauvgarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tester si l'utilisateur remplir tous les champs
				if (!nomTextField.getText().isBlank() && !prenomTextField.getText().isBlank()
						&& !adresseTextField.getText().isBlank() && !teleTextField.getText().isBlank()
						&& !imagePath.getText().isBlank() && !permisPath.getText().isBlank()) {
					// tester si l'utilisateur ne fait des fautes lors de saisie
					if (nomTextField.getText().matches("[a-zA-Z]*") && prenomTextField.getText().matches("[a-zA-Z]*")
							&& teleTextField.getText().matches("[0-9]*")) {
						Client client = new Client(nomTextField.getText(), prenomTextField.getText(),
								adresseTextField.getText(), Long.parseLong(teleTextField.getText()),
								imagePath.getText(), permisPath.getText());
						boolean b = ClientController.creatClient(client);
						if (b) {
							JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showConfirmDialog(null, "Opération Echouée", "Echoue",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
						nomTextField.setText("");
						prenomTextField.setText("");
						teleTextField.setText("");
						adresseTextField.setText("");
						imagePath.setText("image de taille 179x217");
						permisPath.setText("");
					} else {
						JOptionPane.showConfirmDialog(null,
								"nom : chaine de caractere \n prenom : chaine de caractere \n num tele : nombre",
								"Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois remplir tous les champs", "Echoue",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				// rafraîchir le tableau
				ClientController.fetchAll(table);
			}
		});
		buttonSauvgarder.setBounds(496, 346, 129, 43);
		this.add(buttonSauvgarder);
	}
}
