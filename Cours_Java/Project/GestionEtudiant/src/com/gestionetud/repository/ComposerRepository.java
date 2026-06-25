package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Composer;

import java.sql.Connection;
import java.util.List;

public class ComposerRepository implements GenericRep<Composer,Integer>{
    private final Connection connection;

    public ComposerRepository() {
        this.connection = ConnexionBD.getInstance();
    }
    @Override
    public void save(Composer entity) {

    }

    @Override
    public void update(Composer entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Composer findById(Integer integer) {
        return null;
    }

    @Override
    public List<Composer> findAll() {
        return List.of();
    }
}
