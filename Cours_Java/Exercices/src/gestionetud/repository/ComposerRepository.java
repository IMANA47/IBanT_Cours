package src.gestionetud.repository;

import src.gestionetud.entities.Composer;

import java.util.List;

public class ComposerRepository implements GenericRep<Composer,Integer>{


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
