package com.sifast.gardeplan.ihm;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sifast.gardeplan.controller.Service;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class AjouterPlanning extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	//private JTextField textField2;
	static private AjouterPlanning frame1;
	public static JDateChooser dateF;
	public static JDateChooser dateD;
	private JComboBox liste2;
	private JComboBox liste;
    String nom_fichier_image = "image1.jpg";

	// classe principale
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame1= new AjouterPlanning();
					frame1.setVisible(true);
					frame1.setSize(600, 600);
					frame1.setLocationRelativeTo(null);
					frame1.setTitle("Ajouter Planning");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//fonction pour verifier les dates pour pouvoir les comparer apres
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
	@SuppressWarnings("serial")
	public AjouterPlanning() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel()
		 {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
 
                ImageIcon m = new ImageIcon(nom_fichier_image);
                Image monImage = m.getImage();
                g.drawImage(monImage, 0, 0,this);
             }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// Name of planning

		JLabel lblAjouterNouveauPlanning = new JLabel("Ajouter nouveau planning :");
		lblAjouterNouveauPlanning.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblAjouterNouveauPlanning.setBounds(100, 20, 350, 100);
		contentPane.add(lblAjouterNouveauPlanning);

		JLabel lblNomDuPlanning = new JLabel("Nom du Planning :");
		lblNomDuPlanning.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNomDuPlanning.setBounds(110, 125, 150, 20);
		contentPane.add(lblNomDuPlanning);

		textField = new JTextField();
		textField.setBounds(310, 120, 154, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//nbre de personne necessaire pour la garde 
		JLabel lblNbrpers = new JLabel("Nombre de medecin necessaire :");
		lblNbrpers.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNbrpers.setBounds(30, 175, 260, 15);
		contentPane.add(lblNbrpers);
		
		Object[] element = new Object[]{"1","2"};
		liste = new JComboBox(element);
		liste.setBounds(310, 170, 154, 26);
		contentPane.add(liste);
		
		//service de garde
    	JLabel lblNomservice = new JLabel("Nom du service de garde :");
		lblNomservice.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNomservice.setBounds(80, 345, 200, 18);
		contentPane.add(lblNomservice);

		Object[] elements = new Object[]{"service d'accueil de traitement des urgences", "maternité", "chirurgie", "radiologie", "neurologie"};
		liste2 = new JComboBox(elements);
		liste2.setBounds(310, 340, 200, 26);
		contentPane.add(liste2);
		
		// Date debut

		JLabel lblDateDeDbut = new JLabel("Date de d\u00E9but :");
		lblDateDeDbut.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDateDeDbut.setBounds(110, 234, 130, 14);
		contentPane.add(lblDateDeDbut);

		dateF = new JDateChooser();
		dateF.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dateF.getCalendarButton().setForeground(new Color(0, 0, 0));
		dateF.getCalendarButton().setBackground(SystemColor.activeCaption);
		dateF.setDateFormatString("dd-MM-yyyy");
		dateF.setBounds(310, 287, 97, 20);
		contentPane.add(dateF);

		// Date fin

		JLabel lblDateDeFin = new JLabel("Date de fin :");
		lblDateDeFin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDateDeFin.setBounds(110, 287, 97, 14);
		contentPane.add(lblDateDeFin);

		dateD = new JDateChooser();
		dateD.getCalendarButton().setBackground(SystemColor.activeCaption);
		dateD.setDateFormatString("dd-MM-yyyy");
		dateD.setBounds(310, 228, 97, 20);
		contentPane.add(dateD);

		// bouton ajouter

		JButton butAjouter = new JButton("Ajouter");
		butAjouter.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		butAjouter.setBounds(197, 398, 200, 50);
		contentPane.add(butAjouter);

		butAjouter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String dateDebut1 ;
				String dateFin1 ;
							
				dateDebut1= verif_date(AjouterPlanning.dateD.getDate().getDate(),AjouterPlanning.dateD.getDate().getMonth(),AjouterPlanning.dateD.getDate().getYear());
				dateFin1= verif_date(AjouterPlanning.dateF.getDate().getDate(),AjouterPlanning.dateF.getDate().getMonth(),AjouterPlanning.dateF.getDate().getYear());

				int dateDebut1_int = Integer.parseInt(dateDebut1);
				int dateFin1_int = Integer.parseInt(dateFin1);
				System.out.println(dateDebut1_int);
				System.out.println(dateFin1_int);

				if ((textField.getText().isEmpty()) || (dateDebut1==null) || (dateF.getDate()==null)) {
					JOptionPane.showMessageDialog(null, "Un ou plusieurs champs sont vide\n \n                  Svp réssayez", "Erreur",
							JOptionPane.ERROR_MESSAGE);

				}
				
				else{
					
					if (dateDebut1.equals(dateFin1)){
				JOptionPane.showMessageDialog(butAjouter, "La date de debut doit etre differente de la date de fin\n \n                  Svp réssayez", "Erreur",
						JOptionPane.ERROR_MESSAGE);	}
				else{
					
					if ((!(textField.getText().isEmpty())) && (!(dateDebut1==null)) && (!(dateFin1==null))
				
						&& (dateDebut1_int < dateFin1_int)) {

//					plan.setDateDebut(dateD);
//					plan.setDateFin(dateF);
//					plan.setNomPlanning(textField.getText().toString());

					Service.createPlanning(textField.getText().toString(), dateD, dateF,liste2.getSelectedItem(),Integer.parseInt(liste.getSelectedItem().toString()));

					MembresDeGarde frame = new MembresDeGarde();
					frame.setSize(600, 600);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Membres de garde");
					frame.setVisible(true);
					frame1.dispose();
					}
			
				    else {
					JOptionPane.showMessageDialog(null, "La date de fin doit etre situé aprés la date début\n \n                  Svp réssayez", "Erreur",
							JOptionPane.ERROR_MESSAGE);

				}   }       
				                        
			}}

		});

	}
}
