package com.sifast.gardeplan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
	public static void affiche_tab(int j ,int i,String date,PdfPTable table) {	
		if (j==0) {
		 PdfPCell cell3 = new PdfPCell(new Paragraph(date));
         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(i).getNom()));
         table.addCell(cell3);
         table.addCell(cell4);}
		else { 		
		 PdfPCell cell3 = new PdfPCell(new Paragraph(""));
         PdfPCell cell4 = new PdfPCell(new Paragraph(Service.docteurs.get(i).getNom()));
         table.addCell(cell3);
         table.addCell(cell4);}
		}
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
   	    maFonte2.setSize(13.0f);
   	    
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
		 PdfPCell cell1 = new PdfPCell(new Paragraph("Date",maFonte2));
         PdfPCell cell2 = new PdfPCell(new Paragraph("Medecin en garde",maFonte2));
         table.addCell(cell1);
         table.addCell(cell2);
           
		Boolean test2 = true;
		int indice = 0;
		int n=Service.plan.getNbrmed();
		Docteur docteur;
		
		for (Object elem1 : MembresDeGarde.dates) { 
			// afficher le jour de la semaine 
			String str = elem1.toString();
			java.text.SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dte = sdf.parse(str);
			sdf = new SimpleDateFormat("EEEE");
			
			String strdim = "13/08/2017"; // un dimanche pour comparer avec les autres jours
			java.text.SimpleDateFormat sdfdim = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dtedim = sdfdim.parse(strdim);
			sdfdim = new SimpleDateFormat("EEEE");
			
			Boolean testdim=sdf.format(dte).equals(sdfdim.format(dtedim));
		    System.out.println(testdim);
			
			test2 = true;
			String nomdr="" ;
			for(int j=0; j<n; j++) {
			while (test2) {
				 docteur = Service.docteurs.get(indice % Service.docteurs.size());
				 if (Service.preference.get(elem1).indexOf(PrefEnum.disponible) <0)
					{
					indice++;
				test2 = false;
				}else {
					if (Service.preference.get(elem1).indexOf(PrefEnum.disponible) >= 0) {
			//si un medecin est diponible pour un dimanche mais a deja dépassé les 2 dimanches il sera considéré non disponible

					   if (testdim)
					   {
							for (int k =0;k<Service.preference.get(elem1).size();k++) 
							{
							   if(Service.preference.get(elem1).get(k) == PrefEnum.disponible &&	Service.dimanche[k]>=2 ) 
							        {Service.preference.get(elem1).set(k , PrefEnum.non_disponible);}
							}
						}
					Boolean test = false;
					// recherche du docteur disponible
					for (int i = 0; i < Service.docteurs.size(); i++)
					{
						  if(!(Service.docteurs.get(i).getPreference().containsKey(elem1))) 
						  {
					          	if  (Service.nbr[i]<4 && nomdr!=Service.docteurs.get(i).getNom()  &&
					          			(Service.preference.get(elem1).get(i) == PrefEnum.disponible)) 
						          {
							           affiche_tab(j , i, sdf.format(dte) +" le "+elem1.toString(), table);
						           	test = true;
				        		    indice++;
						            Service.nbr[i]+=1 ;
						             if (testdim){Service.dimanche[i]+=1;}
						            nomdr=Service.docteurs.get(i).getNom();
						            test2 = false;
							         break;
					           	}
						   }
					}
			   	    if ((!test) ){
					  if (Service.preference.get(elem1).get(indice % Service.docteurs.size()) == PrefEnum.disponible  && 
				          Service.nbr[indice % Service.docteurs.size()]<4  && nomdr!=Service.docteurs.get(indice % Service.docteurs.size()).getNom())
						  
					  { affiche_tab(j , indice % Service.docteurs.size(),sdf.format(dte)+" le "+ elem1.toString(), table);
				         nomdr=Service.docteurs.get(indice % Service.docteurs.size()).getNom();
						Service.nbr[indice % Service.docteurs.size()]+= 1;
						if(testdim ) { Service.dimanche[indice % Service.docteurs.size()]+= 1;}
       					indice++;
 					} else {
						//si un medecin est disponible mais a dépassé les 4 nuits il sera considéré non disponible
		
						if (Service.preference.get(elem1).indexOf(PrefEnum.disponible) >= 0 )
			            {
							for (int k =0;k<Service.preference.get(elem1).size();k++) {
								if(Service.preference.get(elem1).get(k) == PrefEnum.disponible && Service.nbr[k]>=4 ) 
								{Service.preference.get(elem1).set(k , PrefEnum.non_disponible);
								} 
								}
						
			       	// chercher le medecin disponible dans le cas ou on a besoin au mininmum 2 medecins differents
							int l=Service.preference.get(elem1).indexOf(PrefEnum.disponible);
					
							if (nomdr==Service.docteurs.get(l).getNom())
				              { l+=1;
				              	if ( Service.preference.get(elem1).indexOf(PrefEnum.disponible) >= 0 )
					        	{while (l< Service.docteurs.size() && Service.preference.get(elem1).get(l) != PrefEnum.disponible) 
				                      {l+=1 ;} }
				            	}
			
						 if ( l< Service.docteurs.size() && Service.preference.get(elem1).indexOf(PrefEnum.disponible) >= 0
								&& Service.preference.get(elem1).get(l) == PrefEnum.disponible) 
							{//System.out.println(Service.preference.get(elem1).get(l));
							affiche_tab(j , l, sdf.format(dte)+" le "+elem1.toString(), table);

				         nomdr=Service.docteurs.get(l).getNom();
				         indice ++ ;
					     Service.nbr[l]+= 1;
					     if (testdim) {Service.dimanche[l]+=1;}
					     }
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
	  table.setWidths(new int[]{10, 15});   
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
