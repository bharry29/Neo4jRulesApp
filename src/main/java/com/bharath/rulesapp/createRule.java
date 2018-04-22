/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
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
        List<String> ruleinputparameterslist = new ArrayList<String>();
        String ruleinputevent = "", ruleinputcondition = "", ruleinputAction = "";
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your Rule Name: ");
        String ruleinputname = input.nextLine();
        
        
        //Input Parameters
        System.out.print("How many Input parameters for this rule ? ");
        String ruleinputparameterscount = input.nextLine();
        
        for (int i = 0; i< parseInt(ruleinputparameterscount); i++){
            System.out.print("Enter your Rule's number " + (i+1) + " Input Parameter(s): ");
            String ruleinputparameters = input.nextLine();
            ruleinputparameterslist.add(ruleinputparameters);
        }
        
        //Event
        System.out.print("Enter your Rule's Event part: ");
        ruleinputevent = input.nextLine();
        //Condition
        System.out.print("Enter your Rule's Condition part: ");
        ruleinputcondition = input.nextLine() ;
        
        //Action
        System.out.print("Enter your Rule's Action part: ");
        ruleinputAction = input.nextLine();
        
        String fullrule = "Input Parameters:" + ruleinputparameterslist + "\n" + "Event:" +"{" + ruleinputevent + "}" + "\n" + "Condition:" + "{" + ruleinputcondition + "}" +  "\n" + "Action:" + "{" + ruleinputAction + "}";
        
//        System.out.println("Your rule is: " + ruleinputname + "\n" + ruleinputparameterslist + "\n" + fullrule);
        
        System.out.println("Do you want to save it to Rules Repository (y/n)?");
        String saveruleoption = input.nextLine();
        
        if("y".equals(saveruleoption)){
            createRuleFile(ruleFolder,ruleinputname,fullrule);
            System.out.println("Rule Saved to Directory...!!! Exiting to Main Menu");
        }
        else{
            System.out.println("Rule Not Saved...!!! Exiting to Main Menu");
        }
        input.close();
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
