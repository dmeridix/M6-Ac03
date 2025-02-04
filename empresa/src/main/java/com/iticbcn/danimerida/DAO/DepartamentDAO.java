package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.Departament;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class DepartamentDAO {
    private SessionFactory sessionFactory;
    public DepartamentDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void crearDepartament(Departament departament) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.persist(departament);
            transaccio.commit();
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }

    public Departament trobarDepartamentPerId(int id) {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.find(Departament.class, id);
        }
    }

    public List<Departament> trobarTotsDepartament() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery("from Departament", Departament.class).list();
        }
    }

    public void actualitzarDepartament(Departament departament) {
        Transaction transaccio = null;
        try (Session sessio = sessionFactory.openSession()) {
            transaccio = sessio.beginTransaction();
            sessio.merge(departament);
            transaccio.commit();
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
        } catch (Exception e) {
            if (transaccio != null) transaccio.rollback();
            e.printStackTrace();
        }
    }
}