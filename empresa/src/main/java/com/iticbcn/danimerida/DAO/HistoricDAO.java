package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class HistoricDAO {
    private SessionFactory sessionFactory;
    public HistoricDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void crear(Historic historic) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.persist(historic);
            transaccio.commit();
            System.out.println("Inserció d'Historic exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public Historic trobarPerId(int id) {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.find(Historic.class, id);
        }
    }

    public void actualitzar(Historic historic) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.merge(historic);
            transaccio.commit();
            System.out.println("Actualització d'Historic exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
    
    public void eliminar(Historic historic) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.remove(historic);
            transaccio.commit();
            System.out.println("Eliminació d'Historic exitosa");
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    // Consulta HQL
    public List<Historic> trobarTots() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Historic", Historic.class).list();
        }
    }
    
}
