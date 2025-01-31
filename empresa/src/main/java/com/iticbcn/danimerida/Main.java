package com.iticbcn.danimerida;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.iticbcn.danimerida.model.*;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            
            // Crear un departamento
            Departament departament = new Departament();
            departament.setNom("Tus");
            session.persist(departament);
            
            // Crear empleados
            Empleat empleat1 = new Empleat();
            empleat1.setNom("Anna Perez");
            empleat1.setDepartament(departament);
            session.persist(empleat1);
            
            Empleat empleat2 = new Empleat();
            empleat2.setNom("Joan Martí");
            empleat2.setDepartament(departament);
            session.persist(empleat2);
            
            // Crear una tarea
            Tasca tasca = new Tasca();
            tasca.setDescripcio("Desenvolupar aplicació");
            
            // Asignar empleados a la tarea
            Set<Empleat> empleats = new HashSet<>();
            empleats.add(empleat1);
            empleats.add(empleat2);
            tasca.setEmpleats(empleats);
            
            session.persist(tasca);
            
            // Crear un histórico
            Historic historic = new Historic();
            historic.setTasca(tasca);
            historic.setComentari("Inici de desenvolupament");
            session.persist(historic);
            
            tx.commit();
            System.out.println("Datos guardados correctamente!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }
}