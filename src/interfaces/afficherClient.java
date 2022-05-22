package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import connectionManager.ConnectionManager;
import controller.ClientController;
import model.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Canvas;

public class afficherClient extends JFrame {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JFrame frame;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					afficherClient window = new afficherClient();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public afficherClient() {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(300, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 784, 461);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(41, 33, 67, 32);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 76, 67, 32);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(41, 119, 67, 32);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Num Tel");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(41, 218, 67, 32);
		panel.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setEnabled(false);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBounds(118, 33, 398, 32);
		panel.add(textField);
		textField.setColumns(10);
		//textField.setText(""+client.getCodeClient());
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setEnabled(false);
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setBounds(118, 76, 398, 32);
		panel.add(textField_1);
		textField_1.setColumns(10);
		//textField_1.setText(client.getNomClient());
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setEditable(false);
		textField_2.setEnabled(false);
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setBounds(118, 119, 398, 32);
		panel.add(textField_2);
		textField_2.setColumns(10);
		//textField_2.setText(client.getPrenomClient());
		
		textField_3 = new JTextField();
		textField_3.setBackground(Color.WHITE);
		textField_3.setEditable(false);
		textField_3.setEnabled(false);
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		textField_3.setBounds(118, 162, 398, 32);
		panel.add(textField_3);
		textField_3.setColumns(10);
		//textField_3.setText(client.getAddresseClient());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(41, 280, 322, 151);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 280, 357, 151);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_4 = new JLabel("Addresse");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(41, 162, 67, 32);
		panel.add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		textField_4.setBackground(Color.WHITE);
		textField_4.setEnabled(false);
		textField_4.setEditable(false);
		textField_4.setBounds(118, 212, 398, 38);
		panel.add(textField_4);
		textField_4.setColumns(10);
		//textField.setText(""+client.getCodeClient());
		
		try {
			URL url = new URL("https://unsplash.com/photos/QXevDflbl8A");
			BufferedImage image = ImageIO.read(url);
			ImageIcon imageIcon = new ImageIcon(image);
			JLabel lblNewLabel_5 = new JLabel(imageIcon, JLabel.CENTER);
			lblNewLabel_5.setBounds(565, 33, 179, 217);
			panel.add(lblNewLabel_5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};
	
	public afficherClient(Client client) {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(300, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 784, 461);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(41, 33, 67, 32);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 76, 67, 32);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(41, 119, 67, 32);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Num Tel");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(41, 218, 67, 32);
		panel.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setEnabled(false);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBounds(118, 33, 398, 32);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText(""+client.getCodeClient());
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setEnabled(false);
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setBounds(118, 76, 398, 32);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(client.getNomClient());
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setEditable(false);
		textField_2.setEnabled(false);
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setBounds(118, 119, 398, 32);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(client.getPrenomClient());
		
		textField_3 = new JTextField();
		textField_3.setBackground(Color.WHITE);
		textField_3.setEditable(false);
		textField_3.setEnabled(false);
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		textField_3.setBounds(118, 162, 398, 32);
		panel.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText(client.getAddresseClient());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(41, 280, 703, 151);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 280, 703, 151);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_4 = new JLabel("Addresse");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(41, 162, 67, 32);
		panel.add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		textField_4.setBackground(Color.WHITE);
		textField_4.setEnabled(false);
		textField_4.setEditable(false);
		textField_4.setBounds(118, 212, 398, 38);
		panel.add(textField_4);
		textField_4.setColumns(10);
		textField_4.setText(client.getNumTelClient());
		
		try {
			File fichier = new File(client.getImage());
			BufferedImage image = ImageIO.read(fichier);
			ImageIcon imageIcon = new ImageIcon(image);
			JLabel lblNewLabel_5 = new JLabel(imageIcon, JLabel.CENTER);
			lblNewLabel_5.setBounds(565, 33, 179, 217);
			panel.add(lblNewLabel_5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
