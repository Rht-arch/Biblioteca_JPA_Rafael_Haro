package Practica.DAOs;

import Practica.Conexion;
import Practica.DTOs.Ejemplar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;

public class EjemplarDAO {
    EntityManager em;
    EntityTransaction tx;

    public EjemplarDAO() {
        this.em= Conexion.getEm();
        this.tx=em.getTransaction();
    }
    public int insertEjemplar(Ejemplar e){
        tx.begin();
        em.persist(e);
        tx.commit();
        return 1;
    }
    public int calcularStockDisponible(String isbn) {
        try {
            // Consulta para contar los ejemplares disponibles de un libro específico
            String jpql = "SELECT COUNT(e) FROM Ejemplar e WHERE e.isbn.isbn = :isbn AND e.estado = 'DISPONIBLE'";
            Query query = em.createQuery(jpql);
            query.setParameter("isbn", isbn);
            // Convertir el resultado al tipo entero
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
