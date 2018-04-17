/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bharathvadlamannati
 */
public class testRules {
    
    public static void testRules (String[] args) throws FileNotFoundException, IOException{
        //readRules();
    }
    
    
    public static void findRules(String inputevent,int ruleType) throws FileNotFoundException, IOException{
        
        System.out.print("Hello User. You are testing rules from the repository\n");
        
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
        
        int count = 0;
        List<String> resultFileNames = new ArrayList<String>();
        List<String> resultFilePaths = new ArrayList<String>();
        List<String> paramsList = new ArrayList<String>();
        
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Scanner txtscan = new Scanner(file);
                
                while(txtscan.hasNextLine()){
                    String str = txtscan.nextLine();
                    
                    if(str.contains("Input Parameters:")){
                        paramsList.add(str);
                    }

                    if(str.contains("Event:{" + inputevent)){
                        count++;
                        resultFileNames.add(file.getName());
                        resultFilePaths.add(file.getPath());
                    }
                }
            }
        }
        
        if (count>0){
            int filecount = 1;
            System.out.println("The Event EXISTS" + " in "+ resultFileNames) ;
            for (String filepath:resultFilePaths){
                Path p = Paths.get(filepath);
                String filename = p.getFileName().toString();
                System.out.println("Rule "+ filecount +  ":" + filename);
                printRuleFile(filepath);
                readRuleParams(paramsList);
                filecount++;
            }
        }
        
        else{
            System.out.println("The Event DOES NOT EXIST in existing rules repository");
        }
    }
    
    
    public static void readRuleParams(List<String> paramsList)
    {
       System.out.println(paramsList); 
    }
    
    public static void printRuleParams(List<String> paramsList){
        
        System.out.println(paramsList);
    }
//    public static void readRules (File ruleFile){
//        try {
//            File f = ruleFile;
//            Scanner sc = new Scanner(f);
//
//            List<Rule> rules = new ArrayList<Rule>();
//
//            while(sc.hasNextLine()){
//                String line = sc.nextLine();
//                String[] ruledetails = line.split(":");
//                String params = ruledetails[0];
//                String event = ruledetails[1];
//                String condition = ruledetails[2];
//                String action = ruledetails[3];
//                Rule r = new Rule(params, event, condition, action);
//                rules.add(r);
//            }
//
//            for(Rule p: rules){
//                System.out.println(p.toString());
//            }
//
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void printRuleFile(String inputFilePath) throws FileNotFoundException, IOException{
        
        int character;
        StringBuffer buffer = new StringBuffer("");
        
        try (FileInputStream inputStream = new FileInputStream(new File(inputFilePath))) {
            while( (character = inputStream.read()) != -1)
                buffer.append((char) character);
        }
        System.out.println("The Rule which is found in the rule repository based on your event:\n" + buffer);
    }
}
