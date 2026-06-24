package com.gestionetud.test;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Composer;
import com.gestionetud.entities.Etudiant;
import com.gestionetud.entities.Matiere;

public class Test {
    public static void main (String[] args){
        Etudiant etud =new Etudiant(3,"NGANGA","Dorian",25);
        Matiere matiere =new Matiere(1,"POO","Programmation Orientée Objet");
        Matiere matiere2 =new Matiere(2,"BDD","Base De Doonnées");
        Composer composer1=new Composer(1,15,matiere,etud);
        Composer composer2=new Composer(2,12,matiere2,etud);

        System.out.println(composer1);
        System.out.println(composer2);

        //Autre affichage
        System.out.println("Composition de l'etudiant");
        System.out.println("Nom:" + etud.getNom());
        System.out.println("Nom:" + composer1.getEtudiant().getNom());
        System.out.println("Prenom:" + etud.getPrenom());
        System.out.println("Age:" + etud.getAge());
        System.out.println("Matiere 1:" +composer1.getMatiere().getCode() + " " +composer1.getNote());
        System.out.println("Matiere 2:" +composer2.getMatiere().getCode() + " " +composer2.getNote());
        System.out.println("Moyenne:" + ((composer1.getNote() + composer2.getNote())/2));


        System.out.println(etud);
        etud.setId(1);
        etud.setNom("Jeje");
        //etud.nom = "SAMBA";
        //etud.prenom = "Jean";
        //System.out.println(etud.getId());
        //System.out.println(etud.getNom());
        ConnexionBD.getInstance();



    }
}
