package com.sifast.gardeplan.controller;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sifast.gardeplan.ihm.MembresDeGarde;
import com.sifast.gardeplan.model.Docteur;
import com.sifast.gardeplan.model.PrefEnum;



public class PdfGenerator {
	public static Document document = new Document();
	public static void generatePdfFile() throws Exception {
		
		
		

		PdfWriter.getInstance(document, new FileOutputStream("planning.pdf"));
		document.open();
		
		document.add(new Paragraph("Planning des Gardes Medicales"));
		document.add(new Paragraph(Service.plan.getNomPlanning()));
		document.add(new Paragraph("dans le service " + Service.plan.getNomService()));
		document.add(new Paragraph("du " + String.format("%1$td/%1$tm/%1$tY", Service.plan.getDateDebut().getDate()) + " au "
				+ String.format("%1$td/%1$tm/%1$tY", Service.plan.getDateFin().getDate())));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
				
		
		Boolean test2 = true;
		int indice = 0;
		Docteur docteur;
		for (Object elem1 : MembresDeGarde.dates) { //na3mlou test
			test2 = true;
			
			while (test2) {
				 docteur = Service.docteurs.get(indice % Service.docteurs.size());
				/* if (!(docteur.getPreference().containsKey(elem1))) // champ
															         // vide=
														            // dispo
				{
					document.add(new Paragraph("Le " + elem1 + ", le docteur:  " + Service.docteurs.get(indice % Service.docteurs.size()).getNom() + " en garde "));
					System.out.println(	Service.docteurs.get(indice % Service.docteurs.size()).getNom());
					indice++;
					Service.nbr[indice % Service.docteurs.size()]+=1;
					test2 = false;
				} 
				else{  */
				 
				//if (docteur.getPreference().get(elem1).equals(PrefEnum.not_dispo)) {
					if (Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) <0)
					{
					indice++;
				test2 = false;
				}else {
			   	//if (docteur.getPreference().get(elem1).equals(PrefEnum.dispo_but)) {
					if (Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) >= 0) {

					Boolean test = false;
					// recherche du docteur disponible
					for (int i = 0; i < Service.docteurs.size(); i++) {
						if  (Service.nbr[i]<4) {
						if  (!(Service.docteurs.get(i).getPreference().containsKey(elem1)) && (Service.preference.get(elem1).get(i) == PrefEnum.dispo_but)) {
							document.add(new Paragraph("Le " + elem1 + ", le docteur:  " + Service.docteurs.get(i).getNom() + " en garde "));
							System.out.println(Service.docteurs.get(indice % Service.docteurs.size()).getNom());
							test = true;
							indice++;
							Service.nbr[i]=Service.nbr[i]+ 1 ;
							test2 = false;
							break;
					    	}
						}
					}
			   	if ((!test) && (Service.nbr[indice % Service.docteurs.size()]<4 )){
					if (Service.preference.get(elem1).get(indice % Service.docteurs.size()) == PrefEnum.dispo_but) {
						document.add(new Paragraph("Le " + elem1 + ", le docteur: " + Service.docteurs.get(indice % Service.docteurs.size()
								).getNom() + " en garde "));
						Service.nbr[indice % Service.docteurs.size()]+= 1;
						//affichage docteur
		   			//	System.out.println(	Service.docteurs.get(indice % Service.docteurs.size()).getNom());
					indice++;
					} else {document.add(new Paragraph("Le " + elem1 + ", le docteur: " + Service.docteurs.get
							(Service.preference.get(elem1).indexOf(PrefEnum.dispo_but)).getNom() + " en garde "));
					        indice ++ ;
					        Service.nbr[Service.preference.get(elem1).indexOf(PrefEnum.dispo_but)]+= 1;
                     }
					break;
					}
					break;
				}else {
					//rien faire	
				}
				}
					//}
				}
		}
	
		document.close();

		try {

			// TODO use relative path instead of absolut path
			if ((new File(
					"F:\\work\\projects\\PlanningDesGardesMedicales\\planning.pdf"))
					.exists()) {

				Process p = Runtime
						.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler F:\\work\\projects\\PlanningDesGardesMedicales\\planning.pdf");
				p.waitFor();

			} 
			
			System.out.println("Le Planning a été crée avec succès");
			document.close();

		} catch (Exception ex) {
			
			ex.printStackTrace();
		}

	}

}
