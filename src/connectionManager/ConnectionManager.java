package connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.LogManager;

import javax.swing.JOptionPane;

import log.LogMgr;

public class ConnectionManager {
	private static String USER = Config.USER;
	private static String PASS = Config.PASS;
	private static String URL = "jdbc:mysql://"+Config.IP+":"+Config.PORT+"/"+Config.DB_NAME;
	private static Connection connection;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Pas de connexion avec Base de Donnes", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur de connection.", e);
			System.exit(0);
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static ResultSet execute(String string) {
		try {
			Statement statement = ConnectionManager.getConnection().createStatement();
			ResultSet result = statement.executeQuery(string);
			return result;
		} catch (SQLException e) {
			LogMgr.error("Erreur Execution requete.", e);
			e.printStackTrace();
		}

		return null;
	}

}
