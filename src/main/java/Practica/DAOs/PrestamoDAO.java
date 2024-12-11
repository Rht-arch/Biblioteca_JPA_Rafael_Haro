package Practica.DAOs;

import Practica.Conexion;
import Practica.DTOs.Ejemplar;
import Practica.DTOs.Prestamo;
import Practica.DTOs.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PrestamoDAO {
    EntityManager em;
    EntityTransaction tx;

    public PrestamoDAO() {
        this.em = Conexion.getEm();
        this.tx = em.getTransaction();
    }

    public void registrarPrestamo(int usuarioId, int ejemplarId) {
        try {
            tx.begin();

            // Buscar usuario y ejemplar
            Usuario usuario = em.find(Usuario.class, usuarioId);
            Ejemplar ejemplar = em.find(Ejemplar.class, ejemplarId);

            // Validar usuario y ejemplar existentes
            if (usuario == null || ejemplar == null) {
                throw new IllegalArgumentException("Usuario o ejemplar no encontrado.");
            }

            // Validar penalización activa
            LocalDate ahora = LocalDate.now();
            if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(ahora)) {
                throw new IllegalStateException("El usuario tiene una penalización activa.");
            }

            // Validar préstamos activos
            Long prestamosActivos = (Long) em.createQuery(
                            "SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.fechaDevolucion IS NULL")
                    .setParameter("usuarioId", usuarioId)
                    .getSingleResult();
            if (prestamosActivos >= 3) {
                throw new IllegalStateException("El usuario ya tiene 3 préstamos activos.");
            }

            // Validar estado del ejemplar
            if (!ejemplar.getEstado().equals(Ejemplar.EstadoEjemplar.DISPONIBLE)) {
                throw new IllegalStateException("El ejemplar no está disponible para préstamo.");
            }

            // Registrar préstamo
            Prestamo prestamo = new Prestamo();
            prestamo.setUsuario(usuario);
            prestamo.setEjemplar(ejemplar);
            prestamo.setFechaInicio(Date.valueOf(LocalDate.now()).toLocalDate());
            prestamo.setFechaDevolucion(null);

            // Cambiar estado del ejemplar
            ejemplar.setEstado(String.valueOf(Ejemplar.EstadoEjemplar.PRESTADO));

            // Persistir cambios
            em.persist(prestamo);
            em.merge(ejemplar);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public void registrarDevolucion(int prestamoId) {
        try {
            tx.begin();

            // Buscar el préstamo
            Prestamo prestamo = em.find(Prestamo.class, prestamoId);
            if (prestamo == null) {
                throw new IllegalArgumentException("El préstamo no existe.");
            }

            // Registrar fecha de devolución
            prestamo.setFechaDevolucion(Date.valueOf(LocalDate.now()).toLocalDate());

            // Cambiar estado del ejemplar a DISPONIBLE
            Ejemplar ejemplar = prestamo.getEjemplar();
            ejemplar.setEstado(String.valueOf(Ejemplar.EstadoEjemplar.DISPONIBLE));

            // Persistir cambios
            em.merge(prestamo);
            em.merge(ejemplar);

            tx.commit();

            // Validar si la devolución fue fuera de plazo
            LocalDate fechaLimite = prestamo.getFechaInicio().plusDays(15);
            if (LocalDate.now().isAfter(fechaLimite)) {
                registrarPenalizacion(prestamo);
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    private void registrarPenalizacion(Prestamo prestamo) {
        try {
            tx.begin();

            Usuario usuario = prestamo.getUsuario();
            int diasPenalizacion = 15;

            // Calcular nueva penalización
            LocalDate penalizacionActual = usuario.getPenalizacionHasta() == null
                    ? LocalDate.now()
                    : usuario.getPenalizacionHasta();
            LocalDate nuevaPenalizacion = penalizacionActual.plusDays(diasPenalizacion);
            usuario.setPenalizacionHasta(nuevaPenalizacion);

            // Persistir cambios
            em.merge(usuario);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    public ArrayList<Prestamo> getTodosPrestamos() {
        tx.begin();
        ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) em.createQuery("select p from Prestamo p", Prestamo.class).getResultList();
        tx.commit();
        return prestamos;
    }
    public ArrayList<Prestamo> getPrestamosPorId(int usuarioId) {
        tx.begin();
        ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId", Prestamo.class).setParameter("usuarioId", usuarioId).getResultList();
        tx.commit();
        return prestamos;
    }
}
