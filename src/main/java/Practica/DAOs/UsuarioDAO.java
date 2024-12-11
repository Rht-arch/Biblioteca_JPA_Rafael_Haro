package Practica.DAOs;

import Practica.Conexion;
import Practica.DTOs.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class UsuarioDAO {
    EntityManager em;
    EntityTransaction tx;

    public UsuarioDAO() {
        this.em= Conexion.getEm();
        this.tx=em.getTransaction();
    }
    public void insertarUsuario(Usuario usuario) {
        tx.begin();
        em.persist(usuario);
        tx.commit();
    }
    public boolean tienePenalizacionActiva(int usuarioId) {
        try {
            Usuario usuario = em.find(Usuario.class, usuarioId);
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no existe.");
            }

            LocalDate ahora = LocalDate.now();
            return usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(ahora);
        } finally {
            em.close();
        }
    }

}
