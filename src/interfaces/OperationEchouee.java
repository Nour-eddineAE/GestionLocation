package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperationEchouee extends JFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationEchouee window = new OperationEchouee();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OperationEchouee() {
		frame = new JFrame();
		this.frame.setVisible(true);
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 384, 111);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Op\u00E9ration Echouer Avec Succ\u00E9e");
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD, 21));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 364, 43);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(142, 65, 115, 35);
		panel.add(btnNewButton);
	}

}
