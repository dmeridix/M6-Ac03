package com.iticbcn.danimerida.DAO;


import com.iticbcn.danimerida.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class TascaDAO {
    private SessionFactory sessionFactory;
    public TascaDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void crear(Tasca tasca) {
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
    public void actualitzar(Tasca tasca) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.merge(tasca);
            transaccio.commit();
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
    
    public void eliminar(Tasca tasca) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.remove(tasca);
            transaccio.commit();
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
    
}
