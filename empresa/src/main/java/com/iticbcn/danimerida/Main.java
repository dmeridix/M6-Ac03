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
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            boolean continuarPrograma = true;
            while (continuarPrograma) {

                Object dao = seleccionarTaula(br, sessionFactory);
                if (dao == null) {
                    System.out.println("Opció no vàlida. Tornant al menú...");
                    continue;
                }

                boolean continuarConTabla = true;
                while (continuarConTabla) {
                    seleccionarAccio(dao, br);

                    System.out.print("Vols fer una altra acció sobre la mateixa taula? (s/n) >> ");
                    String respuestaAccion = br.readLine().trim().toLowerCase();
                    continuarConTabla = respuestaAccion.equals("s");
                }

                System.out.print("Vols canviar de taula o sortir del programa? (t = nova taula, s = sortir) >> ");
                String respuestaTabla = br.readLine().trim().toLowerCase();
                if (respuestaTabla.equals("s")) {
                    continuarPrograma = false;
                }
            }
            System.out.println("Programa finalitzat. Adéu!");

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
        System.out.println("2. Llistar per ID");
        System.out.println("3. Inserir");
        System.out.println("4. Actualitzar");
        System.out.println("5. Eliminar");
        System.out.print("Introdueix l'opció >> ");
        
        int accio = Integer.parseInt(br.readLine());
        

        switch (accio) {
            case 1:
                if (dao instanceof DepartamentDAO) {
                    ((DepartamentDAO) dao).trobarTots().forEach(System.out::println);
                } else if (dao instanceof EmpleatDAO) {
                    ((EmpleatDAO) dao).trobarTots().forEach(System.out::println);
                } else if (dao instanceof TascaDAO) {
                    ((TascaDAO) dao).trobarTots().forEach(System.out::println);
                } else if (dao instanceof HistoricDAO) {
                    ((HistoricDAO) dao).trobarTots().forEach(System.out::println);
                }
                break;
            case 2:
                System.out.print("Introdueix ID >> ");
                int id = Integer.parseInt(br.readLine());
    
                if (dao instanceof DepartamentDAO) {
                    System.out.println(((DepartamentDAO) dao).trobarPerId(id));
                } else if (dao instanceof EmpleatDAO) {
                    System.out.println(((EmpleatDAO) dao).trobarPerId(id));
                } else if (dao instanceof TascaDAO) {
                    System.out.println(((TascaDAO) dao).trobarPerId(id));
                } else if (dao instanceof HistoricDAO) {
                    System.out.println(((HistoricDAO) dao).trobarPerId(id));
                }
                break;
            case 3:
                insertarEntidad(dao, br);
                break;
            case 4:
                actualizarEntidad(dao, br);
                break;
            case 5:
                eliminarEntidad(dao, br);
                break;
            default:
                System.out.println("Opció no vàlida.");
        }
    }
    public static void insertarEntidad(Object dao, BufferedReader br) throws IOException {
        if (dao instanceof DepartamentDAO) {
            System.out.print("Introdueix el nom del departament >> ");
            String nom = br.readLine();
            Departament departament = new Departament();
            departament.setNom(nom);
            ((DepartamentDAO) dao).crear(departament);
        } else if (dao instanceof EmpleatDAO) {
            System.out.print("Introdueix el nom de l'empleat >> ");
            String nom = br.readLine();
            System.out.print("Introdueix l'ID del departament >> ");
            int departamentId = Integer.parseInt(br.readLine());
    
            Departament departament = new Departament();
            departament.setId(departamentId);
    
            Empleat empleat = new Empleat();
            empleat.setNom(nom);
            empleat.setDepartament(departament);
    
            ((EmpleatDAO) dao).crear(empleat);
        } else if (dao instanceof TascaDAO) {
            System.out.print("Introdueix la descripció de la tasca >> ");
            String descripcio = br.readLine();
            Tasca tasca = new Tasca();
            tasca.setDescripcio(descripcio);
            ((TascaDAO) dao).crear(tasca);
        } else if (dao instanceof HistoricDAO) {
            System.out.print("Introdueix el comentari >> ");
            String comentari = br.readLine();
            System.out.print("Introdueix l'ID de la tasca >> ");
            int tascaId = Integer.parseInt(br.readLine());
    
            Tasca tasca = new Tasca();
            tasca.setId(tascaId);
    
            Historic historic = new Historic();
            historic.setComentari(comentari);
            historic.setTasca(tasca);
    
            ((HistoricDAO) dao).crear(historic);
        } else {
            System.out.println("DAO no reconegut.");
        }
    }

    public static void actualizarEntidad(Object dao, BufferedReader br) throws IOException {
        System.out.print("Introdueix ID de l'entitat a actualitzar >> ");
        int id = Integer.parseInt(br.readLine());
    
        if (dao instanceof DepartamentDAO) {
            Departament departament = ((DepartamentDAO) dao).trobarPerId(id);
            if (departament != null) {
                System.out.print("Introdueix el nou nom del departament >> ");
                String nom = br.readLine();
                departament.setNom(nom);
                ((DepartamentDAO) dao).actualitzar(departament);
            }
        } else if (dao instanceof EmpleatDAO) {
            Empleat empleat = ((EmpleatDAO) dao).trobarPerId(id);
            if (empleat != null) {
                System.out.print("Introdueix el nou nom de l'empleat >> ");
                String nom = br.readLine();
                empleat.setNom(nom);
                System.out.print("Introdueix el nou ID del departament >> ");
                int departamentId = Integer.parseInt(br.readLine());
                Departament departament = new Departament();
                departament.setId(departamentId);
                empleat.setDepartament(departament);
                ((EmpleatDAO) dao).actualitzar(empleat);
            }
        } else if (dao instanceof TascaDAO) {
            Tasca tasca = ((TascaDAO) dao).trobarPerId(id);
            if (tasca != null) {
                System.out.print("Introdueix la nova descripció de la tasca >> ");
                String descripcio = br.readLine();
                tasca.setDescripcio(descripcio);
                ((TascaDAO) dao).actualitzar(tasca);
            }
        } else if (dao instanceof HistoricDAO) {
            Historic historic = ((HistoricDAO) dao).trobarPerId(id);
            if (historic != null) {
                System.out.print("Introdueix el nou comentari >> ");
                String comentari = br.readLine();
                historic.setComentari(comentari);
                ((HistoricDAO) dao).actualitzar(historic);
            }
        } else {
            System.out.println("DAO no reconegut.");
        }
    }

    public static void eliminarEntidad(Object dao, BufferedReader br) throws IOException {
        System.out.print("Introdueix ID de l'entitat a eliminar >> ");
        int id = Integer.parseInt(br.readLine());
    
        if (dao instanceof DepartamentDAO) {
            Departament departament = ((DepartamentDAO) dao).trobarPerId(id);
            if (departament != null) {
                ((DepartamentDAO) dao).eliminar(departament);
            }
        } else if (dao instanceof EmpleatDAO) {
            Empleat empleat = ((EmpleatDAO) dao).trobarPerId(id);
            if (empleat != null) {
                ((EmpleatDAO) dao).eliminar(empleat);
            }
        } else if (dao instanceof TascaDAO) {
            Tasca tasca = ((TascaDAO) dao).trobarPerId(id);
            if (tasca != null) {
                ((TascaDAO) dao).eliminar(tasca);
            }
        } else if (dao instanceof HistoricDAO) {
            Historic historic = ((HistoricDAO) dao).trobarPerId(id);
            if (historic != null) {
                ((HistoricDAO) dao).eliminar(historic);
            }
        } else {
            System.out.println("DAO no reconegut.");
        }
    }
    
     
}
