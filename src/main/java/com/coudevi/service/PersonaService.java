package com.coudevi.service;

import java.util.List;

import com.coudevi.model.Persona;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersonaService {
	 private final EntityManagerFactory emf;

	    public PersonaService() {
	        this.emf = Persistence.createEntityManagerFactory("oneToOnePU");
	    }

	    public void crearPersona(Persona persona) {
	        EntityManager em = emf.createEntityManager();
	        try {
	            em.getTransaction().begin();
	            em.persist(persona);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) em.getTransaction().rollback();
	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }

	    public List<Persona> obtenerPersonas() {
	        EntityManager em = emf.createEntityManager();
	        try {
	            // Usamos fetch join para evitar LazyInitializationException
	            return em.createQuery("SELECT p FROM Persona p LEFT JOIN FETCH p.documento", Persona.class)
	                     .getResultList();
	        } finally {
	            em.close();
	        }
	    }

	    public void cerrar() {
	        emf.close();
	    }
}
