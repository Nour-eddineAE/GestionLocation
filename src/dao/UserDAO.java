package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import connectionManager.ConnectionManager;
import controller.UserController;
import model.User;

public class UserDAO {
		
	// AFFICHER TOUS LES ENREGISTREMENTS 
		public static ArrayList<User> fetchAll () {
			String query ="SELECT *"
						+" FROM utilisateur"
						+" WHERE username NOT LIKE 'root'";
			ResultSet result = ConnectionManager.execute(query);
			ArrayList<User> User_list = new ArrayList<User>();
			try {
				while (result.next()) {
					User u=new User(result.getInt("matricule"), result.getString("nomUtilisateur"), result.getString("prenomUtilisateur"),
							result.getString("TelUtilisateur"), result.getString("adresseUtilisateur"), result.getBoolean("IsActive"),
							result.getBoolean("IsAdmin"),result.getString("username"),result.getString("password"));
					User_list.add(u);
				}
				
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "User display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
			return User_list;
		}
	//METHODE AUTOCOMPLETING
		public static ArrayList<User> findUserAutoCompleting (String nom) {
			String query="SELECT *"
						+" FROM utilisateur"
						+" WHERE username NOT LIKE 'root'"
						+" and (nomUtilisateur like ? OR prenomUtilisateur like ?);";
			ArrayList<User> User_list = new ArrayList<User>();
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setString(1, nom+"%");
				prepared.setString(2, nom+"%");
				ResultSet result = prepared.executeQuery();
				try {
					while (result.next()) {
						User  u=new User(result.getInt("matricule"), result.getString("nomUtilisateur"), result.getString("prenomUtilisateur"),
								result.getString("TelUtilisateur"), result.getString("adresseUtilisateur"), result.getBoolean("IsActive"),
								result.getBoolean("IsAdmin"),result.getString("username"),result.getString("password"));
						User_list.add(u);
					}
					
				} catch (SQLException e) {
					JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur d'affichage", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur d'affichage", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			return User_list;
			
		}
	//VERIFIE SI L'UTILISATEUR EXISTE DEJA [ON TESTE SUR SA MATRICULE]
		public static User findUser (int id) {
			String query="SELECT *"
						+" FROM utilisateur"
						+" WHERE matricule LIKE ?";
			
			User user=null;
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setInt(1, id);
				ResultSet result = prepared.executeQuery();
				while (result.next()) {
					user=new User(result.getInt("matricule"), result.getString("nomUtilisateur"), result.getString("prenomUtilisateur"),
							result.getString("TelUtilisateur"), result.getString("adresseUtilisateur"), result.getBoolean("IsActive"),
							result.getBoolean("IsAdmin"),result.getString("username"),result.getString("password"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return user;
		}
	//VERIFIER SI UN UTILISATEUR EXISTE EN TESTANT SUR SON CODE MATRICULE? LE RETOUR EST UN BOOLEAN
		public static boolean verifyUser(int id) {
			String query="SELECT *"
					+" FROM utilisateur"
					+" WHERE matricule LIKE ?";
		
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setInt(1, id);
				ResultSet result = prepared.executeQuery();
			if(result.next())
				return true;
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		}
	//VERIFIE SI L'UTILISATEUR EXISTE DEJA [ON TESTE SUR SON USERNAME]
		public static boolean findUser (String username) {
			String query="SELECT *"
						+" FROM utilisateur"
						+" WHERE username LIKE ?";
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setString(1, username);
				ResultSet result = prepared.executeQuery();
				if( result.next()) return true;
				else return false;//RETURNS FALSE IF THE RESULT IS EMPTY, TRUE OTHERWISE			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
	//ON TESTE LE CHAMP IsAdmin DE L'UTILISATEUR POUR AFFICHER/(OU PAS) LE BOUTON UTILISATEUR DANS LA BARRE DE NAVIGATION
	//LA CLE DE RECHERCHE ETANT username
		public static boolean checkAdmin(String username) {
			String query="SELECT isAdmin"
						+" FROM utilisateur"
						+" WHERE username LIKE ?";
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setString(1, username);
				ResultSet result = prepared.executeQuery();
				if( result.next()) {
					return result.getBoolean("isAdmin");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}		
		
		
	//METHODE QUI AJOUTE UN UTILISATEUR A LA BASE DE DONNEES
		public static boolean createUser(String nom, String prenom, String numTel,String adresse,String username,String password) {
			String query="INSERT INTO `utilisateur` ( `nomUtilisateur`, `prenomUtilisateur`, `TelUtilisateur`, `adresseUtilisateur`, `IsActive`, `IsAdmin`,`username`,`password`)"
						+" VALUES ( ?, ?, ?, ?, true, false,?,?);";
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				
				prepared.setString(1, nom);
				prepared.setString(2, prenom);
				prepared.setString(3, numTel);
				prepared.setString(4, adresse);
				prepared.setString(5, username);
				prepared.setString(6, password);
				prepared.execute();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		
	//METHIDE QUI MET A JOUR LES ATTRIBUTS D'UN UTILISATEUR 
	//LA MTHD COMPORTE 2 VERSION (SI L'ADMINISTRATEUR TENTE DE CHANGER LE MOT DE PASSE OU PAS)
		public static boolean modifyUser (User u,int oldId, String newPassword) {
			if(newPassword.equals("null")) {
				String query1="UPDATE `utilisateur`"
						+" SET `matricule` = ?, `nomUtilisateur` = ?, `prenomUtilisateur` = ?, `TelUtilisateur` = ?, `adresseUtilisateur` = ?, `IsActive` = ?, `username` = ? "
						+" WHERE (`matricule` = ?);";
				try {
					PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query1);
					prepared.setInt(1, u.getMatricule());
					prepared.setString(2,u.getNom());
					prepared.setString(3,u.getPrenom());
					prepared.setString(4, u.getTelephone());
					prepared.setString(5, u.getAdresse());
					prepared.setBoolean(6,u.isStatut() );
					prepared.setString(7, u.getUsername());
					prepared.setInt(8, oldId);
					/** execute query*/
					prepared.execute();
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				String query="UPDATE `utilisateur`"
						+" SET `matricule` = ?, `nomUtilisateur` = ?, `prenomUtilisateur` = ?, `TelUtilisateur` = ?, `adresseUtilisateur` = ?, `IsActive` = ? , `username` = ?,`password` = ?"
						+" WHERE (`matricule` = ?);";
				try {
					PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
					prepared.setInt(1, u.getMatricule());
					prepared.setString(2,u.getNom());
					prepared.setString(3,u.getPrenom());
					prepared.setString(4, u.getTelephone());
					prepared.setString(5, u.getAdresse());
					prepared.setBoolean(6,u.isStatut() );
					prepared.setString(7, u.getUsername());
					prepared.setString(8, UserController.cryptWithMD5(u.getPassword()));
					prepared.setInt(9, oldId);
					/** execute query*/
					prepared.execute();
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return false;
		}
	//METHODE SUPPRIMANT UN UTILIDATEUR CONNU PAR SON Id
		public static boolean removeUser(int id) {
			String query="DELETE FROM `utilisateur`"
						+" WHERE (`matricule` = ?);";
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setInt(1, id);
				prepared.execute();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	// METHODE PERMETTANT DE SUSPENDRE UN UTILISATEUR
		public static boolean suspendUser(int id, boolean NewStateValue) {//RETOURNE TRUE SI ON A BIEN CHANGER L'ATTRIBUT STATUT DE 'UTILISATEUR
			String query="UPDATE `utilisateur`"
						+" SET `IsActive` = ?"
						+" WHERE (`matricule` = ?);";
			try {
				PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
				prepared.setBoolean(1, NewStateValue);
				prepared.setInt	(2,id);
				prepared.execute();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	//METHODE VERIFIANT SI LE COMPTE UTILISATEUR EST DEJA DANS LA BASE DE DONNEES 
		public static boolean verifyLogin(String username,String password) {
			String query="SELECT *"
						+" FROM utilisateur"
						+" WHERE username LIKE ?"
						+" AND password LIKE ?";
			try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, username);
				prepared.setString(2, UserController.cryptWithMD5(password));
				ResultSet result = prepared.executeQuery();
				if (result.next()) return true;
				else return false;
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			
			return false;
		}
		
	}


