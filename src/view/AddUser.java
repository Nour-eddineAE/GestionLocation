package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.UserController;
import interfaces.MainInterface;

public class AddUser extends JPanel{
	private MainInterface mainInterface;
	private Color mainColor;
	private Color secondaryColor;
	private CardLayout cl;
	private JTextField newUserNom;
	private JTextField newUserPrenom;
	private JTextField newUserTel;
	private JTextField newUserAdresse;
	private JTable table;
	private JTextField newUserUsername;
	private JTextField newUserPassword;
	private JTextField passwordConfirmed;
	private UserPanel userPanel;
	// constructor
	public AddUser(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.mainInterface = mainInterface;
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		initialize();
	}
	

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// creation d'un Panel 
		setLayout(null);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Ajout d'un nouveau utilisateur");
		lblNewLabel.setBounds(116, 11, 549, 46);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nom");
		lblNewLabel_1_1.setBounds(27, 76, 142, 37);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1);
		
		newUserNom = new JTextField();
		newUserNom.setBounds(206, 76, 399, 37);
		newUserNom.setColumns(10);
		this.add(newUserNom);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Prenom");
		lblNewLabel_1_1_1.setBounds(27, 124, 142, 37);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1_1);
		
		newUserPrenom = new JTextField();
		newUserPrenom.setBounds(206, 124, 399, 37);
		newUserPrenom.setColumns(10);
		this.add(newUserPrenom);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("T\u00E9l\u00E9phone");
		lblNewLabel_1_1_2.setBounds(27, 172, 142, 37);
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1_2);
		
		newUserTel = new JTextField();
		newUserTel.setBounds(205, 173, 400, 37);
		newUserTel.setColumns(10);
		this.add(newUserTel);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Adresse");
		lblNewLabel_1_1_3.setBounds(27, 220, 142, 37);
		lblNewLabel_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1_3);
		
		newUserAdresse = new JTextField();
		newUserAdresse.setBounds(206, 221, 399, 37);
		newUserAdresse.setColumns(10);
		this.add(newUserAdresse);
		
		
		JButton saveNewUser = new JButton("Enregistrer");
		saveNewUser.setBounds(38, 444, 159, 54);
		saveNewUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.saveNewUsers(AddUser.this,newUserNom,newUserPrenom,newUserTel,newUserAdresse,newUserUsername,newUserPassword);
//				AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
			}
		});
		this.add(saveNewUser);
		
		JButton deleteNewUserFields = new JButton("Effacer tout");
		deleteNewUserFields.setBounds(288, 444, 159, 54);
		deleteNewUserFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.emptyAddFields(AddUser.this);
			}
		});
		deleteNewUserFields.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(deleteNewUserFields);
		
		JButton CancelNewUserCreation = new JButton("Annuler");
		CancelNewUserCreation.setBounds(538, 444, 159, 54);
		CancelNewUserCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser.this.mainInterface.showOnMainPanel("user");	//revenir au menu precedent
			}
		});
		CancelNewUserCreation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(CancelNewUserCreation);
		
		JLabel lblNewLabel_1_1_5_1 = new JLabel("Username");
		lblNewLabel_1_1_5_1.setBounds(22, 268, 147, 38);
		lblNewLabel_1_1_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_5_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1_5_1);
		
		newUserUsername = new JTextField();
		newUserUsername.setBounds(206, 269, 399, 37);
		newUserUsername.setColumns(10);
		this.add(newUserUsername);
		
		JLabel lblNewLabel_1_1_5_2 = new JLabel("Mot de passe");
		lblNewLabel_1_1_5_2.setBounds(27, 317, 142, 37);
		lblNewLabel_1_1_5_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_5_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.add(lblNewLabel_1_1_5_2);
		
		newUserPassword = new JTextField();
		newUserPassword.setBounds(206, 317, 399, 37);
		newUserPassword.setColumns(10);
		this.add(newUserPassword);
		
		JLabel lblNewLabel_1_1_5_2_1 = new JLabel("Confirmer mot de passe ");
		lblNewLabel_1_1_5_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_5_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1_5_2_1.setBounds(10, 364, 187, 37);
		add(lblNewLabel_1_1_5_2_1);
		
		passwordConfirmed = new JTextField();
		passwordConfirmed.setColumns(10);
		passwordConfirmed.setBounds(206, 364, 399, 37);
		add(passwordConfirmed);
	}

	// GETTERS
	public JTextField getNewUserNom() {
		return newUserNom;
	}




	public JTextField getNewUserPrenom() {
		return newUserPrenom;
	}




	public JTextField getNewUserTel() {
		return newUserTel;
	}




	public JTextField getNewUserAdresse() {
		return newUserAdresse;
	}




	public JTextField getNewUserUsername() {
		return newUserUsername;
	}




	public JTextField getNewUserPassword() {
		return newUserPassword;
	}




	public JTextField getPasswordConfirmed() {
		return passwordConfirmed;
	}



//SETTERS
	public void setTable(JTable t) {
		this.table=t;//TO PASS THE TABLE AS A PARAMETER SO WE CAN INVOKE  fetchAll()
	}
	
	public void setUserPanel(UserPanel a) {
		this.userPanel=a;
	}
}
