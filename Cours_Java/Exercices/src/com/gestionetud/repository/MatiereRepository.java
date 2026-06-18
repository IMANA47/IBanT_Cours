package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Matiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MatiereRepository implements GenericRep<Matiere,Integer> {

    private final Connection connection;
    public MatiereRepository(){
        this.connection = ConnexionBD.getInstance();
    }

    @Override
    public void save(Matiere entity) {
        String sql = "INSERT INTO matiere(code, libelle) VALUES(?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getCode());
            ps.setString(2, entity.getLibelle());
            ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Matiere entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Matiere findById(Integer integer) {
        return null;
    }

    @Override
    public List<Matiere> findAll() {
        return List.of();
    }
}
