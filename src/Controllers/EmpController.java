/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Employee;
import Views.View;
import java.util.List;

/**
 *
 * @author Konkles
 */
public class EmpController {
    private Employee em;
    private View view;
     
    public EmpController(Employee em, View view){
      this.em = em;
      this.view = view;
    }
    public EmpController(View view){
        this.view = view;
    }
    
    public void setIdEmployee(Integer idEmployee){
        em.setIdEmployee(idEmployee);
    }
    public Integer getIdEmployee(){
        return em.getIdEmployee();
    }
    
    public void setEmployeeFirstName(String firstName){
        em.setFirstName(firstName);
    }
    public String getEmployeeFirstName(){
        return em.getFirstName();
    }
    
    public void setEmployeeLastName(String lastName){
        em.setLastName(lastName);
    }
    public String getEmployeeLastName(){
        return em.getLastName();
    }
    
    public void setEmployeeJobTitle(String major){
        em.setJobTitle(major);
    }
    public String getEmployeeMajor(){
        return em.getJobTitle();
    }
    
    public void setEmployeeSalary(Long salary){
        em.setSalary(salary);
    }
    public Long getEmployeeSalary(){
        return em.getSalary();
    }
    
    
    public void updateView(List emList, String message){				        
        view.printEmployees(emList, message);
    }
    
    public void updateView(String message){				        
        view.printMessage(message);
    }
    
    public void updateExitView(String exitMessage){
        view.printExitMessage(exitMessage);
    }

}


