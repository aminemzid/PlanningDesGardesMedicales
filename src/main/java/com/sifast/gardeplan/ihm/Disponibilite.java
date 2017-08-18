package com.sifast.gardeplan.ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.sifast.gardeplan.controller.Service;
import com.sifast.gardeplan.model.PrefEnum;
import com.toedter.calendar.JDateChooser;

public class Disponibilite extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ButtonGroup buttonGroup = new ButtonGroup();
    String nom_fichier_image = "image1.jpg";

	// private HashMap<String, PrefEnum> preference = new
	// HashMap<String,PrefEnum>();

   public String verif_date(int d,int m,int y) {
	   String date ;
    	if (m<10)
		{ if (d<10)
		{ date=""+y+"0"+m+"0"+d;}
		else { date=""+y+"0"+m+d;}
		}	
		else { if (d<10)
		{ date=""+y+m+"0"+d;}
		else { 	date=""+y+m+d;}
		}
    	return date;
    }
	// constructeur

	public Disponibilite() {

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Disponibilité");
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel()
		 {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
 
                ImageIcon m = new ImageIcon(nom_fichier_image);
                Image monImage = m.getImage();
                g.drawImage(monImage, 0, 0,this); }
 
            };
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Nom docteur + Nombre de nuit déja fait durant ce mois ( doit etre inférieur ou égal à 4 par mois)

		JLabel lblNomDuDocteur = new JLabel(
				MembresDeGarde.table.getValueAt(MembresDeGarde.table.getSelectedRow(), 0).toString() +  " a fait "+
				  		MembresDeGarde.table.getValueAt(MembresDeGarde.table.getSelectedRow(), 1).toString() + " nuits durant ce mois");
		lblNomDuDocteur.setBounds(220, 33, 300, 30);
		contentPane.add(lblNomDuDocteur);
		
		
		int nNuit= Integer.parseInt(MembresDeGarde.table.getValueAt(MembresDeGarde.table.getSelectedRow(), 1).toString());
		if (nNuit>=4) {
		JOptionPane.showMessageDialog(null, "Ce membre a fait 4 nuits ou plus de garde durant ce mois \n \n         ", "choisir un autre membre",
				JOptionPane.ERROR_MESSAGE);	
		}
		int nbdim=Integer.parseInt(MembresDeGarde.table.getValueAt(MembresDeGarde.table.getSelectedRow(), 2).toString());
		if (nbdim >0) {
			JLabel lblNbredimanche = new JLabel( " dont "+
			  		MembresDeGarde.table.getValueAt(MembresDeGarde.table.getSelectedRow(), 2).toString() + " dimanche(s)");
			lblNbredimanche.setBounds(320, 60, 250, 14);
					contentPane.add(lblNbredimanche);
			
		}
		if (nbdim >nNuit) {JOptionPane.showMessageDialog(null, "Le nombre de dimanche doit etre inferieure au nombre de nuit \n \n         ", "Attention",
				JOptionPane.ERROR_MESSAGE);	
		}
		// choisir la date

		JDateChooser dateDispo = new JDateChooser();
		dateDispo.getCalendarButton().setBackground(SystemColor.activeCaption);
		dateDispo.setBounds(221, 89, 105, 20);
		contentPane.add(dateDispo);

		JRadioButton rbDispoBut = new JRadioButton("Disponible");
		rbDispoBut.setBackground(new Color(0, 0, 0));
		rbDispoBut.setOpaque(false);

		buttonGroup.add(rbDispoBut);
		rbDispoBut.setBounds(159, 149, 87, 23);
		contentPane.add(rbDispoBut);
		JRadioButton rbNotDispo = new JRadioButton("Non disponible");
		rbNotDispo.setBackground(new Color(0, 0, 0));
		rbNotDispo.setOpaque(false);
		buttonGroup.add(rbNotDispo);
		rbNotDispo.setBounds(331, 149, 110,23);
		contentPane.add(rbNotDispo);

		// table (affichage de disponibilité)

		Object[][] data = null;

		String[] colomname = { "Date", "Disponibilité"};
		 DefaultTableModel model = new DefaultTableModel(data, colomname);
		JTable table1 = new JTable(model);
		table1.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		table1.setForeground(Color.black);
		table1.setRowHeight(30);

			
		
		// Affichage des données existantes
		HashMap<String,List<PrefEnum>> tmpPref = Service.docteurs.get(MembresDeGarde.table.getSelectedRow()).getPreference();

		for (Entry<String, List<PrefEnum>> entry : tmpPref.entrySet()) {

			System.out.println(entry.getKey());
			System.out.println(entry.getValue());

			Object[] row = new Object[2];
			row[0] = entry.getKey();
			row[1] = entry.getValue().get(MembresDeGarde.table.getSelectedRow());
			model.addRow(row);

		}

		// JScrollPane
		JScrollPane pane = new JScrollPane(table1);
		pane.setBounds(96, 274, 385, 197);
		contentPane.add(pane);

		// bouton ajouter
      
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		btnAjouter.setBounds(100, 210, 89, 23);
		contentPane.add(btnAjouter);
		Object[] row = new Object[2];
		btnAjouter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String dateDebut ;
				String dateFin ;
				String dateChoisie ;		
			
				dateDebut= verif_date(AjouterPlanning.dateD.getDate().getDate(),AjouterPlanning.dateD.getDate().getMonth(),AjouterPlanning.dateD.getDate().getYear());
				dateFin= verif_date(AjouterPlanning.dateF.getDate().getDate(),AjouterPlanning.dateF.getDate().getMonth(),AjouterPlanning.dateF.getDate().getYear());
				dateChoisie= verif_date(dateDispo.getDate().getDate(),dateDispo.getDate().getMonth(),dateDispo.getDate().getYear());

				int dateDebut_int = Integer.parseInt(dateDebut);
				int dateFin_int = Integer.parseInt(dateFin);
				int dateChoisie_int = Integer.parseInt(dateChoisie);
				
				if (dateChoisie_int < dateDebut_int || dateChoisie_int > dateFin_int) {
					JOptionPane
							.showMessageDialog(btnAjouter,
									"La date doit se situer entre le  "
											+ (String.format("%1$td/%1$tm/%1$tY",
													AjouterPlanning.dateD.getDate().getTime()))
											+ " et le "
											+ (String.format("%1$td/%1$tm/%1$tY",
													AjouterPlanning.dateF.getDate().getTime()))
							+ " \n \n                  Svp réssayez", "Erreur", JOptionPane.ERROR_MESSAGE);
				}

				else {
					if ((!rbDispoBut.isSelected()) && (!rbNotDispo.isSelected())) {

						JOptionPane.showMessageDialog(btnAjouter,
								"Un ou plusieurs champs sont vide\n \n                  Svp réssayez", "Erreur",
								JOptionPane.ERROR_MESSAGE);

					}
					

					else {                 
					    if ((rbDispoBut.isSelected()) && (nNuit<4)){
					    	    row[0] = String.format("%1$td/%1$tm/%1$tY",dateDispo.getDate());
						     	row[1] = PrefEnum.disponible;
							    model.addRow(row);
             							  								    
							   String key = String.format("%1$td/%1$tm/%1$tY",dateDispo.getDate());;
							    List<PrefEnum> value = Service.preference.getOrDefault(key, new LinkedList<>());
							    value.add(PrefEnum.disponible);
							    Service.preference.put(key, value);
						}else {
							if (rbNotDispo.isSelected()) {
					    	        row[0] = String.format("%1$td/%1$tm/%1$tY", dateDispo.getDate());
							    	row[1] = PrefEnum.non_disponible;
							    	model.addRow(row);
					
								   String key = String.format("%1$td/%1$tm/%1$tY",dateDispo.getDate());
								    List<PrefEnum> value = Service.preference.getOrDefault(key, new LinkedList<>());
								    value.add(PrefEnum.non_disponible);
								    Service.preference.put(key, value);		
								  
							}
						   }
					}
				}
			}
		});
		// boutton supprimer

		JButton btnSupprimerMembre = new JButton("Supprimer");
		btnSupprimerMembre.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		btnSupprimerMembre.setBounds(245, 210, 95, 23);
		contentPane.add(btnSupprimerMembre);

		btnSupprimerMembre.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int indice = table1.getSelectedRow();
				if (indice >= 0) {
					String date = model.getValueAt(indice, 0).toString();
					Object disp= model.getValueAt(indice, 1);
					model.removeRow(indice);
					// fonction pour supprimer disponibilité
					Service.deletedisponiblity(row,date,disp,MembresDeGarde.table);
				} else {
					System.out.println("Delete Error");
				}
			}
		});
		// boutton modifier

				JButton btnModifierMembre = new JButton("Modifier");
				btnModifierMembre.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
				btnModifierMembre.setBounds(385, 210, 95, 23);
				contentPane.add(btnModifierMembre);

				btnModifierMembre.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						int indice = table1.getSelectedRow();
						if (indice >= 0) {
							String date = model.getValueAt(indice, 0).toString();
							Object disp= model.getValueAt(indice, 1);
							model.setValueAt(Service.setdisponiblity(date,disp), indice, 1);
							
						} else {
							System.out.println("set Error");
						}
					}
				});
		
		// bouton valider
		JButton btnValider = new JButton("Valider ");
		btnValider.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		btnValider.setBounds(235, 506, 89, 23);
		contentPane.add(btnValider);
		btnValider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
				
				Service.docteurs.get(MembresDeGarde.table.getSelectedRow()).setPreference(Service.preference);

				setVisible(false);
				}
		   
		});

	}

	// preference set,get dans la classe Docteur

}
