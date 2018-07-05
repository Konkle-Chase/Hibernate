/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlueClasses;

import Controllers.Handler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konkles
 */
public class Threader extends Hibernate implements Handler {    
    public void Threader() {    
    }
    
    @Override
    public void engage(){
        try {
            runTreads();
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(Threader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void runTreads() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("Example of Treading");
        Runnable task1 = () -> {
            try {
                System.out.println("");
                Hibernate.addEmployee("Jenni", "Hatch", "Accountant", new Long(150000));
                Hibernate.getEmployees(message);
                System.out.println("Task 1 Completed: Added employee");
            } catch (Exception ex) {
                throw new IllegalStateException("2task interrupted",ex);
            }
        };
        
        Runnable task2 = () -> {
            try {
                String firstName = "Jenni";
                String lastName = "Hatch";
                String jobTitle = "CPA";
                Long salary = new Long(150000);
                Object idEmployee = getEmployeeId(firstName, lastName);
                
                Hibernate.updateEmployee((Integer) idEmployee, firstName, lastName, jobTitle, salary);
                Hibernate.getEmployees(message);
                System.out.println("Task 2 Completed: Updated employee");
            } catch (IllegalStateException ex) {
                throw new IllegalStateException("2task interrupted",ex);
            }
        };
        
        Runnable task3 = () -> {
            try {
                String firstName = "Jenni";
                String lastName = "Hatch";
                Object idEmployee = getEmployeeId(firstName, lastName);
                
                Hibernate.deleteEmployee((int) idEmployee);
                Hibernate.getEmployees(message);
                System.out.println("Task 3 Completed: Deleted employee");
            } catch (Exception ex) {
                throw new IllegalStateException("3task interrupted",ex);
            }           
        };
        
        ExecutorService executor = Executors.newWorkStealingPool();

        Future<?> future1 = executor.submit(task1);
        while (!future1.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }
        Future<?> future2 = executor.submit(task2);
        while(!future2.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }
        Future <?> future3 = executor.submit(task3);
        while(!future3.isDone()){
            TimeUnit.SECONDS.sleep(1);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            } 
        } catch (InterruptedException ex) {
            executor.shutdownNow();
        }
        
    }
}
