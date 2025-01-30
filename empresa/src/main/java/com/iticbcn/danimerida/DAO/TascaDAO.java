package com.iticbcn.danimerida.DAO;


import com.iticbcn.danimerida.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class TascaDAO {
    private SessionFactory sessionFactory;
    public TascaDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void desar(Tasca tasca) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.persist(tasca);
            transaccio.commit();
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public Tasca trobarPerId(int id) {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.find(Tasca.class, id);
        }
    }

    public List<Tasca> trobarTots() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Tasca", Tasca.class).list();
        }
    }
}
