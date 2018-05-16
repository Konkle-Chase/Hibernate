/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import GlueClasses.Exit;
import GlueClasses.Hibernate;
import GlueClasses.JSON;
import java.util.HashMap;

/**
 *
 * @author Konkles
 */
public class ACP {
    public static HashMap<String,Handler> options = new HashMap<>();
    
    public void ACP(){
    }
    
    public static void buildMenu() {
        options.put("1", new Hibernate());
        options.put("2", new JSON());
        options.put("0", new Exit());       
    }
    
    public static void runOption(String option) {
        Handler helm = options.get(option);
        helm.engage();
    }
}
