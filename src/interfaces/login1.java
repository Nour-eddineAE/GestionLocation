package interfaces;

import javax.swing.*;
import javax.swing.border.*;
import dao.UserDAO;
import java.awt.*;
import java.awt.event.*;

public class login1 extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	//THIS IS MY FIRST JAVA INTERFACE MADE WITH SWING, IT'S USED JUST TO  TEST THE APP

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login1 frame = new login1();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);// display window in the middle of the screen
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login1() {
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 66);
		panel.setBorder(new LineBorder(new Color(119, 136, 153), 1, true));
		panel.setBackground(new Color(192, 192, 192));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN PANEL");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 24));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(127, 255, 212));
		lblNewLabel.setForeground(new Color(105, 105, 105));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(118, 11, 185, 34);
		panel.add(lblNewLabel);
		JLabel ClsLbl = new JLabel("Close");
		ClsLbl.setVisible(false);
		ClsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		ClsLbl.setOpaque(true);
		ClsLbl.setBounds(398, 31, 46, 14);
		panel.add(ClsLbl);
		
		//create Close button
		JLabel close = new JLabel("\u25CF");
		close.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				close.setForeground(new Color(255, 150, 50));	
				ClsLbl.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {
				close.setForeground(new Color(255, 0, 0));
				ClsLbl.setVisible(false);
			}
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		close.setForeground(new Color(255, 0, 0));
		close.setSize(new Dimension(20, 20));
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setFont(new Font("Dialog", Font.BOLD, 22));
		close.setPreferredSize(new Dimension(20, 20));
		close.setBorder(null);
		close.setBounds(420, 5, 20, 20);
		panel.add(close);
		
		//create Reduce label 
		JLabel LblMin = new JLabel("Reduce");
		LblMin.setHorizontalAlignment(SwingConstants.CENTER);
		LblMin.setOpaque(true);
		LblMin.setVisible(false);
		LblMin.setBounds(381, 31, 46, 14);
		panel.add(LblMin);
		// create Reduce button
		JLabel minimise = new JLabel("\u25CF");
		minimise.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				minimise.setForeground(new Color(200, 255, 130));	
				LblMin.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {
				minimise.setForeground(new Color(0, 255, 127));
				LblMin.setVisible(false);
			}
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);//reduces the window to an icon
				//NOTE: i can add maximize button, then we use setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});
		minimise.setSize(new Dimension(20, 20));
		minimise.setForeground(new Color(0, 255, 127));
		minimise.setHorizontalTextPosition(SwingConstants.CENTER);
		minimise.setHorizontalAlignment(SwingConstants.CENTER);
		minimise.setPreferredSize(new Dimension(20, 20));
		minimise.setBorder(null);
		minimise.setFont(new Font("Dialog", Font.BOLD, 22));
		minimise.setBounds(398, 5, 20, 20);
		panel.add(minimise);

		JLabel lblNewLabel_4 = new JLabel("Username:");
		lblNewLabel_4.setBounds(10, 94, 76, 30);
		lblNewLabel_4.setFont(new Font("Calibri", Font.BOLD, 16));
		lblNewLabel_4.setForeground(new Color(0, 0, 205));
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Password:");
		lblNewLabel_5.setBounds(10, 149, 76, 30);
		lblNewLabel_5.setFont(new Font("Calibri", Font.BOLD, 16));
		lblNewLabel_5.setForeground(new Color(0, 0, 205));
		contentPane.add(lblNewLabel_5);
		// username text field
		username = new JTextField();
		username.setBounds(96, 94, 238, 27);
		username.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				username.setBorder(new LineBorder(new Color(0, 255, 0), 1, true));			
			}
			public void mouseExited(MouseEvent e) {
				username.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
			}
		});
		username.setBorder(new LineBorder(new Color(128, 128, 128)));
		contentPane.add(username);
		username.setColumns(10);
		
		// password text field
		password = new JPasswordField();
		password.setBounds(96, 149, 238, 27);
		password.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				password.setBorder(new LineBorder(new Color(0, 255, 0), 1, true));			
			}
			public void mouseExited(MouseEvent e) {
				password.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
			}
		});
		password.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		password.setColumns(10);
		contentPane.add(password);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if fields are empty ( you can add type of conditions on data types)
				if(emptyField()) {
					JOptionPane.showMessageDialog(login,"username or password are empty", "missing information", JOptionPane.ERROR_MESSAGE);
				}	
				else {
					if (UserDAO.verifyLogin(username.getText(),password.getText())){
						boolean isAdmin1 = UserDAO.checkAdmin(username.getText());
						MainInterface window = new MainInterface(isAdmin1);
						window.frame.setVisible(true);
						window.frame.setLocationRelativeTo(null);
						
					}
					else {
						JOptionPane.showMessageDialog(login,"username or password is not correct", "NON EXISTING ACCOUNT", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// Verify if text fields are empty
			private boolean emptyField() {
			if(username.getText().equals("")||password.getText().equals(""))
				return true;
			else return false;
			}
		});
		login.setBorderPainted(false);
		login.setBounds(140, 187, 149, 30);
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				login.setBackground(new Color(45,87,255));
			}
			public void mouseExited(MouseEvent e) {
				login.setBackground(new Color(30, 144, 255));
			}
		});
		
		login.setFont(new Font("Calibri", Font.BOLD, 14));
		login.setBackground(new Color(30, 144, 255));
		login.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(login);
		
		JLabel signIn = new JLabel(">>Click here to create a new account");
		signIn.setBounds(96, 236, 238, 14);
		signIn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				signIn.setForeground(new Color(0,0,255));
			}
			public void mouseExited(MouseEvent e) {
				signIn.setForeground(new Color(138, 43, 226));
			}
			
		});
		signIn.setForeground(new Color(138, 43, 226));
		signIn.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(signIn);
	}
}
