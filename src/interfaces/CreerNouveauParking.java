package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.ClientController;
import controller.ParkingController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreerNouveauParking {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreerNouveauParking window = new CreerNouveauParking();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreerNouveauParking() {
		
	}
	
	public CreerNouveauParking(JTable table) {
		initialize(table);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JTable table) {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.setBounds(400, 200, 450, 305);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Designation");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(34, 45, 97, 28);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de v\u00E9hicule");
		lblNewLabel_1.setBounds(34, 98, 97, 28);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(141, 45, 283, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 98, 283, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Quitter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(34, 202, 97, 28);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Effacer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
			}
		});
		btnNewButton_1.setBounds(171, 202, 97, 28);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Sauvgarder");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = ParkingController.creatParking(textField.getText(), Integer.parseInt(textField_1.getText()));
				if (b) {
					JOptionPane.showConfirmDialog(null, "Op�ration Effectu�e avce Succ�e", "Succ�e", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Op�ration Echou�e", "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				textField.setText("");
				textField_1.setText("");
				ParkingController.fetchAll(table);
			}
		});
		btnNewButton_2.setBounds(303, 202, 97, 28);
		panel.add(btnNewButton_2);
	}
}