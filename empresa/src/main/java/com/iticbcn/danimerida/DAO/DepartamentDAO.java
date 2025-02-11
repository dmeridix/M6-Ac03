package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.Departament;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class DepartamentDAO {
    private SessionFactory sessionFactory;
    public DepartamentDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void crear(Departament departament) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.persist(departament);
            transaccio.commit();
            System.out.println("Inserció de Departament exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public Departament trobarPerId(int id) {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.find(Departament.class, id);
        }
    }

    
    public void actualitzar(Departament departament) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.merge(departament);
            transaccio.commit();
            System.out.println("Actualització de Departament exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
    
    public void eliminar(Departament departament) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.remove(departament);
            transaccio.commit();
            System.out.println("Eliminació de Departament exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
    
    // Consultes HQL
    public List<Departament> trobarTots() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Departament", Departament.class).list();
        }
    }
    
}