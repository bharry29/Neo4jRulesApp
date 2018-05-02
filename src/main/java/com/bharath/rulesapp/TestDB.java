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
import java.io.File;
import java.util.List;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
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
    public void executeRules(List<Rule> rules) throws Exception
    {
         try ( Session session = driver.session() )
        {
        for (Rule rule : rules) {
        
            String output = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    String event = rule.getEvent();
                    
                    String condition = rule.getCondition() ;
                    
                    String action = rule.getAction();
                    
                    String fullTran = event + " \n" + condition + "\n" + action;
                    System.out.println("Full Tran:" + fullTran);
                    
                    StatementResult result = tx.run(fullTran);
                            //parameters( "message", prevmsg));
                    Record records = result.single();
                    
                    return "The Output from DB is : " + records.fields().toString();
                }
            } );
            
            System.out.println(output);
       
        }
         }
        
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        close();
    }
    
    public static void testRule(List<Rule> rules) throws Exception
    {
        try ( TestDB db = new TestDB( "bolt://localhost:11002", "neo4j", "1234" ) )
        {
            db.executeRules(rules); 
        }
    } 
}