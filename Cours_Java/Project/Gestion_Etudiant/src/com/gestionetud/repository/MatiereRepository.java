package com.gestionetud.repository;

import com.gestionetud.entities.Matiere;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class MatiereRepository implements GenericRep<Matiere,Integer> {
    private int connection;


    @Override
    public void save(Matiere entity) {

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
        String sql = "SELECT * FROM matiere";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
        while (result.next()){
            int id =result.getInt("idm");
            String code = result.getString("code");
            String libelle = result.getString("libelle");
            matieres.add(new Matiere(id,code,libelle));
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return matieres;
    }
}
