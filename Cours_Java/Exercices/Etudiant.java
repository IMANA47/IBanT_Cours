// Fichier: Etudiant.java
public class Etudiant {
    // Attributs privés (encapsulation)
    private String nom;
    private int age;
    private double moyenne;

    // Constructeur
    public Etudiant(String nom, int age, double moyenne) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("L'âge doit être positif.");
        }
        if (moyenne < 0 || moyenne > 20) {
            throw new IllegalArgumentException("La moyenne doit être comprise entre 0 et 20.");
        }
        this.nom = nom;
        this.age = age;
        this.moyenne = moyenne;
    }

    // Méthode d'instance pour afficher les infos
    public void afficherInfos() {
        System.out.println("Nom : " + nom);
        System.out.println("Âge : " + age + " ans");
        System.out.println("Moyenne : " + moyenne);
    }

    // Méthode d'instance pour savoir si l'étudiant est admis
    public boolean estAdmis() {
        return moyenne >= 10.0;
    }

    // Méthode statique pour comparer deux étudiants
    public static Etudiant meilleurEtudiant(Etudiant e1, Etudiant e2) {
        if (e1 == null || e2 == null) {
            throw new IllegalArgumentException("Les étudiants ne peuvent pas être null.");
        }
        return (e1.moyenne >= e2.moyenne) ? e1 : e2;
    }
}