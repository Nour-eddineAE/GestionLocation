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

import model.Client;

public class AfficherClientPanel extends JPanel {
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private CardLayout cl;

	public AfficherClientPanel(JPanel panel, Client client) {
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(41, 33, 67, 32);
		this.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 76, 67, 32);
		this.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(41, 119, 67, 32);
		this.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Num Tel");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(41, 218, 67, 32);
		this.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setEnabled(false);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBounds(118, 33, 398, 32);
		this.add(textField);
		textField.setColumns(10);
		textField.setText(""+client.getCodeClient());
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setEnabled(false);
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setBounds(118, 76, 398, 32);
		this.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(client.getNomClient());
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setEditable(false);
		textField_2.setEnabled(false);
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setBounds(118, 119, 398, 32);
		this.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(client.getPrenomClient());
		
		textField_3 = new JTextField();
		textField_3.setBackground(Color.WHITE);
		textField_3.setEditable(false);
		textField_3.setEnabled(false);
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		textField_3.setBounds(118, 162, 398, 32);
		this.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText(client.getAddresseClient());
		
		JLabel lblNewLabel_4 = new JLabel("Addresse");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(41, 162, 67, 32);
		this.add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		textField_4.setBackground(Color.WHITE);
		textField_4.setEnabled(false);
		textField_4.setEditable(false);
		textField_4.setBounds(118, 212, 398, 38);
		this.add(textField_4);
		textField_4.setColumns(10);
		textField_4.setText(client.getNumTelClient());
		
		JButton btnNewButton = new JButton("Retour");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		btnNewButton.setBounds(585, 392, 105, 47);
		this.add(btnNewButton);
		
		try {
			File fichier = new File(client.getImage());
			BufferedImage image = ImageIO.read(fichier);
			ImageIcon imageIcon = new ImageIcon(image);
			JLabel lblNewLabel_5 = new JLabel(imageIcon, JLabel.CENTER);
			lblNewLabel_5.setBounds(540, 33, 179, 217);
			this.add(lblNewLabel_5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
