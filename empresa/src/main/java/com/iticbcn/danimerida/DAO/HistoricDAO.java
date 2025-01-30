package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class HistoricDAO {
    private SessionFactory sessionFactory;
    public HistoricDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void desar(Historic historic) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.persist(historic);
            transaccio.commit();
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

    public List<Historic> trobarTots() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Historic", Historic.class).list();
        }
    }
}
