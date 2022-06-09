package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.UserController;
import controller.VehiculeController;
import interfaces.MainInterface;
import model.User;
import model.Vehicule;

import java.awt.Rectangle;


public class ChangeExistingUser extends JPanel{
	private MainInterface mInterface;
	private CardLayout cl;
	private JTextField newMatricule;
	private JTextField newNom;
	private JTextField newPrenom;
	private JTextField newTel;
	private JTextField newAdresse;
	private JLabel lblNewLabel_7;
	private JButton saveModification;
	private JButton btnEffacerTout;
	private JButton btnAnnuler;
	private JTable table;
	private JCheckBox StatusChkBox;
	private JLabel userSuspendu;
	private JLabel userActif;
	private JPanel userPanel;
	private static int oldId; //contains id of the row chosen on the main menu
	private boolean statut;
	private JLabel lblNewLabel;
	private JTextField newPassword;
	private JTextField username;
	private JLabel lblNewLabel_5;
	
	
	// constructor
	public ChangeExistingUser(MainInterface mInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.mInterface = mInterface;
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		initialize();
	}
	

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		newMatricule = new JTextField();
		newMatricule.setBounds(140, 102, 183, 42);
		setBounds(0,0, 732, 547);
		setLayout(null);
		this.add(newMatricule);
		newMatricule.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Veuillez remplir les nouveaux donn\u00E9es");
		lblNewLabel_1.setBounds(152, 23, 483, 42);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setBounds(44, 181, 92, 42);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_2);
		
		newNom = new JTextField();
		newNom.setBounds(474, 102, 195, 42);
		newNom.setColumns(10);
		this.add(newNom);
		
		JLabel lblNewLabel_3 = new JLabel("Telephone");
		lblNewLabel_3.setBounds(371, 180, 82, 42);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_3);
		
		newPrenom = new JTextField();
		newPrenom.setBounds(140, 184, 183, 42);
		newPrenom.setColumns(10);
		this.add(newPrenom);
		
		JLabel lblNewLabel_4 = new JLabel("Adresse");
		lblNewLabel_4.setBounds(44, 256, 92, 42);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_4);
		
		newTel = new JTextField();
		newTel.setBounds(474, 183, 195, 42);
		newTel.setColumns(10);
		this.add(newTel);
		
		newAdresse = new JTextField();
		newAdresse.setBounds(140, 259, 183, 42);
		newAdresse.setColumns(10);
		this.add(newAdresse);
		
		lblNewLabel_7 = new JLabel("Nom");
		lblNewLabel_7.setBounds(371, 102, 82, 42);
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_7);
		
		
// check box for user status
		StatusChkBox = new JCheckBox("Statut d'utilisateur");
		StatusChkBox.setBounds(362, 330, 177, 40);
		StatusChkBox.setSelected(true);
		StatusChkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.changeStatus(ChangeExistingUser.this);
			}
		});
		StatusChkBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		StatusChkBox.setHorizontalTextPosition(SwingConstants.LEADING);
		StatusChkBox.setIconTextGap(20);
		StatusChkBox.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(StatusChkBox);
		
		// button to save modifications
		saveModification = new JButton("Enregistrer");
		saveModification.setBounds(540, 471, 164, 42);
		saveModification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!UserController.empty(ChangeExistingUser.this)) {
					try {
						if(!newPassword.getText().equals("")) {
						User u = new User(Integer.parseInt(newMatricule.getText()),newNom.getText(),newPrenom.getText(),newTel.getText(),newAdresse.getText(),
								StatusChkBox.isSelected(),false,username.getText(),newPassword.getText());
						UserController.saveUserUpdate(u,ChangeExistingUser.oldId,newPassword.getText());
						}
						else {
							User u = new User(Integer.parseInt(newMatricule.getText()),newNom.getText(),newPrenom.getText(),newTel.getText(),newAdresse.getText(),
									StatusChkBox.isSelected(),false,username.getText(),"la on s'interesse pas au mot de passe car on va pas l'utiliser");
							UserController.saveUserUpdate(u,ChangeExistingUser.oldId,"null");
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage(), ex+"", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		saveModification.setBackground(viewSettings.SECONDARY);
		this.add(saveModification);
		
		btnEffacerTout = new JButton("Effacer tout");
		btnEffacerTout.setBounds(281, 471, 164, 42);
		/** REMOVES ANY INPUT FROM THE TEXT FIELDS*/
		btnEffacerTout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.emptyUpdateFields(ChangeExistingUser.this);
			}
		});
		btnEffacerTout.setBackground(viewSettings.SECONDARY);
		this.add(btnEffacerTout);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(25, 471, 164, 42);
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mInterface.showOnMainPanel("user");
			}
		});
		btnAnnuler.setBackground(viewSettings.SECONDARY);
		this.add(btnAnnuler);
		
		userSuspendu = new JLabel("Suspendu");
		userSuspendu.setBounds(543, 330, 164, 42);
		userSuspendu.setVisible(false);
		userSuspendu.setHorizontalAlignment(SwingConstants.CENTER);
		add(userSuspendu);
		
		userActif = new JLabel("Actif");
		userActif.setBounds(558, 330, 164, 42);
		userActif.setVisible(false);
		userActif.setHorizontalAlignment(SwingConstants.CENTER);
		add(userActif);
		
		JLabel lblNewLabel_7_1 = new JLabel("Matricule");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_7_1.setBounds(44, 99, 92, 42);
		add(lblNewLabel_7_1);
		
		lblNewLabel = new JLabel("Mot de passe");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(44, 328, 92, 42);
		add(lblNewLabel);
		
		newPassword = new JTextField();
		newPassword.setColumns(10);
		newPassword.setBounds(140, 329, 183, 42);
		add(newPassword);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(474, 258, 195, 42);
		add(username);
		
		lblNewLabel_5 = new JLabel("Username");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5.setBounds(371, 257, 82, 42);
		add(lblNewLabel_5);
		
		
	}

	public JTextField getUsername() {
		return username;
	}




	public JTextField getNewPasword() {
		return newPassword;
	}




	public MainInterface getmInterface() {
		return mInterface;
	}


	public JTextField getNewMatricule() {
		return newMatricule;
	}




	public JTextField getNewNom() {
		return newNom;
	}




	public JTextField getNewPrenom() {
		return newPrenom;
	}




	public JTextField getNewTel() {
		return newTel;
	}




	public JTextField getNewAdresse() {
		return newAdresse;
	}



	public JTable getTable() {
		return table;
	}




	public int getOldId() {
		return oldId;
	}




	public boolean isStatut() {
		return statut;
	}




	public boolean empty() {
		if(this.newNom.getText().isEmpty()||this.newPrenom.getText().isEmpty()||this.newMatricule.getText().isEmpty()||this.newTel.getText().isEmpty()||
				this.newAdresse.getText().isEmpty())
			return true;
		else return false;
	}
	
	//	GETTERS
	public JCheckBox getChkBox() {
		return this.StatusChkBox;
	}
	public JLabel getUserActif() {
		return this.userActif;
	}
	public JLabel getUserSuspendu() {
		return this.userSuspendu;
	}
	// SETTERS
	public void setNewMatricule( int i) {
		this.newMatricule.setText(i+"");
	}
	

	public void setNewPasword(String newPasword) {
		this.newPassword.setText(newPasword);
	}




	public void setUsername(String username) {
		this.username.setText(username);
	}




	public void setNewNom(String newNom) {
		this.newNom.setText(newNom);
	}

	

	public void setNewPrenom(String newPrenom) {
		this.newPrenom.setText(newPrenom);
	}

	

	public void setNewTel(String newTel) {
		this.newTel.setText(newTel);
	}

	

	public void setNewAdresse(String newAdresse) {
		this.newAdresse.setText(newAdresse);
	}

	
	public void setChkBox(boolean b) {
		this.StatusChkBox.setSelected(b);;
	}

	

	public static void setOldId(int h){
		ChangeExistingUser.oldId=h;
	}
	public void setStatus(boolean a) {
		this.statut=a;
	}
	public void setTable(JTable t) {
		this.table=t;//TO PASS THE TABLE AS A PARAMETER SO WE CAN INVOKE  fetchAll()
	}
	public void setUserPanel(JPanel a) {
		this.userPanel=a;// // sets the user panel,we add it to the main panel if we want to get back to the previous menu
	}
}
