package Practica;

import Practica.DAOs.EjemplarDAO;
import Practica.DAOs.LibroDAO;
import Practica.DAOs.PrestamoDAO;
import Practica.DAOs.UsuarioDAO;
import Practica.DTOs.Usuario;

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
    public void lstaUsuarios() {
        usuarioDAO.
    }
}
