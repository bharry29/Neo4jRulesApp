/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import static com.bharath.rulesapp.createRule.createRule;
//import static com.bharath.rulesapp.neo4jDb.TransactionResult;
//import static com.bharath.rulesapp.neo4jDb.runQuery;
import static com.bharath.rulesapp.testRules.findRules;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;


/**
 *
 * @author bharath vadlamannati
 */
public class rulesAppMain {
    
    public static void main ( String [] arguments ) throws IOException, Exception
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Hello User. Please Select one option and press enter : 1. Add Rule 2. Test Rule \n");
        
        int useroptionnumber = input.nextInt();
        System.out.println("You entered " + useroptionnumber);
        
        
        if(useroptionnumber ==1)
        {
            System.out.print("Hello User. To Create a rule please select a type: 1. Time Based 2. Non-Time Based\n");
            int userruletypenumber = input.nextInt();
            
            System.out.print("Hello User\n");
            createRule(userruletypenumber);
        }
        
        else{
            System.out.print("Hello User. To Test please select an option: 1. Time Based 2. Non-Time Based\n");
            int usertestingnumber = input.nextInt();
            System.out.println("You entered " + usertestingnumber);
            
            try (Scanner eventinput = new Scanner(System.in)) {
                System.out.print("Please Enter a CQL Event to Test if there are any rules are applicable\n");
                
                String userinputevent = null;
                ArrayList<String> userinputcompleteevent = new ArrayList<>();
                for(userinputevent = eventinput.nextLine();!userinputevent.isEmpty();userinputevent = eventinput.nextLine()){
                    userinputcompleteevent.add(userinputevent);
                }
                
                for(String s: userinputcompleteevent)
                {
                    userinputevent += s;
                }
                
                System.out.println("Your event is : " + "\"" + userinputevent + "\" \n");
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat timeformat = new SimpleDateFormat("K:mm a");
                String presenttime = timeformat.format(cal.getTime());
                System.out.print("The time is : " + "\"" + presenttime + "\" \n");
                
                if(usertestingnumber ==1)
                {
                    String presenttimeevent = "WITH " + "\"" + presenttime + "\" AS currenttime ";
                    System.out.println(presenttimeevent + userinputevent);
                    findRules(presenttimeevent + userinputevent, 1);
                }
                
                if(usertestingnumber ==2)
                {
                    findRules(userinputevent,2);
                }
            }
        }
        input.close();
    }
}