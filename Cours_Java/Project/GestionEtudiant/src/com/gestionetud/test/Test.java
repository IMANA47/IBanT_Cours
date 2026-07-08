package com.gestionetud.test;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Composer;
import com.gestionetud.entities.Etudiant;
import com.gestionetud.entities.Matiere;
import com.gestionetud.exception.DatabaseException;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.service.ComposerService;
import com.gestionetud.service.EtudiantService;
import com.gestionetud.service.MatiereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

/**
 * Classe de test principale du projet pour la démonstration.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        logger.info("Lancement du programme de test...");

        // 1. Instanciation des objets en mémoire
        Etudiant etud = new Etudiant(0, "KIMBATSA", "Ange", 30);
        Matiere matiere1 = new Matiere(0, "POO", "Programmation Orientee Objet");
        Matiere matiere2 = new Matiere(0, "BDD", "Base De Données");

        System.out.println("=== 1. Affichage des objets en mémoire ===");
        Composer composer1 = new Composer(0, 15.0, etud, matiere1);
        Composer composer2 = new Composer(0, 12.0, etud, matiere2);
        System.out.println(composer1);
        System.out.println(composer2);
        System.out.println("\nComposant de l'etudiant :");
        System.out.println("Nom: " + composer1.getEtudiant().getNom());
        System.out.println("Prenom: " + etud.getPrenom());
        System.out.println("age: " + etud.getAge() + " ans");
        System.out.println("Matiere 1: " + composer1.getMatiere().getCode() + " -> Note: " + composer1.getNote());
        System.out.println("Matiere 2: " + composer2.getMatiere().getCode() + " -> Note: " + composer2.getNote());
        System.out.println("Moyenne : " + (composer1.getNote() + composer2.getNote()) / 2);
        System.out.println(etud);

        System.out.println("\n=== 2. Test des services et de la base de données ===");
        try {
            // Nettoyage initial par requêtes ciblées sur les noms et codes de test pour assurer la répétabilité
            logger.info("Nettoyage des anciennes données de test dans la base...");
            try (PreparedStatement ps1 = ConnexionBD.getInstance().prepareStatement(
                    "DELETE FROM composer WHERE id_etudiant IN (SELECT id_etudiant FROM etudiant WHERE nom = ? AND prenom = ?)")) {
                ps1.setString(1, etud.getNom());
                ps1.setString(2, etud.getPrenom());
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = ConnexionBD.getInstance().prepareStatement(
                    "DELETE FROM etudiant WHERE nom = ? AND prenom = ?")) {
                ps2.setString(1, etud.getNom());
                ps2.setString(2, etud.getPrenom());
                ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = ConnexionBD.getInstance().prepareStatement(
                    "DELETE FROM matiere WHERE code IN (?, ?)")) {
                ps3.setString(1, matiere1.getCode());
                ps3.setString(2, matiere2.getCode());
                ps3.executeUpdate();
            }
            logger.info("Nettoyage terminé.");

            EtudiantService etudiantService = new EtudiantService();
            MatiereService matiereService = new MatiereService();
            ComposerService composerService = new ComposerService();

            logger.info("Enregistrement de l'étudiant...");
            etudiantService.registerEtudiant(etud);
            System.out.println("Nouvel ID étudiant généré : " + etud.getIdEtudiant());

            logger.info("Enregistrement des matières...");
            matiereService.registerMatiere(matiere1);
            matiereService.registerMatiere(matiere2);
            System.out.println("ID Matière 1 généré : " + matiere1.getIdMatiere());
            System.out.println("ID Matière 2 généré : " + matiere2.getIdMatiere());

            logger.info("Enregistrement des notes...");
            // On met à jour les références d'ID des notes associées avec les ID réels générés par la BD
            composer1.setEtudiant(etud);
            composer1.setMatiere(matiere1);
            composer2.setEtudiant(etud);
            composer2.setMatiere(matiere2);

            composerService.registerNote(composer1);
            composerService.registerNote(composer2);

            // Calcul de la moyenne via le service
            double moyenneService = composerService.calculateMoyenne(etud.getIdEtudiant());
            System.out.println("Moyenne calculée via le Service : " + moyenneService);

            System.out.println("Liste de toutes les matières en BD :");
            System.out.println(matiereService.getAllMatieres());

        } catch (DatabaseException e) {
            logger.warn("ATTENTION : Impossible de finaliser le test en base de données. " +
                    "Veuillez vérifier que votre serveur MariaDB est démarré et configuré avec les bons accès dans 'data.properties'. " +
                    "Message de l'erreur : {}", e.getMessage());
        } catch (ValidationException e) {
            logger.error("Erreur lors des validations de données : {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur inattendue durant les tests : ", e);
        }
    }
}
