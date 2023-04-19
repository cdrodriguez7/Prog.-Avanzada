package ec.edu.utpl.computacion.pa.controller;

import ec.edu.utpl.computacion.pa.model.RegisterDTO;
import ec.edu.utpl.computacion.pa.model.Registration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationController {
    private static EntityManager em = null;

    public RegistrationController() {
        getEm("pu-pa");
    }

    private void getEm(String puName) {
        if(em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
            em = emf.createEntityManager();
        }
    }

    public boolean addRegistration(RegisterDTO register) {

        Registration newRegistration = new Registration(register.id(), register.firstName(), register.lastName(), register.age());
        em.getTransaction().begin();
        em.persist(newRegistration);
        em.flush();
        em.getTransaction().commit();

        return true;
    }

    public void close() {
        em.close();
    }
}
