package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ContractTableModel extends AbstractTableModel {
		private String[] nomColonnes = new String[]{
				"Référence",
				"Date contrat",
				"Date échéance",
				"Date retour"
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
		
		public void loadContracts(ArrayList<Contrat> list) {
			rows.clear();
			for(Contrat c : list) {
				String[] row = {
						c.getCodeContrat()+"",
						c.getDateContrat()+"",
						c.getDateEcheance()+"",
						c.getDateRetActuel()+""
				};
				
				rows.add(row);
			}
			
			fireTableChanged(null);
		}

}
