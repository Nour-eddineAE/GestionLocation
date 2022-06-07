package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class SanctionTableModel extends AbstractTableModel {
	
	String[] cols = new String[]{
			"N° Client",
			"Nom Client",
			"Prenom Client",
			"Montant sanction"};
	Vector<String[]> rows = new Vector<String[]>();
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int index) {
		return cols[index];
	}
	
	@Override
	public boolean isCellEditable(int row, int column){  
        return false;  
	}
	
	public void loadSanctions(ArrayList<Sanction> sList) {
		rows.clear();
		for(Sanction s: sList) {
			String[] row = {
					Integer.toString(s.getClient().getCodeClient()),
					s.getClient().getNomClient(),
					s.getClient().getPrenomClient(),
					Integer.toString(s.getMontant())
			};
			rows.add(row);
		}
		fireTableChanged(null);
	}
}
