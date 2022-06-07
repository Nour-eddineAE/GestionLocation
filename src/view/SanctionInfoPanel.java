package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.SanctionController;
import controller.TempContratController;
import model.Contrat;
import model.SanctionTableModel;
import model.TempContratTableModel;

/**
 * @author Abd-AB
 *
 */
public class SanctionInfoPanel extends JPanel {
	
	private JTable contrat_table;
	private JLabel client_nbr_lbl;
	private JLabel client_nom_lbl;
	private JLabel client_prenom_lbl;
	
	private TempContratTableModel table_model = new TempContratTableModel();
	
	private SanctionController cont;


	public SanctionInfoPanel() {
		Init();
	}
	
	private void Init() {
		this.setLayout(null);
		
		JLabel client_nbr_text_lbl = new JLabel("Client N\u00B0:");
		client_nbr_text_lbl.setBounds(227, 55, 153, 30);
		this.add(client_nbr_text_lbl);
		
		client_nbr_lbl = new JLabel("8797545");
		client_nbr_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		client_nbr_lbl.setBounds(390, 55, 153, 30);
		this.add(client_nbr_lbl);
		
		JLabel client_nom_text_lbl = new JLabel("Nom:");
		client_nom_text_lbl.setBounds(227, 95, 153, 30);
		this.add(client_nom_text_lbl);
		
		client_nom_lbl = new JLabel("test_NOM");
		client_nom_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		client_nom_lbl.setBounds(390, 95, 153, 30);
		this.add(client_nom_lbl);
		
		JLabel client_prenom_text_lbl = new JLabel("Prenom:");
		client_prenom_text_lbl.setBounds(227, 135, 153, 30);
		this.add(client_prenom_text_lbl);
		
		client_prenom_lbl = new JLabel("test_prenom");
		client_prenom_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		client_prenom_lbl.setBounds(390, 135, 153, 30);
		this.add(client_prenom_lbl);
		
		JScrollPane contrat_scroll = new JScrollPane();
		contrat_scroll.setBounds(23, 196, 706, 286);
		this.add(contrat_scroll);
		
		contrat_table = new JTable(table_model);
		contrat_table.setRowSelectionAllowed(false);
		contrat_scroll.setViewportView(contrat_table);
		
		JButton retour_btn = new JButton("Retour");
		retour_btn.setBounds(23, 505, 146, 46);
		retour_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.back();
			}
		});
		this.add(retour_btn);
	}
	
	//Getters
	public JTable getContrat_table() {
		return contrat_table;
	}

	public JLabel getClient_nbr_lbl() {
		return client_nbr_lbl;
	}

	public JLabel getClient_nom_lbl() {
		return client_nom_lbl;
	}

	public JLabel getClient_prenom_lbl() {
		return client_prenom_lbl;
	}
	
	public void loadContrats(ArrayList<Contrat> cList) {
		this.table_model.loadContrats(cList);
	}
	
	//setters
	public void setSanctionController(SanctionController cont) {
		this.cont = cont;
	}
}
