package com.sifast.gardeplan.model;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

public class PlanningGarde {

	String NomPlanning;
	int Nbmed;
	JDateChooser DateDebut;
	JDateChooser DateFin;
    Object NomService ;
	// Constructeurs

	public PlanningGarde() {
	}

	public PlanningGarde(String NomPlanning, JDateChooser DateDebut, JDateChooser DateFin, Object NomService,int nbmed ) {
		this.NomPlanning = NomPlanning;
		this.DateDebut = DateDebut;
		this.DateFin = DateFin;
		this.NomService = NomService;
		this.Nbmed = nbmed ;
	}

	// Methodes set,get

	public String getNomPlanning() {
		return NomPlanning;
	}

	public void setNomPlanning(String nomPlanning) {
		this.NomPlanning = nomPlanning;
	}
	
	
	public Object getNomService() {
		return NomService;
	}

	public void setNomService(Object nomService) {
		this.NomService = nomService;
	}

	public JDateChooser getDateDebut() {
		return DateDebut;
	}

	public void setDateDebut(JDateChooser dateDebut) {
		this.DateDebut = dateDebut;
	}

	public JDateChooser getDateFin() {
		return DateFin;
	}

	public void setDateFin(JDateChooser dateFin) {
		this.DateFin = dateFin;
	}

	public int getNbrmed() {
		return Nbmed;
	}

	public void setNbrmed(int nbmed) {
		this.Nbmed = nbmed;
	}
	

}
