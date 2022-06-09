package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import connectionManager.ConnectionManager;
import model.Vehicule;

public class vehiculeDAO {

	public static ArrayList<Vehicule> fetchAll () {
		String query= "SELECT * "
				+ "FROM  vehicule ";
		
		ResultSet result = ConnectionManager.execute(query);
		
		ArrayList<Vehicule> Vehicule_list = new ArrayList<Vehicule>();
		
		try {
			while (result.next()) {
				Vehicule V = new Vehicule(result.getString("Immatriculation"),
						result.getString("marqueVehicule"),
						result.getString("typeVehicule"),
						result.getString("carburant"),
						result.getInt("kilometrage"),
						result.getDate("dateMiseCirculation"),
						result.getInt("codePark"),
						result.getInt("prixLocation"),
						result.getBoolean("disponible"));
				Vehicule_list.add(V);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "vehicule display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return Vehicule_list;
	}
	
	// METHODE RECHERCHANT LE VEHICULE PAR SON MATRICULE
	public static Vehicule findVehicule(String matricule) {
		String query="SELECT *"
				+" FROM vehicule"
				+" WHERE  Immatriculation like ?;";
		Vehicule V=null;
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, matricule);
			ResultSet result = prepared.executeQuery();
			try {
				while (result.next()) {
					V = new Vehicule(result.getString("Immatriculation"),
							result.getString("marqueVehicule"),
							result.getString("typeVehicule"),
							result.getString("carburant"),
							result.getInt("kilometrage"),
							result.getDate("dateMiseCirculation"),
							result.getInt("codePark"),
							result.getInt("prixLocation"),
							result.getBoolean("disponible"));
					return V;
				}
			
			} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Ereur de recherche", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		
		} catch (SQLException e) {
		JOptionPane.showConfirmDialog(null, e.getMessage(), "erreur de recherche", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		}
		return V;
		
	}
	//VERIFIER SI UN VEHICULE EXISTE EN TESTANT SUR SON CODE MATRICULE? LE RETOUR EST UN BOOLEAN
	public static boolean verifyVehicle(String id) {
		String query="SELECT *"
				+" FROM vehicule"
				+" WHERE Immatriculation LIKE ?";
			
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, id);
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
	//METHODE QUI RETOURNE LES VEHCICULE CORRESPONDANT AU CRITERE DE RECHERCHE 
	public static ArrayList<Vehicule>  findVehiculeAutoCompleting (String Immatriculation) {
		String query="SELECT *"
					+" FROM vehicule"
					+" WHERE  Immatriculation like ?;";
		ArrayList<Vehicule> Vehicule_list = new ArrayList<Vehicule>();
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, Immatriculation+"%");
			ResultSet result = prepared.executeQuery();
			try {
				while (result.next()) {
					Vehicule V = new Vehicule(result.getString("Immatriculation"),
							result.getString("marqueVehicule"),
							result.getString("typeVehicule"),
							result.getString("carburant"),
							result.getInt("kilometrage"),
							result.getDate("dateMiseCirculation"),
							result.getInt("codePark"),
							result.getInt("prixLocation"),
							result.getBoolean("disponible"));
					Vehicule_list.add(V);
				}
				
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "vehicule display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "vehicule display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return Vehicule_list;
	}
	// MEHTODE QUI AJOUTE UN VEHICULE A  LA BASE DE DONNEES 
	public static boolean createVehicule(Vehicule vehicule) {
		String query="INSERT INTO `vehicule` (`Immatriculation`, `marqueVehicule`, `typeVehicule`, `carburant`,"+
					" `kilometrage`, `dateMiseCirculation`, `codePark`, `prixLocation`, `disponible`) "+
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, vehicule.getMatricule());
			prepared.setString(2, vehicule.getMarque());
			prepared.setString(3, vehicule.getType());
			prepared.setString(4, vehicule.getCarburant());
			prepared.setDouble(5, vehicule.getKilometrage());
			prepared.setDate(6, vehicule.getDMC());
			prepared.setInt(7, vehicule.getCodePark());
			prepared.setDouble(8, vehicule.getPrixLocation());
			prepared.setBoolean(9, vehicule.getDisponible());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
// METHODE SUPPRIMANT UN VEHICULE A L'AIDE DE SON MATRICULE
	public static boolean removeVehicule(String id) {
		// TODO Auto-generated method stub
		String query="DELETE FROM `vehicule`"
					+" WHERE (`Immatriculation` = ?);";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Supression vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
//METHODE QUI ENVOI UNE REQUTE UPDATE AFIN DE CHANGER LES ATTRIBUTS D'UN VEHICULE
	public static boolean ChangeVehicle(Vehicule vehicule,String oldId) {
		// TODO Auto-generated method stub
		String query="UPDATE `vehicule`"
				+" SET `Immatriculation`=?, `marqueVehicule`=?, `typeVehicule`=?, `carburant`=?,"
				+ " `kilometrage`=?, `dateMiseCirculation`=?, `codePark`=?, `prixLocation`=?, `disponible`=?"
				+" WHERE (`Immatriculation` = ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, vehicule.getMatricule());
			prepared.setString(2, vehicule.getMarque());
			prepared.setString(3, vehicule.getType());
			prepared.setString(4, vehicule.getCarburant());
			prepared.setDouble(5, vehicule.getKilometrage());
			prepared.setDate(6, vehicule.getDMC());
			prepared.setInt(7, vehicule.getCodePark());
			prepared.setDouble(8, vehicule.getPrixLocation());
			prepared.setBoolean(9, vehicule.getDisponible());
			prepared.setString(10,oldId);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	
}
