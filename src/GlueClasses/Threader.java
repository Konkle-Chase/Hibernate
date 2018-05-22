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
            runThreads();
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(Threader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void runThreads() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("Example of Treading");
        Runnable task1 = () -> {
            try {
                System.out.println("");
                Hibernate.addEmployee("Jenni", "Hatch", "Accountant", new Long(150000));
                System.out.println("Task 1 Completed");
            } catch (Exception e) {
                throw new IllegalStateException("2task interrupted",e);
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
                System.out.println("Task 2 completed");
            } catch (Exception e) {
                throw new IllegalStateException("2task interrupted",e);
            }
        };
        
        Runnable task3 = () -> {
            try {
                System.out.println("Task 3 completed");
                Hibernate.getEmployees(message);
            } catch (Exception e) {
                throw new IllegalStateException("3task interrupted",e);
            }           
        };
        
        ExecutorService executor = Executors.newWorkStealingPool();

        Future<?> future1 = executor.submit(task1);
        while (!future1.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }
        Future<?> future2 = executor.submit(task2);
        while (!future2.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }
        executor.submit(task3);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            } 
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        
    }
}
