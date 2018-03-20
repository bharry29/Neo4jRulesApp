/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import static com.bharath.rulesapp.createRules.createRule;
import static com.bharath.rulesapp.testRules.testRules;
import java.io.*;
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
    
    public static void main ( String [] arguments ) throws IOException
    {
        Driver driver = GraphDatabase.driver( "bolt://localhost:11002", AuthTokens.basic( "neo4j", "1234" ) );
        
        try ( Session session = driver.session() )
        {
            StatementResult rs = session.run( "CREATE (n) RETURN n" );
            //System.out.print(rs);
            System.out.print("\nHello CQL Statement\n");
        }
        
        
        driver.close();
        Scanner input = new Scanner(System.in);
        System.out.print("Hello User. Please Select one option and press enter : 1. Add Rule 2. Test Rule \n");
        
        int useroptionnumber = input.nextInt();
        System.out.println("You entered " + useroptionnumber);
        
        
        if(useroptionnumber ==1)
        {
            System.out.print("Hello User\n");
            createRule();
        }
        
        else{
            System.out.print("Hello User. To Test please select an option: 1. Time Based 2. Non-Time Based\n");
            System.out.print("Please Enter a CQL Event to Test if there are any rules are applicable");
            testRules();
        }
    }
}