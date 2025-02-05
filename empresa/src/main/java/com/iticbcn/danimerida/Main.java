package com.iticbcn.danimerida;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.iticbcn.danimerida.DAO.DepartamentDAO;
import com.iticbcn.danimerida.DAO.EmpleatDAO;
import com.iticbcn.danimerida.DAO.HistoricDAO;
import com.iticbcn.danimerida.DAO.TascaDAO;
import com.iticbcn.danimerida.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            boolean DispOptions = true;
        
            
            
            tx.commit();
            System.out.println("Datos guardados correctamente!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }
    public static Object seleccionarTaula(BufferedReader br, SessionFactory session) throws IOException {
        System.out.println("Selecciona la taula:");
        System.out.println("1. Departaments");
        System.out.println("2. Empleats");
        System.out.println("3. Tasques");
        System.out.println("4. Historic");
        System.out.print("Introdueix l'opció >> ");
        
        int taula = Integer.parseInt(br.readLine());
        
        return switch (taula) {
            case 1 -> new DepartamentDAO(session);
            case 2 -> new EmpleatDAO(session);
            case 3 -> new TascaDAO(session);
            case 4 -> new HistoricDAO(session);
            default -> null;            
        };
    }
    public static void seleccionarAccio(Object dao, BufferedReader br) throws IOException {
        if (dao == null) return;
        
        System.out.println("Selecciona l'acció:");
        System.out.println("1. Llistar");
        System.out.println("1. Llistar per ID");
        System.out.println("2. Inserir");
        System.out.println("3. Actualitzar");
        System.out.println("4. Eliminar");
        System.out.print("Introdueix l'opció >> ");
        
        int accio = Integer.parseInt(br.readLine());
        
        switch (accio) {
            case 1:
                dao.trobarTots();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                System.out.println("Opció no vàlida.");
        }
    }
    
}
