/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.bharath.rulesapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bharathvadlamannati
 */
public class Rule{
    
    private String params;
    private int paramscount;
    private String event;
    private String condition;
    private String action;
    
    public Rule(String params, String event, String condition, String action){
        this.params = params;
        this.event = event;
        this.condition = condition;
        this.action = action;
    }
    

    public String getRuleParams() {
        return params;
    }
    

    public void setRuleParams(String params) {
        this.params = params;
    }
    

    public String getEvent() {
        return event;
    }
    

    public void setEvent(String event) {
        this.event = event;
    }
    
    

    public String getCondition() {
        return event;
    }
    
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    
    public int getParamsCount() {
        return paramscount;
    }
    
    public void setParamsCount(int paramscount) {
        this.paramscount = paramscount;
    }
    
    public String toString(){
        return this.params + " " + this.event + " " + this.condition + " " + this.action;
    }
}
