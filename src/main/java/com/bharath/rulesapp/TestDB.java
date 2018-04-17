/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

/**
 *
 * @author bharathvadlamannati
 */
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;

import static org.neo4j.driver.v1.Values.parameters;

/**
 *
 * @author bharathvadlamannati
 */

public class TestDB implements AutoCloseable
{
    private final Driver driver;
    
    public TestDB( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }
    
    @Override
    public void close() throws Exception
    {
        driver.close();
    }
    
    public void printGreeting(String prevmessage, String message ) throws Exception
    {
        String prevmsg = prevmessage;
        String msg = message;
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    String event = "CREATE (a:Greeting2) ";
                    
                    String condition = "SET a.message = {message}"  ;
                    
                    String action = " RETURN a.message + ', from node ' + id(a)";
                    
                    System.out.println("Full Tran:" + event + condition + action);
                    
                    StatementResult result = tx.run( event + condition + action ,
                            parameters( "message", prevmsg));

                    return result.single().get(0).asString();
                }
            } );
            
            System.out.println(greeting);
        }
        
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        close();
        
    }
    
    public static void testDB() throws Exception
    {
        try ( TestDB greeter = new TestDB( "bolt://localhost:11002", "neo4j", "1234" ) )
        {
            greeter.printGreeting( "hello", "hello, bharath" );
        }
    }
    
    
}