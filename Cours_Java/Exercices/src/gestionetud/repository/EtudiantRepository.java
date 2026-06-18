package src.gestionetud.repository;

import src.gestionetud.entities.Etudiant;

import java.util.List;

public class EtudiantRepository implements GenericRep<Etudiant,Integer> {


    @Override
    public void save(Etudiant entity) {

    }

    @Override
    public void update(Etudiant entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Etudiant findById(Integer integer) {
        return null;
    }

    @Override
    public List<Etudiant> findAll() {
        return List.of();
    }
}
