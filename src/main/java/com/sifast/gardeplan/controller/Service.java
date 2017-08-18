package com.sifast.gardeplan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;

import com.sifast.gardeplan.ihm.MembresDeGarde;
import com.sifast.gardeplan.model.Docteur;
import com.sifast.gardeplan.model.PlanningGarde;
import com.sifast.gardeplan.model.PrefEnum;
import com.toedter.calendar.JDateChooser;

public class Service {
	
	public static ArrayList<Docteur> docteurs = new ArrayList<Docteur>();
	public static int[] nbr = new int[100];
    public static int[] dimanche =new int[100];
	public static PlanningGarde plan = new PlanningGarde();
	public static HashMap<String,List<PrefEnum>> preference = new HashMap<String, List<PrefEnum>>();

	public Service() {
	}

	public void createDoctor() {
		Docteur docteur = new Docteur();
		docteur.setPreference(new HashMap<String,List<PrefEnum>>());
		docteurs.add(docteur);
	}

	public void deleteDoctor(int indice) {
		docteurs.remove(indice);
	}
	
	// fonction pour gerer le planning dans MembreDeGarde
		public static  ArrayList<Docteur> genererPlanning(JTable table) {
	
			for (int i = 0; i < table.getRowCount(); i++) {
				docteurs.get(i).setNom(table.getValueAt(i,0).toString());
					}
			return docteurs;
		}
		// stocker le nbre de nuits dans le tableau nbr
	     public static int[] generernombre(JTable table ) {
	    	 for (int i = 0; i < table.getRowCount(); i++) {
					nbr[i]=Integer.parseInt(MembresDeGarde.table.getValueAt(i,1).toString());
	    	 }
	    	 return nbr;
	     }

	     public static int[] generernombredimanche(JTable table ) {
	    	 for (int i = 0; i < table.getRowCount(); i++) {
					dimanche[i]=Integer.parseInt(MembresDeGarde.table.getValueAt(i,2).toString());
	    	 }
	    	 return dimanche;
	     }
	// fonction pour gerer la disponibilié dans Disponibilite

	public static ArrayList<Docteur> gererDisponiblite(JTable table, HashMap<String, List<PrefEnum>> preference) {
    //   if (nbr[table.getSelectedRow()]<4 && dimanche[table.getSelectedRow()]<2)        
		{docteurs.get(table.getSelectedRow()).setPreference(preference);
		}
		return docteurs;
	}
	

	// foncion pour creer un planning
	public static void createPlanning(String nom, JDateChooser dateD, JDateChooser dateF, Object nomserv,int nbmed) {
		plan.setDateDebut(dateD);
		plan.setDateFin(dateF);
		plan.setNomPlanning(nom);
		plan.setNomService(nomserv);
		plan.setNbrmed(nbmed);
	}
	

	// fonction pour supprimer disponibilité
	public static void deletedisponiblity(Object[] row,String date, Object disp,JTable table) {
		preference.remove(row);
	  		
	    int i = MembresDeGarde.table.getSelectedRow();

	    preference.get(date).remove(i);
	  
	}
	// fonction pour modifier disponibilité
		public static PrefEnum setdisponiblity(String date, Object disp) {
		
			PrefEnum test ;
		    int i = MembresDeGarde.table.getSelectedRow();
if (disp==PrefEnum.disponible) {test= PrefEnum.non_disponible;} 
else {test= PrefEnum.non_disponible;}

		    preference.get(date).set(i, test);
		    return test;
		  
		}

	
}