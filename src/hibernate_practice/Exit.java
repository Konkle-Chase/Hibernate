/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate_practice;

import Controllers.EmpController;
import Controllers.Handler;
import Views.View;
import static hibernate_practice.Hibernate.factory;

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
        
        controller.updateView(exitMessage);
        factory.close();
    }
}
