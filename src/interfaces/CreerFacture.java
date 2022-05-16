package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;

import controller.FactureController;
import controller.TempContratController;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class CreerFacture {

	private JFrame frmNouvelleFacture;
	private JTable contrat_table;
	private JTable facture_table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreerFacture window = new CreerFacture();
					window.frmNouvelleFacture.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreerFacture() {
		initialize();
	}
	
	public CreerFacture(JTable facture_table) {
		initialize();
		this.facture_table = facture_table;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNouvelleFacture = new JFrame();
		frmNouvelleFacture.setTitle("Nouvelle Facture");
		frmNouvelleFacture.setResizable(false);
		frmNouvelleFacture.setBounds(100, 100, 898, 588);
		frmNouvelleFacture.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNouvelleFacture.setVisible(true);
		frmNouvelleFacture.getContentPane().setLayout(null);
		
		JScrollPane contrat_scroll = new JScrollPane();
		contrat_scroll.setBounds(33, 53, 809, 339);
		frmNouvelleFacture.getContentPane().add(contrat_scroll);
		
		contrat_table = new JTable(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		contrat_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contrat_scroll.setViewportView(contrat_table);
		
		JLabel warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(235, 488, 405, 42);
		frmNouvelleFacture.getContentPane().add(warning_lbl);
		
		TempContratController.fetchAll(contrat_table);
		
		JButton actualiser_btn = new JButton("Actualiser");
		actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TempContratController.fetchAll(contrat_table);
			}
		});
		actualiser_btn.setBounds(340, 408, 192, 42);
		frmNouvelleFacture.getContentPane().add(actualiser_btn);
		
		JButton creerFacture_btn = new JButton("Creer Facture");
		creerFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = contrat_table.getSelectedRow();
				if(index < 0) {
					warning_lbl.setText("Veuillez Choisir un contrat.");
					return;
				}
				try {
					int codeContrat = (int)contrat_table.getValueAt(index, 0);
					FactureController.createFacture(codeContrat);
					FactureController.createPdf(codeContrat);
					warning_lbl.setText("");
					JOptionPane.showConfirmDialog(null, "Operation Effectuee.", "Operation Effectuee.", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					FactureController.fetchAll(facture_table);
					TempContratController.fetchAll(contrat_table);
				} catch (SQLException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), "Operation Echouée.", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		creerFacture_btn.setBounds(650, 488, 192, 42);
		frmNouvelleFacture.getContentPane().add(creerFacture_btn);
		
		JButton annuler_btn = new JButton("Annuler");
		annuler_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmNouvelleFacture.dispose();
			}
		});
		annuler_btn.setBounds(33, 488, 192, 42);
		frmNouvelleFacture.getContentPane().add(annuler_btn);
		
		JLabel choisirContrat_lbl = new JLabel("Choisir un contrat :");
		choisirContrat_lbl.setBounds(33, 10, 369, 33);
		frmNouvelleFacture.getContentPane().add(choisirContrat_lbl);
	}
}
