package com.gestionetud.repository;

import com.gestionetud.entities.Matiere;

import java.util.List;

public class MatiereRepository implements GenericRep<Matiere,Integer> {


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
        return List.of();
    }
}
