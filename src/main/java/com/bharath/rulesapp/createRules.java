/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author bharathvadlamannati
 */
public class createRules {
    public static void main(String [] arguments) throws IOException{
        createRule();
    }
public static void createRule() throws IOException{
        String ruleFolder = "Rules";
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your Rule Name: ");
        String ruleinputname = input.next();

        //Input Parameters
        System.out.print("Enter your Rule Input Parameters: ");
        String ruleinputparameters = input.next();

        //Event
        System.out.print("Enter your Rule's Event part: ");
        String ruleinputevent = input.next();

        //Condition
        System.out.print("Enter your Rule's Condition part: ");
        String ruleinputcondition = input.next();

        //Action
        System.out.print("Enter your Rule's Action part: ");
        String ruleinputAction = input.next();

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
    FileWriter fWriter = new FileWriter (outFile);
  }
}
