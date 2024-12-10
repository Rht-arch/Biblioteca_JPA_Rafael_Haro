import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexión {
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
}
