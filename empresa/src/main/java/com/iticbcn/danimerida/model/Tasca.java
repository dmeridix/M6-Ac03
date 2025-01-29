package com.iticbcn.danimerida.model;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Tasca")
public class Tasca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcio;
    
    @ManyToMany(mappedBy = "tasques")
    private Set<Empleat> empleats;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescripcio() { return descripcio; }
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }
    public Set<Empleat> getEmpleats() { return empleats; }
    public void setEmpleats(Set<Empleat> empleats) { this.empleats = empleats; }
}