/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlueClasses;

import Controllers.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Konkles
 */
public class HttpURL implements Handler{
    @Override
    public void engage(){
        HttpURL();
    }
    
    public static void HttpURL() {
        try {
            String url = "http://espn.com/";  
            String results = getHttpUrl(url);
            System.out.println(results);
        } catch (Exception e) {
        }
    }   

    // This function gets the html of the given web site and thereby verifies that a connection has been established.
    public static String getHttpUrl(String desiredUrl) throws Exception{
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            url = new URL(desiredUrl);
            // Opens a connection with a given web server. 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            // This while loop along with StringBuilder() displays the input stream of html code.
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw e;
        }
        finally {
            if (reader != null) {
                try {
                reader.close();
                } catch (IOException ioe){
                }
            }
        }
    }
}
