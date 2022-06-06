package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ClientController;
import model.Client;

public class AfficherClientPanel extends JPanel {

	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
	// rendre globals
	private JTextField idClientTextField;
	private JTextField nomClientTextField;
	private JTextField prenomClientTextField;
	private JTextField adresseClientTextField;
	private JTextField teleClientTextField;
	private CardLayout cl;
	private JTable table_1;

	public AfficherClientPanel(JPanel panel, Client client) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);

		JLabel idClientlbl = new JLabel("Id");
		idClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		idClientlbl.setBounds(41, 33, 67, 32);
		this.add(idClientlbl);

		JLabel nomClientlbl = new JLabel("Nom");
		nomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomClientlbl.setBounds(41, 76, 67, 32);
		this.add(nomClientlbl);

		JLabel prenomClientlbl = new JLabel("Prenom");
		prenomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClientlbl.setBounds(41, 119, 67, 32);
		this.add(prenomClientlbl);

		JLabel teleClientlbl = new JLabel("Num Tel");
		teleClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		teleClientlbl.setBounds(41, 218, 67, 32);
		this.add(teleClientlbl);

		idClientTextField = new JTextField();
		idClientTextField.setBackground(Color.WHITE);
		idClientTextField.setEditable(false);
		idClientTextField.setEnabled(false);
		idClientTextField.setHorizontalAlignment(SwingConstants.LEFT);
		idClientTextField.setBounds(118, 33, 398, 32);
		this.add(idClientTextField);
		idClientTextField.setColumns(10);
		idClientTextField.setText("" + client.getCodeClient());

		nomClientTextField = new JTextField();
		nomClientTextField.setBackground(Color.WHITE);
		nomClientTextField.setEditable(false);
		nomClientTextField.setEnabled(false);
		nomClientTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nomClientTextField.setBounds(118, 76, 398, 32);
		this.add(nomClientTextField);
		nomClientTextField.setColumns(10);
		nomClientTextField.setText(client.getNomClient());

		prenomClientTextField = new JTextField();
		prenomClientTextField.setBackground(Color.WHITE);
		prenomClientTextField.setEditable(false);
		prenomClientTextField.setEnabled(false);
		prenomClientTextField.setHorizontalAlignment(SwingConstants.LEFT);
		prenomClientTextField.setBounds(118, 119, 398, 32);
		this.add(prenomClientTextField);
		prenomClientTextField.setColumns(10);
		prenomClientTextField.setText(client.getPrenomClient());

		adresseClientTextField = new JTextField();
		adresseClientTextField.setBackground(Color.WHITE);
		adresseClientTextField.setEditable(false);
		adresseClientTextField.setEnabled(false);
		adresseClientTextField.setHorizontalAlignment(SwingConstants.LEFT);
		adresseClientTextField.setBounds(118, 162, 398, 32);
		this.add(adresseClientTextField);
		adresseClientTextField.setColumns(10);
		adresseClientTextField.setText(client.getAddresseClient());

		JLabel adresseClientlbl = new JLabel("Addresse");
		adresseClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		adresseClientlbl.setBounds(41, 162, 67, 32);
		this.add(adresseClientlbl);

		teleClientTextField = new JTextField();
		teleClientTextField.setBackground(Color.WHITE);
		teleClientTextField.setEnabled(false);
		teleClientTextField.setEditable(false);
		teleClientTextField.setBounds(118, 212, 398, 38);
		this.add(teleClientTextField);
		teleClientTextField.setColumns(10);
		teleClientTextField.setText(client.getNumTelClient() + "");

		JButton btnNewButton = new JButton("Retour");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		btnNewButton.setBounds(615, 491, 105, 47);
		this.add(btnNewButton);

		try {
			// charger l'image de client pour l'afficher
			File fichier = new File(client.getImage());
			BufferedImage image = ImageIO.read(fichier);
			ImageIcon imageIcon = new ImageIcon(image);
			// afficher l'image du client d'une manière simple
			// je veux le refaire dans une autre manière s'il me reste le temps
			JLabel lblNewLabel_5 = new JLabel(imageIcon, JLabel.CENTER);
			lblNewLabel_5.setBounds(540, 33, 179, 217);
			this.add(lblNewLabel_5);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 308, 649, 158);
		add(scrollPane);

		// table de voiture louée
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);

		// rafraîchir le tableau des voitures louée
		ClientController.findVehicule(table_1, idClientTextField.getText());

	}
}
