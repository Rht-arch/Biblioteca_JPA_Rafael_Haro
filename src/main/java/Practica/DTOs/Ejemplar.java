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
    @Column(name = "estado")
    private EstadoEjemplar estado = EstadoEjemplar.DISPONIBLE;

    @OneToMany(mappedBy = "ejemplar")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();
    public enum EstadoEjemplar {
        DISPONIBLE,PRESTADO,DAÑADO
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

}