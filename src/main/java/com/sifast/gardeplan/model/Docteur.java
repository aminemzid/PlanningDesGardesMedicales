package com.sifast.gardeplan.model;

import java.util.HashMap;
import java.util.List;

public class Docteur {

	private String nom;
	private HashMap<String,List<PrefEnum>> preference;

	// constructeur

	public Docteur() {
	}

	public Docteur(String nom) {
		this.nom = nom;
	}

	public Docteur(HashMap<String, List<PrefEnum>> preference) {
		this.preference = preference;
	}

	// Nom set, get

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
  	public HashMap<String,List<PrefEnum>> getPreference() {
		return preference;
	}

	public void setPreference(HashMap<String, List<PrefEnum>> preference) {
		this.preference = preference;
	}
}