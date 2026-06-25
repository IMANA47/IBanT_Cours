package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MatiereRepository implements GenericRep<Matiere,Integer> {
     private final Connection connection;

    public MatiereRepository() {
        this.connection = ConnexionBD.getInstance();
    }

    @Override
    public void save(Matiere entity) {
        String sql = "INSERT INTO matiere(code,libelle)VALUES(?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getCode());
            ps.setString(2, entity.getLibelle());
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Matiere entity) {
        String sql = "update matiere set code = ?,libelle = ? where id_matiere = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getCode());
            ps.setString(2, entity.getLibelle());
            ps.setInt(3,entity.getId_matiere());
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id_matiere) {
        String sql = "delete from matiere where id_matiere = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,id_matiere);
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Matiere findById(Integer id) {
        Matiere matiere = new Matiere();
        String sql = "select * from matiere where id_matiere = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                matiere = new Matiere(result.getInt("id_matiere"),
                        result.getString("code"),
                        result.getString("libelle"));

            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return matiere;
    }

    @Override
    public List<Matiere> findAll() {
        List<Matiere> matieres = new ArrayList<>();

        String sql = "select * from matiere";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                matieres.add(new Matiere(result.getInt("id_matiere"),
                        result.getString("code"),
                        result.getString("libelle")));

            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return matieres;
    }

}
