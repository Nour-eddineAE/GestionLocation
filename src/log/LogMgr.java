package log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

import javax.swing.JOptionPane;

public class LogMgr {
	public final static Logger logr = Logger.getGlobal();
	
	static {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		
		try {
			FileHandler handler = new FileHandler("/logs/log-"+today, true);
			handler.setFormatter(new XMLFormatter());
			handler.setLevel(Level.SEVERE);
			logr.addHandler(handler);
			
			
		} catch (SecurityException e) {
			JOptionPane.showConfirmDialog(null, "Erreur log", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Erreur log.", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void error(String message, Exception e) {
		logr.log(Level.SEVERE, message, e);
	}
}
