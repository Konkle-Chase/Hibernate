/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ACP;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Konkles
 */
public class View {
    
public void acView(){
        ACP.buildMenu();
        String option = "";
        String menu = "------------------------------------------\n"
                    + "| Student Records Menu Options           |\n"
                    + "------------------------------------------\n"
                    + "| 1 - Run Hibernate                      |\n"
                    + "| 2 - Read From and Write To JSON        |\n" 
                    + "| 0 - Exit Application                   |\n" 
                    + "------------------------------------------\n\n"
                    + "Enter menu option: ";
        while(!option.equals("0")){
            System.out.print(menu);
            Scanner reader = new Scanner(System.in);            
            option = reader.nextLine();
            Set<String> key = ACP.options.keySet();
            if (key.contains(option)) {                
                ACP.runOption(option);
            } else {
                System.out.println("Invalid Entry. Please select the number that corresponds to the desired function.");
            }
            
        } 
    }
    
    public void printEmployees(List emList, String message){        
        System.out.println(message);
        for (Iterator iterator = emList.iterator(); iterator.hasNext();){
               String record = (String)iterator.next();
               System.out.println(record);
        }
        System.out.println();
    }
    
    public void printMessage(String message){
        System.out.println(message + "\n");
    }
    
    public void printExitMessage(String exitMessage){
        System.out.println(exitMessage);
    }
}
