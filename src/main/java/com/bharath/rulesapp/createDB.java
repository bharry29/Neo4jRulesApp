/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
Hello*/
package com.bharath.rulesapp;

import java.io.File;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author bharathvadlamannati
 */
public class createDB {
    public static void main (String[] args)
    {
        System.out.print("\n Initiating Supply Chain Management system \n");
        Driver driver = GraphDatabase.driver( "bolt://localhost:11002", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            createGraphDB();
            createSuppliers();
            createDistributors();
            createFactories();
            createRetailers();
            createCustomers();
            session.close();
        }
        driver.close();
    }
    //This method is to create a new graph database
    public static void createGraphDB()
    {
        //Check if the db exists or no
        File dbDirectory = new File("RulesAppDb");
        if ( dbDirectory.exists() ) {
            if ( dbDirectory.isDirectory() ) {
                for ( File child : dbDirectory.listFiles() ) {
                    child.delete();
                }
            }
            dbDirectory.delete();
        }
        else {
            GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(dbDirectory);
            //System.out.print(rs);
            System.out.print("\n Hello CQL Statement\n");
            graphDB.shutdown();
            
        }
    }
    
    public static void createSuppliers()
    {
        System.out.print("\n Creating \n");
    }
    
    public static void createRetailers()
    {
        System.out.print("\n Creating \n");
    }
    
    public static void createDistributors()
    {
        System.out.print("\n Creating \n");
    }
    
    public static void createFactories()
    {
        System.out.print("\n Creating \n");
    }
    
    public static void createCustomers()
    {
        System.out.print("\n Creating \n");
    }
}
