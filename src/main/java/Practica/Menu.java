package Practica;

import Practica.DTOs.Ejemplar;
import Practica.DTOs.Usuario;

import java.util.Scanner;

public class Menu {
    public void menus(){
        BibliotecaService bibliotecaService = new BibliotecaService();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n---- Menú Biblioteca ----");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Libro");
            System.out.println("3. Registrar Ejemplar");
            System.out.println("4. Calcular Stock Disponible");
            System.out.println("5. Registrar Préstamo");
            System.out.println("6. Registrar Devolución");
            System.out.println("7. Visualizar Préstamos");
            System.out.println("8. Verificar Penalización");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese DNI: ");
                    String dni = scanner.nextLine();
                    System.out.print("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Ingrese Tipo (NORMAL/ADMINISTRADOR): ");
                    String tipo = scanner.nextLine().toUpperCase();
                    bibliotecaService.registrarUsuario(dni, nombre, email, password, Usuario.TipoUsuario.valueOf(tipo));
                    break;
                case 2:
                    System.out.print("Ingrese ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Ingrese Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese Autor: ");
                    String autor = scanner.nextLine();
                    bibliotecaService.registrarLibro(isbn, titulo, autor);
                    break;
                case 3:
                    System.out.print("Ingrese ISBN del libro asociado: ");
                    String isbnEjemplar = scanner.nextLine();
                    System.out.print("Ingrese Estado (DISPONIBLE, PRESTADO, DAÑADO): ");
                    String estado = scanner.nextLine().toUpperCase();
                    bibliotecaService.registrarEjemplar(isbnEjemplar, Ejemplar.EstadoEjemplar.valueOf(estado));
                    break;
                case 4:
                    System.out.print("Ingrese ISBN para calcular stock: ");
                    String isbnStock = scanner.nextLine();
                    bibliotecaService.calcularStock(isbnStock);
                    break;
                case 5:
                    System.out.print("Ingrese ID del Usuario: ");
                    int usuarioIdPrestamo = scanner.nextInt();
                    System.out.print("Ingrese ID del Ejemplar: ");
                    int ejemplarIdPrestamo = scanner.nextInt();
                    bibliotecaService.registrarPrestamo(usuarioIdPrestamo, ejemplarIdPrestamo);
                    break;
                case 6:
                    System.out.print("Ingrese ID del Préstamo: ");
                    int prestamoId = scanner.nextInt();
                    bibliotecaService.registrarDevolucion(prestamoId);
                    break;
                case 7:
                    System.out.print("Ingrese ID del Usuario: ");
                    int usuarioIdVisualizar = scanner.nextInt();
                    bibliotecaService.listarPrestamos(usuarioIdVisualizar);
                    break;
                case 8:
                    System.out.print("Ingrese ID del Usuario: ");
                    int usuarioIdPenalizacion = scanner.nextInt();
                    bibliotecaService.verificarPenalizacion(usuarioIdPenalizacion);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
