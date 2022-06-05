package connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectionManager {
	private static String USER = "root";
	private static String PASS = "root";
	private static String URL = "jdbc:mysql://localhost:8889/location";
	private static Connection connection;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Pas de connexion avec Base de Donnée", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
			e.printStackTrace();
		}

		return null;
	}

}
