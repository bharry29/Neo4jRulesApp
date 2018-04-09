/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author bharathvadlamannati
 */
public class createRule {
    public static void main(String [] arguments) throws IOException{
        
//        createRule();
    }
    public static void createRule(int ruleType) throws IOException{
        
        String ruleFolder;
        if(ruleType ==1) {
            String ruleFolderTime = "Rules/TimeBased";
            ruleFolder = ruleFolderTime;
        }
        else
        {
            String ruleFolderNonTime = "Rules/NonTimebased";
            ruleFolder = ruleFolderNonTime;
        }
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your Rule Name: ");
        String ruleinputname = input.next();
        
        //Input Parameters
        System.out.print("Enter your Rule Input Parameters: ");
        String ruleinputparameters = "Input Parameters:" + "{" + input.next() + "}";
        
        //Event
        System.out.print("Enter your Rule's Event part: ");
        String ruleinputevent = "Event:" + "{" + input.next() + "}";;
        
        //Condition
        System.out.print("Enter your Rule's Condition part: ");
        String ruleinputcondition = "Condition:" + "{" + input.next() + "}";;
        
        //Action
        System.out.print("Enter your Rule's Action part: ");
        String ruleinputAction = "Action:" + "{" + input.next() + "}";;
        
        String fullrule = ruleinputparameters + "\n" + ruleinputevent + "\n" + ruleinputcondition + "\n" + ruleinputAction;
        
        System.out.println("Your rule is: " + ruleinputname + "\n" + ruleinputparameters + "\n" + fullrule.toUpperCase());
        
        System.out.println("Do you want to save it to Rules Repository (y/n)?");
        String saveruleoption = input.next();
        
        if("y".equals(saveruleoption)){
            createRuleFile(ruleFolder,ruleinputname,fullrule);
            System.out.println("Rule Saved to Directory...!!! Exiting to Main Menu");
        }
        else{
            System.out.println("Rule Not Saved...!!! Exiting to Main Menu");
        }
    }
    
    public static void createRuleFile (String directoryName,String ruleName, String ruleData) throws IOException
    {
        File dir = new File(directoryName);
        if (!dir.exists()) dir.mkdirs();
        File outFile = new File (directoryName + "/" + ruleName + ".txt");
        
        try{
            FileWriter fWriter = new FileWriter (outFile);
            fWriter.write(ruleData);
            fWriter.close();
        }
        catch( IOException e ){
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
