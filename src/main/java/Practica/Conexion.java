package Practica;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    static EntityManagerFactory managerFactory;
    static EntityManager em;

    public static EntityManagerFactory getEntityManager() {
        if (managerFactory == null) {
            return managerFactory;
        }else{
            managerFactory= Persistence.createEntityManagerFactory("biblioteca");
            return managerFactory;
        }
    }
    public static void cerrar(){
        if(managerFactory!=null && managerFactory.isOpen()){
            managerFactory.close();

        }
    }

    public static EntityManager getEm() {
        if(em!=null){
            return em;
        }else{
            getEntityManager();
            em = managerFactory.createEntityManager();
            return em;
        }
    }
}
