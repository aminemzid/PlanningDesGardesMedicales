package com.sifast.gardeplan.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;

import com.sifast.gardeplan.ihm.MembresDeGarde;
import com.sifast.gardeplan.model.Docteur;
import com.sifast.gardeplan.model.PlanningGarde;
import com.sifast.gardeplan.model.PrefEnum;
import com.toedter.calendar.JDateChooser;

public class Service {
	
	public static ArrayList<Docteur> docteurs = new ArrayList<Docteur>();
	public static int[] nbr = new int[1000];

	public static PlanningGarde plan = new PlanningGarde();
	public static HashMap<String, PrefEnum> preference = new HashMap<String, PrefEnum>();

	public Service() {
	}

	public void createDoctor() {
		Docteur docteur = new Docteur();
		docteur.setPreference(new HashMap<String, PrefEnum>());
		docteurs.add(docteur);
	}

	public void deleteDoctor(int indice) {
		docteurs.remove(indice);
	}
	
	// fonction pour gerer le planning dans MembreDeGarde
		public static  ArrayList<Docteur> genererPlanning(JTable table) {
	
			for (int i = 0; i < table.getRowCount(); i++) {
				docteurs.get(i).setNom(table.getValueAt(i,0).toString());
				
   // stocker le nbre de nuits dans le tableau nbr
				nbr[i]=Integer.parseInt(MembresDeGarde.table.getValueAt(i,1).toString());
			}
			return docteurs;
		}
	
	// fonction pour gerer la disponibilié dans Disponibilite

	public static ArrayList<Docteur> gererDisponiblite(JTable table, HashMap<String, PrefEnum> preference) {
       if (nbr[table.getSelectedRow()]<4)        
		{docteurs.get(table.getSelectedRow()).setPreference(preference);
		}
		return docteurs;
	}
	

	// foncion pour creer un planning
	public static void createPlanning(String nom, JDateChooser dateD, JDateChooser dateF, String nomserv) {
		plan.setDateDebut(dateD);
		plan.setDateFin(dateF);
		plan.setNomPlanning(nom);
		plan.setNomService(nomserv);
	}
	

	// fonction pour supprimer disponibilité
	public static void deletedisponiblity(Object[] row) {
		preference.remove(row);
	}

}