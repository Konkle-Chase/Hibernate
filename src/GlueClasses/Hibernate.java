/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlueClasses;

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
import java.math.BigDecimal;
import org.hibernate.Query;

/**
 *
 * @author Konkles
 */
//This is a simple program which uses the Hibernate ORM API and demonstrates CRUD functionality 
public class Hibernate implements Handler{
    public static SessionFactory factory;
    String message = "";
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
    public void addEmployee(String firstName, String lastName, String jobTitle, Long salary){
        Session session = factory.openSession();
        boolean exists = checkExistence(firstName, lastName, jobTitle);
        try {
            if(exists == false){
                Transaction tx = session.beginTransaction();
                Employee em = new Employee(firstName, lastName, jobTitle, salary);
                session.save(em);
                tx.commit();
            } else{
                throw new EmptyStackException();
            }
        } catch (EmptyStackException | HibernateException e) {
            System.out.println("Failed to add Employee.");
        } finally{
          session.close();
        }
    }
    
    //This method retrieves a list of all employees from the database.
    public List getEmployees(String message){
        Session session = factory.openSession();
        Transaction tx = null;
        List emList = new ArrayList();
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();           
            tx.commit(); 
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                    Employee em = (Employee)iterator.next();
                    emList.add(formatEmployee(em));
            }
            View view = new View();
            EmpController controller = new EmpController(view);
            controller.updateView(emList, message);
            return employees;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally {
            session.close();
        }      
        return emList;
    }
    
    //This method puts employee information into a user-friendly format.
    public String formatEmployee(Employee em){
        String record = " Employee ID: " + em.getIdEmployee() 
        + "  First Name: " + em.getFirstName() 
        + "  Last Name: " + em.getLastName()
        + "  Job Title: " + em.getJobTitle() 
        + "  Salary: " + em.getSalary();
        return record;
    }
    
    //This method updates an employee's information that is already stored in the database.
    public void updateEmployee(Integer idEmployee, String firstName, String lastName, String jobTitle, Long salary){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee em = (Employee)session.get(Employee.class, idEmployee);
            em.setFirstName(firstName);
            em.setLastName(lastName);
            em.setJobTitle(jobTitle);
            em.setSalary(salary);
            session.update(em); 
            tx.commit();
        } catch(NullPointerException|EmptyStackException|HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally{
            session.close(); 
        }
    }
    
    //This method deletes employee information from the database.
    public void deleteEmployee(int idEmployee){       
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
    
    public boolean checkExistence(String firstName, String lastName, String jobTitle) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean exists = false;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Employee E WHERE E.firstName = :firstName AND E.lastName = :lastName");
            query.setParameter("firstName",firstName);
            query.setParameter("lastName", lastName);
//            query.setParameter("jobTitle", jobTitle);
//            query.setParameter("salary", salary);
            List result = query.list();
            tx.commit();
            if(!result.isEmpty())
                exists = true;
            return exists;
        } catch(EmptyStackException | HibernateException e){
          if (tx!=null) tx.rollback();
          exists = false;
          return exists;
        } finally {
            session.close();
        }
    }
    @Override
    public void engage(){
        message = "Adding Employees Example";
        addEmployee("Axel", "Konkle", "Owner", new Long(200000));
        addEmployee("Penny","Russell", "Office Manager", new Long(59000));
        addEmployee("Yoshi", "Benson", "Temp", new Long(30000));
        addEmployee("Jordy", "Hatch", "Temp", new Long(30000));
        getEmployees(message);
        
        message = "Updating Employees Example";
        updateEmployee(1, "Axel", "Konkle", "President and Owner", new Long(300000));
        updateEmployee(2, "Penelope","Russell","Office Manager", new Long(60000));
        getEmployees(message);
        
        message = "Deleting Employees Example";
        deleteEmployee(3);
        deleteEmployee(4);
        getEmployees(message);
    }
}
