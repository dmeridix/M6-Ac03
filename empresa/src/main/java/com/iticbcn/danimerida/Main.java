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

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }
}
