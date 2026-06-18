package src.gestionetud.entities;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private int age;


//Getters et Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    //constructeur avec parametres
    public Etudiant(int age, String prenom, String nom, int id) {
        this.age = age;
        this.prenom = prenom;
        this.nom = nom;
        this.id = id;
    }


    //constructeur sans parametre
    public Etudiant() {
    }

    /*@Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                '}';
    }*/

    //modification

    @Override
    public String toString() {
        return id + ";" + nom + ";" + prenom + ";" + age + " ans";
    }
}
