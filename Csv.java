package com.sifast.stage.controller ;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.sifast.stage.ihm.MembresDeGarde;
import com.sifast.stage.model.Docteur;
import com.sifast.stage.model.PrefEnum;

public class Csv {

	public static void generateCsvFile() throws IOException {

		FileWriter fileWriter = new FileWriter("Planning CSV.csv");
		fileWriter.append("Nom de Planning:" + Service.plan.getNomPlanning());

		fileWriter.append("\n du " + String.format("%1$td/%1$tm/%1$tY", Service.plan.getDateDebut().getDate())
		+ " au " + String.format("%1$td/%1$tm/%1$tY\n", Service.plan.getDateFin().getDate()));
		fileWriter.append("\n");
		Boolean test2 = true;
		int indice = 0;
		Docteur docteur;
		for (Object elem1 : MembresDeGarde.dates) { // na3mlou test
			test2 = true;

			while (test2) {
				docteur = Service.docteurs.get(indice % Service.docteurs.size());
				if (!(docteur.getPreference().containsKey(elem1))) // champ
																	// vide=
																	// dispo
				{
					fileWriter.append( elem1 + ","
							+ Service.docteurs.get(indice % Service.docteurs.size()).getNom() + ",en garde \n");
					// System.out.println(Service.docteurs.get(indice %
					// Service.docteurs.size()).getNom());
					indice++;
					test2 = false;
				} else {
					if (docteur.getPreference().get(elem1).equals(PrefEnum.not_dispo)) {
						indice++;
						test2 = false;
					} else if (docteur.getPreference().get(elem1).equals(PrefEnum.dispo_but)) {
						Boolean test = false;
						// recherche du docteur disponible
						for (int i = 0; i < Service.docteurs.size(); i++) {
							if (!(Service.docteurs.get(i).getPreference().containsKey(elem1))) {
								fileWriter.append(elem1 + ", " + Service.docteurs.get(i).getNom()
										+ "  , garde \n");
								System.out.println(Service.docteurs.get(indice % Service.docteurs.size()).getNom());
								test = true;
								indice++;
								test2 = false;
								break;
							}
						}
						if (!test) {
							fileWriter.append( elem1 + ","
									+ Service.docteurs.get(indice % Service.docteurs.size()).getNom()
									+ " ,en garde 	\n ");
							// affichage docteur
							// System.out.println( Service.docteurs.get(indice %
							// Service.docteurs.size()).getNom());
							indice++;

							break;
						}
						break;
					} else {
						// rien faire

					}
				}
			}
		}

		fileWriter.close();

		try {

			// TODO use relative path instead of absolut path
			if ((new File("planning.pdf")).exists()) {

				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler planning.pdf");
				p.waitFor();

			}

			System.out.println("Le Planning a été crée avec succès");
			fileWriter.close();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

}