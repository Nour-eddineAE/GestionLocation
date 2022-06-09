package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import controller.UserController;
import interfaces.MainInterface;
import model.UserTableModel;

import java.awt.Rectangle;

public class UserPanel extends JPanel{
	//private CardLayout cl;
	private static JTable userTable;
	private static UserTableModel uTable ;
	private static LinkedHashMap<String, JLabel> navItemList;
	
	
	public UserPanel(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
	//	this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		this.setLayout(null);
		
		JTextField chercherUtilisateur = new JTextField();
		chercherUtilisateur.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				UserController.autoCompletion(chercherUtilisateur.getText());
			}
		});
		chercherUtilisateur.setBounds(0, 16, 595, 33);
		this.add(chercherUtilisateur);
		chercherUtilisateur.setColumns(10);
		
		// ZONE D'AFFICHAGE
		JScrollPane UScrollPane = new JScrollPane();
		UScrollPane.setBounds(0, 60, 595, 503);
		this.add(UScrollPane);
		uTable = new UserTableModel();
		userTable=new JTable(uTable);
		UScrollPane.setViewportView(userTable);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/**ADD NEW USER*/
		
		JButton addUser = new JButton("Ajouter ");
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.setWindow(mainInterface);
				UserController.setPanel(UserPanel.this);
				UserController.setTable(userTable);
				UserController.createUser(UserPanel.this);
			}
		});
		addUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addUser.setBounds(605, 222, 117, 50);
		this.add(addUser);
		
		/**REMOVE USER*/
		
		JButton removeUser = new JButton("Suppimer");
		removeUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.setPanel(UserPanel.this);
				UserController.setTable(userTable);
				UserController.removeUser();
			}
		});
		removeUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		removeUser.setBounds(605, 299, 117, 50);
		this.add(removeUser);
		
		/**CHANGE USER DATA*/
		
		JButton changeUser = new JButton("Modifier");
		changeUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.setPanel(UserPanel.this);
				UserController.setTable(userTable);
				UserController.setWindow(mainInterface);
				
				UserController.changeUser();
			}
		});
		changeUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		changeUser.setBounds(605, 136, 117, 50);
		this.add(changeUser);
		this.setLayout(null);
		
		JButton update = new JButton("Actualiser");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserController.fetchAll();
			}
		});
		update.setFont(new Font("Tahoma", Font.PLAIN, 14));
		update.setBounds(605, 378, 117, 50);
		this.add(update);
		
		/**DISPLAY ALL ROWS*/
		navItemList.get("user").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.setPanel(UserPanel.this);
				UserController.fetchAll();
				mainInterface.showOnMainPanel("user");
			}
		});
	}
	//SETTERS 
	public static void setNavList(LinkedHashMap<String, JLabel> a) {
		UserPanel.navItemList=a;
	}
	public static UserTableModel getTableModel() {
		// TODO Auto-generated method stub
		return UserPanel.uTable;
	}
}
