/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlueClasses;

import Controllers.EmpController;
import Controllers.Handler;
import Views.View;
import static GlueClasses.Hibernate.factory;

/**
 *
 * @author Konkles
 */
public class Exit implements Handler {
    @Override
    public void engage() {
        String exitMessage = "Factory Closed";
        View view = new View();
        EmpController controller = new EmpController(view);
        
        controller.updateExitView(exitMessage);
        factory.close();
    }
}
