package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import controller.ClientController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifierClient extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierClient window = new ModifierClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifierClient() {
		
	};
	
	public ModifierClient(JTable table, int index) {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(400, 200, 450, 305);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(217, 40, 207, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(table.getModel().getValueAt(index, 1).toString());
		
		textField_1 = new JTextField();
		textField_1.setBounds(217, 85, 207, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(table.getModel().getValueAt(index, 2).toString());
		
		textField_2 = new JTextField();
		textField_2.setBounds(217, 131, 207, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(table.getModel().getValueAt(index, 3).toString());
		
		JButton btnNewButton = new JButton("Sauvgarder");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean b = ClientController.modifyClient(ModifierClient.id, textField.getText(), textField_1.getText(), textField_2.getText());
				if (b) {
					JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Opération Echouée", "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				ClientController.fetchAll(table);
			}
		});
		btnNewButton.setBounds(305, 208, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("nom client");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 43, 197, 14);
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
		btnNewButton_1.setBounds(165, 208, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Quitter");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(35, 208, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
	}

	public void setTextField(String string) {
		this.textField.setText(string);
	}

	public void setTextField_1(String string) {
		this.textField_1.setText(string);
	}

	public void setTextField_2(String string) {
		this.textField_2.setText(string);
	}

	public static void setId(String nid) {
		id = nid;
	}
	}

