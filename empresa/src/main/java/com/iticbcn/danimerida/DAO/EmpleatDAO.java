package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Set;

public class EmpleatDAO {
    private SessionFactory sessionFactory;
    public EmpleatDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void crear(Empleat empleat, Set<Tasca> tasques) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            empleat.setTasques(tasques);
            sessio.persist(empleat);
            transaccio.commit();
            System.out.println("Inserció de Empleat exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public Empleat trobarPerId(int id) {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.find(Empleat.class, id);
        }
    }

    public List<Empleat> trobarTots() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Empleat", Empleat.class).list();
        }
    }

    public void actualitzar(Empleat empleat) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.merge(empleat);
            transaccio.commit();
            System.out.println("Actualització de Empleat exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Empleat empleat) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.remove(empleat);
            transaccio.commit();
            System.out.println("Eliminació de Empleat exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
}