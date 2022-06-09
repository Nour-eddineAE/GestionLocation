package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class VehiculeTableModel extends AbstractTableModel{
	
	private String[] nomColonnes = new String[]{
			"Matricule",
			"Marque ",
			"Type",
			"Code Park",
			"Prix location",
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
	
	public void loadVehicules(ArrayList<Vehicule> list) {
		rows.clear();
		for(Vehicule v : list) {
			String[] row = {
					v.getMatricule(),
					v.getMarque(),
					v.getType(),
					v.getCodePark()+"",
					v.getPrixLocation()+"",
					v.getDisponible()+""
			};
			
			rows.add(row);
		}
		
		fireTableChanged(null);
	}

}
