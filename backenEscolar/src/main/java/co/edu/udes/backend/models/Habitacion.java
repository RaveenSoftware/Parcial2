package co.edu.udes.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "precio")
    private double precio;

    @Column(name = "capacidad")
    private int capacidad;

    public Habitacion() {
    }

    public Habitacion(String tipo, double precio, int capacidad) {
        super();
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
