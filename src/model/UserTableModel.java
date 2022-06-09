package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {
	private String[] nomColonnes = new String[]{
			"Matricule",
			"Nom",
			"Prénom",
			"Téléphone",
			"Adresse",
			"Disponible"
	};
	
	private Vector<String[]> rows = new Vector<String[]>();
	
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return rows.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int index) {
		return nomColonnes[index];
	}
	
	@Override
	public boolean isCellEditable(int row, int column){  
        return false;  
	}
	
	public void loadUsers(ArrayList<User> list) {
		rows.clear();
		for(User u : list) {
			String[] row = {
					u.getMatricule()+"",
					u.getNom(),
					u.getPrenom(),
					u.getTelephone(),
					u.getAdresse(),
					u.isStatut()+""
			};
			
			rows.add(row);
		}
		
		fireTableChanged(null);
	}

}
