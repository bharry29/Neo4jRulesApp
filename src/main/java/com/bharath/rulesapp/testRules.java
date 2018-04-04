/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author bharathvadlamannati
 */
public class testRules {
    
    public static void testRules (String[] args) throws FileNotFoundException, IOException{
        //readRules();
    }
    
    
    public static void readRules(String inputevent,int ruleType) throws FileNotFoundException, IOException{
        
        System.out.print("Hello User. You are reading and testing rules \n");
        
        File ruleFolder = null;
        if(ruleType== 1)
        {
            File folder = new File("/Users/bharathvadlamannati/NetBeansProjects/RulesApp/Rules/TimeBased");
            ruleFolder = folder;
        }
        else{
            File folder = new File("/Users/bharathvadlamannati/NetBeansProjects/RulesApp/Rules/NonTimebased");
            ruleFolder = folder;
        }
        
        File[] listOfFiles = ruleFolder.listFiles();
        
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Scanner txtscan = new Scanner(file);
                
                while(txtscan.hasNextLine()){
                    String str = txtscan.nextLine();
                    if(!str.contains(inputevent)){
                        System.out.println("The Event DOES NOT EXIST in existing rules");
                    }
                    else
                    {
                        System.out.println("The Event EXISTS" + " in "+ file.getName()) ;
                    }
                }
            }
        }
    }
    
}
