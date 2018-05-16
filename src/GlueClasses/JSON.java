/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlueClasses;

import Controllers.EmpController;
import Controllers.Handler;
import static GlueClasses.Hibernate.factory;
import Models.Employee;
import Views.View;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Konkles
 */
public class JSON extends Hibernate implements Handler{
    static String message = "Action failed.";
    @Override
    public void engage(){
        View view = new View();
        EmpController controller = new EmpController(view);
        String filePath = "/Users/Konkles/Desktop/CIT_360/CIT360/src/GlueClasses/JsonWriteTo.json";
        
        getEmployeesFromJson();
        List emList = getEmployees(message);
        JSONArray jArray = new JSONArray();
        for (Iterator iterator = emList.iterator(); iterator.hasNext();){
                Employee em = (Employee) iterator.next();
                JSONObject jObject = constructJsonObject(em);
                jArray.add(jObject);
        }
        try(FileWriter file = new FileWriter(filePath)){
            file.write(jArray.toJSONString());
            file.flush();
            file.close();
        } catch(IOException ex){
        }              
        controller.updateExitView(message);
    }
    
    //This method seeds the student database by reading a JSONArray from a JSON file.
    public static void getEmployeesFromJson(){
        String filePath = "/Users/Konkles/Desktop/CIT_360/CIT360/src/GlueClasses/JsonReadFrom.json";
        try(FileReader reader = new FileReader(filePath)){           
            message = "Employees added via JSON file Example";
            JSONParser jsonParser = new JSONParser();
            JSONArray jArray = (JSONArray) jsonParser.parse(reader);
            for (Iterator iterator = jArray.iterator(); iterator.hasNext();){
                JSONObject jObject = (JSONObject)iterator.next();
                Employee em = new Employee();
   
                em.setFirstName((String) jObject.get("first_name"));
                em.setLastName((String) jObject.get("last_name"));
                em.setJobTitle((String) jObject.get("job_title"));
                em.setSalary((Long) jObject.get("salary"));
                Session session = factory.openSession();
                Transaction tx = null;
                tx = session.beginTransaction();
                session.save(em);
                tx.commit();
            }
            reader.close();
        } catch (FileNotFoundException|org.json.simple.parser.ParseException ex) {} 
          catch (IOException|NullPointerException ex){}      
    }
    
    //This function takes the attributes of a student and puts them into an JSONObject
    public static JSONObject constructJsonObject (Employee em) {
        JSONObject jObject = new JSONObject();
        jObject.put("id_employee", em.getIdEmployee());
        jObject.put("first_name", em.getFirstName());
        jObject.put("last_name", em.getLastName());
        jObject.put("job_title", em.getJobTitle());
        jObject.put("salary", em.getSalary());
        return jObject;
    }
}
