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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bharathvadlamannati
 */
public class testRules {
    
    public static void testRules (String[] args) throws FileNotFoundException, IOException{
        //readRules();
    }
    
    
    public static void findRules(String eventFromUser, String inputParamsValuesFromUser, int ruleType) throws FileNotFoundException, IOException, Exception{
        
        System.out.print("Hello User. You are testing rules from the repository\n");
        
        File ruleFolder = null;
        if(ruleType== 1)
        {
            File folder = new File("/Users/bharathvadlamannati/NetBeansProjects/Neo4jRulesApp/Rules/TimeBased");
            ruleFolder = folder;
        }
        else{
            File folder = new File("/Users/bharathvadlamannati/NetBeansProjects/Neo4jRulesApp/Rules/NonTimebased");
            ruleFolder = folder;
        }
        
        File[] listOfFiles = ruleFolder.listFiles();
        
        int count = 0;
        List<String> resultFileNames = new ArrayList<>();
        List<String> resultFilePaths = new ArrayList<>();
        
        List<Rule> rulesList = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Scanner txtscan = new Scanner(file);
                Rule newRule = new Rule();
                
                String eventInRuleFile = "";
                String partOfEventFromFile = "";
               String inputParamsFormatInFile="";
               String partOfInputParamsFormatInFile = "";
                
               
                
                List<String> paramsListInRule = new ArrayList<>();
                
                String[] inputParamValues = null;
                if(txtscan.hasNextLine()){
                    String inputParamsString = txtscan.nextLine();
                    
                    if(inputParamsString.contains("Input Parameters:")){
                        int endIndex = inputParamsString.lastIndexOf("]");
                        int startIndex = inputParamsString.indexOf("[");
                        String inputParamsValueFromRule = inputParamsString.substring(startIndex+1,endIndex);
                        if(inputParamsValueFromRule != null && !inputParamsValueFromRule.isEmpty()){
                            paramsListInRule =
                                    Arrays.asList(inputParamsValueFromRule.split(","));
                            
                            newRule.setRuleParamsList(paramsListInRule);
                        }
                    }
                }
                
                if(txtscan.hasNextLine()){
                    String inputParamsString = txtscan.nextLine();
                    
                    if(inputParamsString.contains("Input Params Format:")){
                        int endIndex = inputParamsString.lastIndexOf("}");
                        int startIndex = inputParamsString.indexOf("{");
                        inputParamsFormatInFile = inputParamsString.substring(startIndex+1,endIndex);
                        if(inputParamsFormatInFile != null && !inputParamsFormatInFile.isEmpty()){
                           
                            newRule.setParamsFormat(inputParamsFormatInFile);
                        }
                    }
                }
                
                 while(txtscan.hasNextLine()){
                    String nextLine = txtscan.nextLine();
                    partOfEventFromFile += nextLine + " ";
                    
                    if(nextLine.contains("}")){
                        break;
                    }
                }
                
                if(!partOfEventFromFile.isEmpty()){
                    // partOfEventFromFile = txtscan.nextLine();
                    if(partOfEventFromFile.contains("Event:")){
                      
                        int startIndex = partOfEventFromFile.indexOf("{");
                        int endIndex = partOfEventFromFile.lastIndexOf("}");
                        eventInRuleFile = partOfEventFromFile.substring(startIndex+1,endIndex);
                       
                    }
                }
                
                // Match user input for params with the rule input params
//                inputParamValues = matchInput(eventFromUser.trim(), eventInRuleFile.trim(), paramsListInRule);

        if(eventInRuleFile.trim().equals(eventFromUser.trim())){
                inputParamValues = matchInput(inputParamsValuesFromUser.trim(), inputParamsFormatInFile.trim(), paramsListInRule);
                }
                if(inputParamValues!=null){
                    List<String> paramsvalues = new ArrayList<String>();
                    int paramArraySize  = paramsListInRule.size();
                    
                    for(int i = 0;i< paramArraySize;i++){
                        paramsvalues.add(inputParamValues[i]);
                    }
                    
                    //System.out.println("Values from the end user are:" + paramsvalues);
                    
                   
                    count++;
                    
                    resultFileNames.add(file.getName());
                    resultFilePaths.add(file.getPath());
                    newRule.setEvent(eventInRuleFile);

                    newRule.setRuleParamsValues(Arrays.asList(inputParamValues));

                    if(txtscan.hasNextLine()){
                        String condition = txtscan.nextLine();
                        if(condition.contains("Condition:")){                                
                            int startIndex = condition.indexOf("{");
                            int endIndex = condition.lastIndexOf("}");
                            String conditionInRuleFile = condition.substring(startIndex+1,endIndex);
                            newRule.setCondition(conditionInRuleFile);
                        }
                    }

                    if(txtscan.hasNextLine()){
                        String action = txtscan.nextLine();
                        if(action.contains("Action:")){
                            int startIndex = action.indexOf("{");
                            int endIndex = action.lastIndexOf("}");
                            String actionInRuleFile = action.substring(startIndex+1,endIndex);
                            newRule.setAction(actionInRuleFile);
                        }
                    }

                    rulesList.add(newRule);
                    
                }
                else{
                
                    //System.out.println("No input parameters found");
                    //break;
                }
            }
        }
        
        
        if (count > 0){
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
    
    public static String[] matchInput(String eventFromUser, String eventInFile, List<String> paramsList)
    {
        // Find match between user entered partOfEventFromFile and partOfEventFromFile in rule file
        int numberOfParams = paramsList.size();
        String[] userInputArray = new String[numberOfParams];
        String patternTemplate = eventInFile;
        
        patternTemplate = escapeRE(patternTemplate);
        for(String param:paramsList)
        {
            param = param.replace("\"", "");
            if(!eventInFile.contains(param)){
                return null;
            }
            patternTemplate = patternTemplate.replace(param, "(.*)");
        }
        
        
        Pattern pattern = Pattern.compile(patternTemplate) ;
        Matcher matcher = pattern.matcher(eventFromUser);
        
        if (matcher.matches()) {
            for(int i=0;i<numberOfParams;i++)
                userInputArray[i] = matcher.group(i+1);
        }
        
        return userInputArray;
        
    }
    
    public static String escapeRE(String str) {
        Pattern escaper = Pattern.compile("([^a-zA-z0-9])");
        str = escaper.matcher(str).replaceAll("\\\\$1");
        escaper = Pattern.compile("([\\[\\]])");
        return escaper.matcher(str).replaceAll("\\\\$1");
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
