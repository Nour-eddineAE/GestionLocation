package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controller.ClientController;
import model.Client;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class CreerNouveauClient extends JFrame {
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreerNouveauClient window = new CreerNouveauClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreerNouveauClient() {
		
	};
	
	public CreerNouveauClient(JTable table) {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(100, 100, 680, 471);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(217, 40, 408, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(217, 85, 408, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(217, 131, 408, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("nom client");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 43, 197, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("prenom client");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 88, 197, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("num Tel client");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 134, 197, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Effacer");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		btnNewButton_1.setBounds(272, 346, 129, 43);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Quitter");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(68, 346, 129, 43);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_3 = new JLabel("Adresse Client");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 188, 187, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(217, 185, 408, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Image Client");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(10, 240, 187, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Permis Scan\u00E9e de Client");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(10, 292, 187, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(336, 240, 289, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(336, 292, 289, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JButton btnNewButton_3 = new JButton("choisir un fichier");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				lblNewLabel_6.setText(file.getAbsolutePath());
			}
		});
		btnNewButton_3.setBounds(217, 236, 109, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("choisir un fichier");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				lblNewLabel_7.setText(file.getAbsolutePath());
			}
		});
		btnNewButton_4.setBounds(217, 288, 109, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton = new JButton("Sauvgarder");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Client client = new Client(textField.getText(), textField_1.getText(), textField_3.getText(), textField_2.getText(), lblNewLabel_6.getText(), lblNewLabel_7.getText());
				boolean b = ClientController.creatClient(client);
				if (b) {
					JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Opération Echouée", "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
			}
		});
		btnNewButton.setBounds(496, 346, 129, 43);
		frame.getContentPane().add(btnNewButton);
		
	}
	
}
