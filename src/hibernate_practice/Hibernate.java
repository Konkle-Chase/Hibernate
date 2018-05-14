/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate_practice;

import Controllers.*;
import Controllers.Handler;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Models.Employee;
import Views.View;

/**
 *
 * @author Konkles
 */
//This is a simple program which uses the Hibernate ORM API and demonstrates CRUD functionality 
public class Hibernate implements Handler{
    public static SessionFactory factory;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) { 
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }  
        View menu = new View();
        menu.acView();
    }
    
    //This method adds employees to the database.
    public static void addEmployee(String firstName, String lastName, String title, Date dateHired){
        Session session = factory.openSession();
        try {               
            Transaction tx = session.beginTransaction();
            Employee em = new Employee(firstName, lastName, title, dateHired);
            session.save(em);
            tx.commit();                        
        } catch (EmptyStackException | HibernateException e) {
            System.out.println("Failed to add Employee.");
        } finally{
          session.close();
        }
    }
    
    //This method retrieves a list of all employees from the database.
    public static void getEmployees(){
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();           
            tx.commit();  
           
            List emList = new ArrayList();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                    Employee em = (Employee)iterator.next();
                    emList.add(formatEmployee(em));
            }
            View view = new View();
            EmpController controller = new EmpController(view);
            controller.updateView(emList);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally {
            session.close();
        }      
    }
    
    //This method puts employee information into a user-friendly format.
    public static String formatEmployee(Employee em){
        String record = " Student ID: " + em.getIdEmployee() 
        + "  First Name: " + em.getFirstName() 
        + "  Last Name: " + em.getLastName()
        + "  Job Title: " + em.getJobTitle() 
        + "  Date Hired: " + em.getDateHired();
        return record;
    }
    
    //This method updates an employee's information that is already stored in the database.
    public static void updateEmployee(Integer idEmployee, String firstName, String lastName, String jobTitle, Date dateHired){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee em = (Employee)session.get(Employee.class, idEmployee);
            em.setFirstName(firstName);
            em.setLastName(lastName);
            em.setJobTitle(jobTitle);
            em.setDateHired(dateHired);
            session.update(em); 
            tx.commit();
        } catch(NullPointerException|EmptyStackException|HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally{
            session.close(); 
        }
    }
    
    //This method deletes employee information from the database.
    public static void deleteEmployee(int idEmployee){       
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee em = (Employee)session.get(Employee.class, idEmployee); 
            session.delete(em); 
            tx.commit();
        } catch (IllegalArgumentException|EmptyStackException|HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally {
            session.close(); 
        }       
    }
    
    @Override
    public void engage(){
        System.out.println("Adding Employees Example");
        addEmployee("Axel", "Konkle", "Owner", new Date(118,4,5));
        addEmployee("Penny","Russell", "Office Manager", new Date(118,4,5));
        addEmployee("Yoshi", "Benson", "Temp", new Date(118,4,5));
        addEmployee("Jordy", "Hatch", "Temp", new Date(118,4,5));
        getEmployees();
        
        System.out.println("Update Employees Example");
        updateEmployee(1, "Axel", "Konkle", "President and Owner", new Date(118,4,1));
        updateEmployee(2, "Penelope","Russell","Office Manager", new Date(118,4,4));
        getEmployees();
        
        System.out.println("Delete Employees Example");
        deleteEmployee(3);
        deleteEmployee(4);
        getEmployees();
    }
}
