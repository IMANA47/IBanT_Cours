package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Etudiant;
import com.gestionetud.entities.Matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EtudiantRepository implements GenericRep<Etudiant,Integer> {
    private final Connection connection;

    public EtudiantRepository() {
        this.connection = ConnexionBD.getInstance();
    }

    @Override
    public void save(Etudiant entity) {
        String sql = "INSERT INTO etudiant(nom,prenom,age)VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getNom());
            ps.setString(2, entity.getPrenom());
            ps.setInt(3,entity.getAge());
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Etudiant entity) {

        String sql = "update etudiant set nom = ?,prenom = ?,age = ? where id_etudiant = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getNom());
            ps.setString(2, entity.getPrenom());
            ps.setInt(3,entity.getAge());
            ps.setInt(4,entity.getId_etudiant());
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id_etudiant) {
        String sql = "delete from etudiant where id_etudiant = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,id_etudiant);
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Etudiant findById(Integer id_etudiant) {

        Etudiant etudiant = new Etudiant();
        String sql = "select * from etudiant where id_etudiant = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id_etudiant);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                etudiant = new Etudiant(result.getInt("id_etudiant");
                        result.getString("nom");
                        result.getString("prenom");
                        result.getInt("age");

            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> etudiant = new ArrayList<>();

        String sql = "select * from matiere";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                etudiant.add(
                        new Etudiant(
                                result.getInt("id_matiere");
                        result.getString("nom");
                        result.getString("prenom");
                        result.getInt("age"););
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return etudiant;
    }
}
