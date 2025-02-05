package com.iticbcn.danimerida.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Departament")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;

    @OneToMany(mappedBy = "departament")
    private Set<Empleat> empleats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Empleat> getEmpleats() {
        return empleats;
    }

    public void setEmpleats(Set<Empleat> empleats) {
        this.empleats = empleats;
    }

    @Override
    public String toString() {
        return "Departament{id=" + id + ", nom='" + nom + "'}";
    }

}
