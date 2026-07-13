package org.gstudent.entities;

import java.util.Objects;

public class Utilisateur {
    private int id;
    private String login;
    private String password; // en clair pour l'exemple, à hasher en production
    private String role; // ex: "ADMIN", "USER"

    public Utilisateur() {}

    public Utilisateur(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}