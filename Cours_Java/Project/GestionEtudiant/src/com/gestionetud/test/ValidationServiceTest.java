package com.gestionetud.test;

import com.gestionetud.entities.Composer;
import com.gestionetud.entities.Etudiant;
import com.gestionetud.entities.Matiere;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.repository.GenericRep;
import com.gestionetud.service.ComposerService;
import com.gestionetud.service.EtudiantService;
import com.gestionetud.service.MatiereService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de tests unitaires pour valider les règles métier des Services de gestion des étudiants.
 */
public class ValidationServiceTest {

    private EtudiantService etudiantService;
    private MatiereService matiereService;
    private ComposerService composerService;

    private FakeEtudiantRepository etudiantRepository;
    private FakeMatiereRepository matiereRepository;
    private FakeComposerRepository composerRepository;

    @BeforeEach
    public void setUp() {
        etudiantRepository = new FakeEtudiantRepository();
        matiereRepository = new FakeMatiereRepository();
        composerRepository = new FakeComposerRepository();

        etudiantService = new EtudiantService(etudiantRepository);
        matiereService = new MatiereService(matiereRepository);
        composerService = new ComposerService(composerRepository);
    }

    @Test
    public void testRegisterEtudiant_Success() {
        Etudiant etudiant = new Etudiant(1, "Dupont", "Jean", 20);
        etudiantService.registerEtudiant(etudiant);
        assertEquals(1, etudiantRepository.findAll().size());
        assertEquals("Dupont", etudiantRepository.findAll().get(0).getNom());
    }

    @Test
    public void testRegisterEtudiant_InvalidAge_ThrowsException() {
        Etudiant etudiantNegative = new Etudiant(1, "Dupont", "Jean", -5);
        assertThrows(ValidationException.class, () -> etudiantService.registerEtudiant(etudiantNegative));

        Etudiant etudiantTooOld = new Etudiant(2, "Dupont", "Jean", 150);
        assertThrows(ValidationException.class, () -> etudiantService.registerEtudiant(etudiantTooOld));
    }

    @Test
    public void testRegisterEtudiant_EmptyName_ThrowsException() {
        Etudiant etudiantEmptyNom = new Etudiant(1, "", "Jean", 20);
        assertThrows(ValidationException.class, () -> etudiantService.registerEtudiant(etudiantEmptyNom));

        Etudiant etudiantNullPrenom = new Etudiant(2, "Dupont", null, 20);
        assertThrows(ValidationException.class, () -> etudiantService.registerEtudiant(etudiantNullPrenom));
    }

    @Test
    public void testRegisterMatiere_Success() {
        Matiere matiere = new Matiere(1, "JAVA", "Programmation Java");
        matiereService.registerMatiere(matiere);
        assertEquals(1, matiereRepository.findAll().size());
        assertEquals("JAVA", matiereRepository.findAll().get(0).getCode());
    }

    @Test
    public void testRegisterMatiere_InvalidData_ThrowsException() {
        Matiere matiereEmptyCode = new Matiere(1, "", "Programmation Java");
        assertThrows(ValidationException.class, () -> matiereService.registerMatiere(matiereEmptyCode));

        Matiere matiereNullLibelle = new Matiere(2, "JAVA", null);
        assertThrows(ValidationException.class, () -> matiereService.registerMatiere(matiereNullLibelle));
    }

    @Test
    public void testRegisterNote_Success() {
        Etudiant etudiant = new Etudiant(1, "Dupont", "Jean", 20);
        Matiere matiere = new Matiere(1, "JAVA", "Programmation Java");
        
        etudiantRepository.save(etudiant);
        matiereRepository.save(matiere);

        Composer composer = new Composer(1, 15.5, etudiant, matiere);
        composerService.registerNote(composer);

        assertEquals(1, composerRepository.findAll().size());
        assertEquals(15.5, composerRepository.findAll().get(0).getNote());
    }

    @Test
    public void testRegisterNote_InvalidNote_ThrowsException() {
        Etudiant etudiant = new Etudiant(1, "Dupont", "Jean", 20);
        Matiere matiere = new Matiere(1, "JAVA", "Programmation Java");

        Composer noteTropBasse = new Composer(1, -1.0, etudiant, matiere);
        assertThrows(ValidationException.class, () -> composerService.registerNote(noteTropBasse));

        Composer noteTropHaute = new Composer(2, 21.0, etudiant, matiere);
        assertThrows(ValidationException.class, () -> composerService.registerNote(noteTropHaute));
    }

    @Test
    public void testCalculateMoyenne() {
        Etudiant etudiant = new Etudiant(1, "Dupont", "Jean", 20);
        Matiere m1 = new Matiere(1, "JAVA", "Programmation Java");
        Matiere m2 = new Matiere(2, "WEB", "Développement Web");

        etudiantRepository.save(etudiant);
        matiereRepository.save(m1);
        matiereRepository.save(m2);

        composerRepository.save(new Composer(1, 14.0, etudiant, m1));
        composerRepository.save(new Composer(2, 18.0, etudiant, m2));

        double moyenne = composerService.calculateMoyenne(1);
        assertEquals(16.0, moyenne, 0.001);
    }

    @Test
    public void testCalculateMoyenne_NoNotes_ReturnsZero() {
        double moyenne = composerService.calculateMoyenne(99);
        assertEquals(0.0, moyenne);
    }

    // --- Implémentations Fake des Repositories ---

    private static class FakeEtudiantRepository implements GenericRep<Etudiant, Integer> {
        private final List<Etudiant> list = new ArrayList<>();

        @Override
        public void save(Etudiant entity) {
            list.add(entity);
        }

        @Override
        public void update(Etudiant entity) {
            list.removeIf(e -> e.getIdEtudiant() == entity.getIdEtudiant());
            list.add(entity);
        }

        @Override
        public void delete(Integer id) {
            list.removeIf(e -> e.getIdEtudiant() == id);
        }

        @Override
        public Optional<Etudiant> findById(Integer id) {
            return list.stream().filter(e -> e.getIdEtudiant() == id).findFirst();
        }

        @Override
        public List<Etudiant> findAll() {
            return new ArrayList<>(list);
        }
    }

    private static class FakeMatiereRepository implements GenericRep<Matiere, Integer> {
        private final List<Matiere> list = new ArrayList<>();

        @Override
        public void save(Matiere entity) {
            list.add(entity);
        }

        @Override
        public void update(Matiere entity) {
            list.removeIf(m -> m.getIdMatiere() == entity.getIdMatiere());
            list.add(entity);
        }

        @Override
        public void delete(Integer id) {
            list.removeIf(m -> m.getIdMatiere() == id);
        }

        @Override
        public Optional<Matiere> findById(Integer id) {
            return list.stream().filter(m -> m.getIdMatiere() == id).findFirst();
        }

        @Override
        public List<Matiere> findAll() {
            return new ArrayList<>(list);
        }
    }

    private static class FakeComposerRepository implements GenericRep<Composer, Integer> {
        private final List<Composer> list = new ArrayList<>();

        @Override
        public void save(Composer entity) {
            list.add(entity);
        }

        @Override
        public void update(Composer entity) {
            list.removeIf(c -> c.getId() == entity.getId());
            list.add(entity);
        }

        @Override
        public void delete(Integer id) {
            list.removeIf(c -> c.getId() == id);
        }

        @Override
        public Optional<Composer> findById(Integer id) {
            return list.stream().filter(c -> c.getId() == id).findFirst();
        }

        @Override
        public List<Composer> findAll() {
            return new ArrayList<>(list);
        }
    }
}
