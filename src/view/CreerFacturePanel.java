package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.TempContratController;
import interfaces.MainInterface;
import javax.swing.SwingConstants;
import java.awt.Color;

public class CreerFacturePanel extends JPanel {
	
	private JTable contrat_table;
	private MainInterface mInterface;
	private CardLayout cl;
	private JLabel warning_lbl;

	public CreerFacturePanel(MainInterface mInterface) {
		this.mInterface = mInterface;
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		
		this.setLayout(null);
		
		JScrollPane contrat_scroll = new JScrollPane();
		contrat_scroll.setBounds(10, 50, 484, 487);
		this.add(contrat_scroll);
		
		contrat_table = new JTable();
		contrat_scroll.setViewportView(contrat_table);
		
		TempContratController.fetchAll(contrat_table);
		
		JLabel choisirContrat_lbl = new JLabel("Veuillez choisir un contrat:");
		choisirContrat_lbl.setBounds(10, 10, 511, 31);
		this.add(choisirContrat_lbl);
		
		JButton actualiser_btn = new JButton("Actualiser");
		actualiser_btn.setBackground(viewSettings.SECONDARY);
		actualiser_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TempContratController.fetchAll(contrat_table);
			}
		});
		actualiser_btn.setBounds(510, 51, 184, 49);
		this.add(actualiser_btn);
		
		JButton creerFacture_btn = new JButton("Creer Facture");
		creerFacture_btn.setBackground(viewSettings.SECONDARY);
		creerFacture_btn.setBounds(510, 110, 184, 49);
		
		creerFacture_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mInterface.getFactureController().CreerFacture();
			}
		});
		this.add(creerFacture_btn);
		
		JButton annuler_btn = new JButton("Annuler");
		annuler_btn.setBackground(viewSettings.SECONDARY);
		annuler_btn.setBounds(510, 488, 184, 49);
		
		annuler_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		this.add(annuler_btn);
		
		warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(510, 192, 184, 124);
		add(warning_lbl);
	}
	
	//GETTERS
	public JTable getContrat_table() {
		return contrat_table;
	}

	public JLabel getWarning_lbl() {
		return warning_lbl;
	}
	
	/**
	 * Methode pour retourner a l'interface de gestion des factures
	 */
	public void goBack() {
		cl.show(mInterface.getMainPanel(), "facture");
	}
	
}
