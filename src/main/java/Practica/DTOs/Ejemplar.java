package Practica.DTOs;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Ejemplar {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    private Libro isbn;

    @ColumnDefault("'Disponible'")
    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEjemplar estado = EstadoEjemplar.Disponible;

    @OneToMany(mappedBy = "ejemplar")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();

    public enum EstadoEjemplar {
        Disponible, Prestado, Dañado
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getIsbn() {
        return isbn;
    }

    public void setIsbn(Libro isbn) {
        this.isbn = isbn;
    }

    public EstadoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = EstadoEjemplar.valueOf(estado);
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "id=" + id +
                ", estado=" + estado +
                ", libroIsbn='" + (isbn != null ? isbn.getIsbn() : "N/A") + '\'' +
                '}';
    }
}