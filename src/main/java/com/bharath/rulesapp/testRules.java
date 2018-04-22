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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static com.bharath.rulesapp.TestDB.testRule;
/**
 *
 * @author bharathvadlamannati
 */
public class testRules {
    
    public static void testRules (String[] args) throws FileNotFoundException, IOException{
        //readRules();
    }
    
    
    public static void findRules(String inputevent,int ruleType) throws FileNotFoundException, IOException, Exception{
        
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
        
        List<Rule> rulesList = new ArrayList<Rule>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Scanner txtscan = new Scanner(file);
                Rule newRule = new Rule();
                if(txtscan.hasNextLine()){
                    String inputParamsString = txtscan.nextLine();
                    if(txtscan.hasNextLine()){
                        String event = txtscan.nextLine();
                        
                        if(event.contains("Event:{" + inputevent)){
                            count++;
                            resultFileNames.add(file.getName());
                            resultFilePaths.add(file.getPath());
                            int endIndex = event.lastIndexOf("}");
                            int startIndex = event.indexOf("{");
                            String eventValue = event.substring(startIndex+1,endIndex);
                            newRule.setEvent(eventValue);
                            
                            if(inputParamsString.contains("Input Parameters:")){
                                endIndex = inputParamsString.lastIndexOf("]");
                                startIndex = inputParamsString.indexOf("[");
                                String paramsValue = inputParamsString.substring(startIndex+1,endIndex);
                                List<String> paramsList = new ArrayList<String>(
                                        Arrays.asList(paramsValue.split(", ")));
                                newRule.setRuleParamsList(paramsList);
                            }
                            
                            if(txtscan.hasNextLine()){
                                String condition = txtscan.nextLine();
                                if(condition.contains("Condition:")){
                                    endIndex = condition.lastIndexOf("}");
                                    startIndex = condition.indexOf("{");
                                    String conditionValue = condition.substring(startIndex+1,endIndex);
                                    newRule.setCondition(conditionValue);
                                }
                            }
                            
                            if(txtscan.hasNextLine()){
                                String action = txtscan.nextLine();
                                if(action.contains("Action:")){
                                    endIndex = action.lastIndexOf("}");
                                    startIndex = action.indexOf("{");
                                    String actionValue = action.substring(startIndex+1,endIndex);
                                    newRule.setAction(actionValue);
                                }
                            }
                            
                            rulesList.add(newRule);
                        }
                        
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
filecount++;
            }
            
            testRule(rulesList);
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
