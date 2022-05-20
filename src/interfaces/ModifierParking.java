package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ClientController;
import controller.ParkingController;

public class ModifierParking {

	protected static String id;
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
					ModifierParking window = new ModifierParking();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ModifierParking() {
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public ModifierParking(JTable table, int index) {
		initialize(table, index);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JTable table, int index) {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		textField.setText(table.getModel().getValueAt(index, 1).toString());
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 98, 283, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(table.getModel().getValueAt(index, 2).toString());
		
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
				boolean b = ParkingController.modifyParking(ModifierParking.id, textField.getText(), Integer.parseInt(textField_1.getText()));
				if (b) {
					JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Opération Echouée", "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				ParkingController.fetchAll(table);
			}
		});
		btnNewButton_2.setBounds(303, 202, 97, 28);
		panel.add(btnNewButton_2);
	}
	
	public static void setId(String id) {
		ModifierParking.id = id;
	}

}
