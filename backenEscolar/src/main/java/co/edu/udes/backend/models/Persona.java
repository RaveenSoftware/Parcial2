package co.edu.udes.backend.models;

import jakarta.persistence.*;

@MappedSuperclass
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    public Persona() {
    }

    public Persona(String nombre, String email, String numeroTelefono) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}
