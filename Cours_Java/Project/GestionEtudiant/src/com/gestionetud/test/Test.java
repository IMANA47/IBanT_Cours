package com.gestionetud.test;

import com.gestionetud.entities.Composer;
import com.gestionetud.entities.Matiere;
import com.gestionetud.repository.MatiereRepository;

public class Test {
    public static void main(String[] args) {
        Etudiant etud = new Etudiant(1, "KIMBATSA", "Ange", "30");
        Matiere matiere1 = new Matiere(1, "POO", "Programmation Oriente Objet");
        Matiere matiere2 = new Matiere(2, "BDD", "Base De Données");
        Composer composer1 = new Composer(1, 15, etud, matiere1);
        Composer composer2 = new Composer(2, 12, etud, matiere2);
        System.out.println(composer1);
        System.out.println(composer2);
        System.out.println("composant de l'etudiant");
        System.out.println("Nom: " + composer1.getEtudiant().getNom());
        System.out.println("Prenom: " + etud.getPrenom());
        System.out.println("age: " + etud.getAge());
        System.out.println("Matiere 1: " + composer1.getMatiere().getCode() + "" + composer1.getNote());
        System.out.println("Matiere 2: " + composer2.getMatiere().getCode() + "" + composer2.getNote());
        System.out.println("Moyenne :" + (composer1.getNote() + composer2.getNote()) / 2);
        System.out.println(etud);
        etud.setId_etudiant(1);
        System.out.println(etud.getId_etudiant());

        //System.out.println(etud);
        //etud.setId(1);
        //etud.setNom("MALONGA");
        //etud.setPrenom("Jean");
        //System.out.println(etud.getNom());
        //ConnexionBD.getInstance();
        MatiereRepository mr = new MatiereRepository();
        //mr.save(matiere1);
        //mr.save(matiere2);
        //mr.update(matiere2);
        //mr.delete(matiere2.getId());
        //System.out.println(mr.findById(2));
        System.out.println(mr.findAll());
    }
}
