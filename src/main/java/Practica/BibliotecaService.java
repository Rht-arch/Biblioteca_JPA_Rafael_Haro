package Practica;

import Practica.DAOs.EjemplarDAO;
import Practica.DAOs.LibroDAO;
import Practica.DAOs.PrestamoDAO;
import Practica.DAOs.UsuarioDAO;
import Practica.DTOs.Ejemplar;
import Practica.DTOs.Libro;
import Practica.DTOs.Prestamo;
import Practica.DTOs.Usuario;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class BibliotecaService {
    private UsuarioDAO usuarioDAO;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;
    private EjemplarDAO ejemplarDAO;

    public BibliotecaService() {
        this.usuarioDAO = new UsuarioDAO();
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.ejemplarDAO = new EjemplarDAO();
    }

    public void registrarUsuario(String dni, String nombre,String email, String password, Usuario.TipoUsuario tipoUsuario) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTipo(tipoUsuario.toString());
        usuarioDAO.insertarUsuario(usuario);
        System.out.println("Usuario registrado exitosamente");
    }
    public void registrarPrestamo(int usuarioId, int prestamoId) {
        try{
            Prestamo p = new Prestamo();
            p.setFechaInicio(Date.valueOf(LocalDate.now()).toLocalDate());
            prestamoDAO.registrarPrestamo(usuarioId, prestamoId);
            System.out.println("Prestamo registrado exitosamente con fecha de inicio : "+ p.getFechaInicio());
        }catch(Exception e){
            System.out.println("Error al registrar el prestamo");
        }
    }
    public void registrarLibro(String isbn13, String titulo, String autor) {
        try {
            Libro libro = new Libro();
            libro.setIsbn(isbn13);
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libroDAO.insertarLibro(libro);
            System.out.println("Libro registrado exitosamente: " + titulo);
        } catch (Exception e) {
            System.out.println("Error al registrar el libro: " + e.getMessage());
        }
    }
    public void registrarEjemplar(String isbn, Ejemplar.EstadoEjemplar estadoEjemplar) {
        Ejemplar e = new Ejemplar();
        Libro libro = libroDAO.buscarLibro(isbn);
        e.setIsbn(libro);
        e.setEstado(estadoEjemplar.toString());
        ejemplarDAO.insertEjemplar(e);
        System.out.println("Ejemplar registrado exitosamente con isbn : "+ libro.getIsbn());
    }
    public void registrarDevolucion(int prestamoId) {
        try {
            prestamoDAO.registrarDevolucion(prestamoId);
            System.out.println("Devolución registrada correctamente");
        } catch (Exception e) {
            System.out.println("Error al registrar la devolución: " + e.getMessage());
        }
    }
    public void calcularStock(String isbn){
        int stock = ejemplarDAO.calcularStockDisponible(isbn);
        System.out.println("Stock para el isbn "+isbn+" : "+stock);
    }
    public void listarPrestamos(int usuarioId){
        Usuario u = usuarioDAO.buscarUsuario(usuarioId);
        if(u == null){
            System.out.println("Usuario no encontrado");
        }
        ArrayList<Prestamo> prestamos;
        if(u.getTipo().equals(Usuario.TipoUsuario.administrador.toString())){
            prestamos = prestamoDAO.getTodosPrestamos();
            System.out.println("Prestamos de la biblioteca : ");
        }else {
            prestamos = prestamoDAO.getPrestamosPorId(usuarioId);
            System.out.println("Prestamos del usuario : ");
        }
        for(Prestamo p : prestamos){
            System.out.println(p.toString());
        }
    }
    public void verificarPenalizacion(int usuarioId) {
        boolean penalizado = usuarioDAO.tienePenalizacionActiva(usuarioId);
        if (penalizado) {
            System.out.println("El usuario tiene una penalización activa y no puede realizar nuevos préstamos.");
        } else {
            System.out.println("El usuario está habilitado para realizar préstamos.");
        }
    }

}
