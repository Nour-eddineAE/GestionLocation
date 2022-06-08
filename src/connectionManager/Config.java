package connectionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Config {
	public static String DB_NAME;
	public static String USER;
	public static String PASS;
	public static String IP;
	public static String PORT;
	
	static {
		String configFile = "config/config.ini";
		try {
			FileInputStream propsIn = new FileInputStream(configFile);
			Properties prop = new Properties();
			prop.load(propsIn);
			DB_NAME = prop.getProperty("DB_NAME");
			USER = prop.getProperty("USER");
			PASS = prop.getProperty("PASS");
			IP = prop.getProperty("IP");
			PORT = prop.getProperty("PORT");
		} catch (FileNotFoundException e) {
			JOptionPane.showConfirmDialog(null, "Fichier de config introuvable.", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Erreur ouverture de fichier config.", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
}
