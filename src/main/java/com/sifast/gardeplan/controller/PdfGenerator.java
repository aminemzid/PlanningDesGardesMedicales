package com.sifast.gardeplan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sifast.gardeplan.ihm.MembresDeGarde;
import com.sifast.gardeplan.model.Docteur;
import com.sifast.gardeplan.model.PrefEnum;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
public class PdfGenerator {
	public static Document document = new Document();
   	public static void generatePdfFile() throws Exception {
		
   	  BaseFont fonte = BaseFont.createFont(
   	          BaseFont.COURIER,
   	          BaseFont.CP1252,
   	          BaseFont.NOT_EMBEDDED);
   	      Font maFonte = new Font(fonte);
    	maFonte.setColor(BaseColor.BLUE);
   	    maFonte.setStyle(Font.BOLD);
   	    maFonte.setSize(30.0f);
  		  	    
   	  BaseFont fonte2 = BaseFont.createFont(
   	          BaseFont.COURIER,
   	          BaseFont.CP1252,
   	          BaseFont.NOT_EMBEDDED);
   	      Font maFonte2 = new Font(fonte2);
    	maFonte2.setColor(BaseColor.GRAY);
    	maFonte2.setStyle(Font.BOLD);
   	    maFonte2.setSize(10.0f);
   	    
		PdfWriter.getInstance(document, new FileOutputStream("planning.pdf"));
		document.open();
		Paragraph para =new Paragraph("Hopital Habib Bourguiba Sfax",maFonte2);
		para.setAlignment(Element.ALIGN_RIGHT);
		document.add(para);
		document.add(new Paragraph("Planning des Gardes Medicales",maFonte));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("Nom du planning : " + Service.plan.getNomPlanning()));
		document.add(new Paragraph("Service de la garde : " + Service.plan.getNomService()));
		document.add(new Paragraph("Periode de la garde : du " + String.format("%1$td/%1$tm/%1$tY", Service.plan.getDateDebut().getDate()) + " au "
				+ String.format("%1$td/%1$tm/%1$tY", Service.plan.getDateFin().getDate())));
		document.add(new Paragraph("Nombre de medecins présents par jour de garde : " + Service.plan.getNbrmed()));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));
	
		PdfPTable table = new PdfPTable(2);	
		 PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
         PdfPCell cell2 = new PdfPCell(new Paragraph("Medecin en garde"));
         table.addCell(cell1);
         table.addCell(cell2);
           
		Boolean test2 = true;
		int indice = 0;
		int n=Service.plan.getNbrmed();
		Docteur docteur;
		
		for (Object elem1 : MembresDeGarde.dates) { //na3mlou test
			test2 = true;
			String nomdr="" ;
			for(int j=0; j<n; j++) {
			while (test2) {
				 docteur = Service.docteurs.get(indice % Service.docteurs.size());
				 if (Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) <0)
					{
					indice++;
				test2 = false;
				}else {
					if (Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) >= 0) {

					Boolean test = false;
					// recherche du docteur disponible
					for (int i = 0; i < Service.docteurs.size(); i++) {
						  if(!(Service.docteurs.get(i).getPreference().containsKey(elem1))) {
						if  (Service.nbr[i]<4 && nomdr!=Service.docteurs.get(i).getNom()  && (Service.preference.get(elem1).get(i) == PrefEnum.dispo_but)) {
							//System.out.println(Service.docteurs.get(indice % Service.docteurs.size()).getNom());
							if (j==0) {
							 PdfPCell cell3 = new PdfPCell(new Paragraph(elem1.toString()));
					         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(i).getNom()));
					         table.addCell(cell3);
					         table.addCell(cell4);}
							else { 		
							 PdfPCell cell3 = new PdfPCell(new Paragraph(""));
					         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(i).getNom()));
					         table.addCell(cell3);
					         table.addCell(cell4);;}
						   	test = true;
						    indice++;
						    Service.nbr[i]=Service.nbr[i]+ 1 ;
						    nomdr=Service.docteurs.get(i).getNom();
						    test2 = false;
							break;
					    	}
						}
					}
			   	    if ((!test) ){
					  if (Service.preference.get(elem1).get(indice % Service.docteurs.size()) == PrefEnum.dispo_but  && 
				(Service.nbr[indice % Service.docteurs.size()]<4 ) &&  nomdr != Service.docteurs.get(indice % Service.docteurs.size()).getNom()) {
						if (j==0) {
						 PdfPCell cell3 = new PdfPCell(new Paragraph(elem1.toString()));
				         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(indice % Service.docteurs.size()).getNom()));
				         table.addCell(cell3);
				         table.addCell(cell4);}
						else {  
						 PdfPCell cell3 = new PdfPCell(new Paragraph(""));
				         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(indice % Service.docteurs.size()).getNom()));
				         table.addCell(cell3);
				         table.addCell(cell4);}
				         nomdr=Service.docteurs.get(indice % Service.docteurs.size()).getNom();
						Service.nbr[indice % Service.docteurs.size()]+= 1;
						//affichage docteur
		   			//	System.out.println(	Service.docteurs.get(indice % Service.docteurs.size()).getNom());
					indice++;
					} else { 
						while(Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) >= 0 && Service.nbr[Service.preference.get(elem1).indexOf(PrefEnum.dispo_but)]>=4 ) 
						{Service.preference.get(elem1).set(Service.preference.get(elem1).indexOf(PrefEnum.dispo_but), PrefEnum.not_dispo);} 
						if (Service.preference.get(elem1).indexOf(PrefEnum.dispo_but) >= 0 )
						{
							int l=Service.preference.get(elem1).indexOf(PrefEnum.dispo_but);
							while (nomdr==Service.docteurs.get(l).getNom() && Service.preference.get(elem1).get(l) == PrefEnum.dispo_but )
							{l+=1;}
						{if (j==0) {
						 PdfPCell cell3 = new PdfPCell(new Paragraph(elem1.toString()));
				         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(l).getNom()));
				         table.addCell(cell3);
				         table.addCell(cell4); }
						 else {
						 PdfPCell cell3 = new PdfPCell(new Paragraph(""));
				         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(l).getNom()));
				         table.addCell(cell3);
				         table.addCell(cell4);}
				         nomdr=Service.docteurs.get(l).getNom();
				         System.out.println(nomdr);
				         indice ++ ;
					     Service.nbr[l]+= 1;}
									}
						 }
      					break;
					} 
					}else {
					//rien faire	
				          }
				}
								}
			}	}
	  table.setWidths(new int[]{8, 15});   
		document.add(table);
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("\n"));	
		Paragraph parag =new Paragraph("Ce planning est crée le " +String.format("%1$td/%1$tm/%1$tY",Calendar.getInstance().getTime().getTime ()));
		parag.setAlignment(Element.ALIGN_RIGHT);
		document.add(parag);
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
