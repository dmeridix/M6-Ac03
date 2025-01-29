package com.iticbcn.danimerida;

import org.hibernate.SessionFactory;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.iticbcn.danimerida.model.*;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            // Crear i guardar un departament
            Departament departament = new Departament();
            departament.setNom("IT");
            session.persist(departament);

            // Crear i guardar un empleat
            Empleat empleat = new Empleat();
            empleat.setNom("Joan Perez");
            empleat.setDepartament(departament);
            session.persist(empleat);

            // Crear i guardar una tasca
            Tasca tasca = new Tasca();
            tasca.setDescripcio("Desenvolupar aplicació");
            session.persist(tasca);

            // Crear i guardar un històric
            Historic historic = new Historic();
            historic.setTasca(tasca);
            historic.setComentari("Primera versió completada");
            session.persist(historic);

            tx.commit();
            System.out.println("Dades guardades correctament!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }
}
