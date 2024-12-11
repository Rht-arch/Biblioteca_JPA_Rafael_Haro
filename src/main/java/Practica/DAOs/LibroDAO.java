package Practica.DAOs;
import  Practica.Conexion;
import Practica.DTOs.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class LibroDAO {
    EntityManager em;
    EntityTransaction tx;

    public LibroDAO() {
        this.em= Conexion.getEm();
        this.tx=em.getTransaction();
    }
    public int insertarLibro(Libro libro) {
        tx.begin();
        em.persist(libro);
        tx.commit();
        return 1;
    }
    public Libro buscarLibro(String isbn) {
        tx.begin();
        Libro libro = em.find(Libro.class, isbn);
        tx.commit();
        return libro;
    }
    public ArrayList<Libro> consultarLibro() {
        tx.begin();
        ArrayList<Libro> libros = (ArrayList<Libro>) em.createQuery("from Libro").getResultList();
        tx.commit();
        return libros;
    }
}
