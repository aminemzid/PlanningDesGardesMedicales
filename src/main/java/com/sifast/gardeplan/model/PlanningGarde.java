package com.sifast.gardeplan.model;

import com.toedter.calendar.JDateChooser;

public class PlanningGarde {

	String NomPlanning;
	String NomService ;
	JDateChooser DateDebut;
	JDateChooser DateFin;

	// Constructeurs

	public PlanningGarde() {
	}

	public PlanningGarde(String NomPlanning, JDateChooser DateDebut, JDateChooser DateFin, String NomService ) {
		this.NomPlanning = NomPlanning;
		this.DateDebut = DateDebut;
		this.DateFin = DateFin;
		this.NomService = NomService;
	}

	// Methodes set,get

	public String getNomPlanning() {
		return NomPlanning;
	}

	public void setNomPlanning(String nomPlanning) {
		this.NomPlanning = nomPlanning;
	}
	
	
	public String getNomService() {
		return NomService;
	}

	public void setNomService(String nomService) {
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

}
