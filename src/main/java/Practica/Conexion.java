package Practica;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    private static EntityManagerFactory managerFactory;

    // Método para obtener o inicializar el EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        if (managerFactory == null) {
            managerFactory = Persistence.createEntityManagerFactory("biblioteca");
        }
        return managerFactory;
    }

    // Método para obtener un EntityManager
    public static EntityManager getEm() {
        return getEntityManagerFactory().createEntityManager();
    }

    // Método para cerrar el EntityManagerFactory
    public static void cerrar() {
        if (managerFactory != null && managerFactory.isOpen()) {
            managerFactory.close();
        }
    }
}
