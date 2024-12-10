package Practica.DAOs;
import  Practica.Conexion;
import Practica.DTOs.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
}
