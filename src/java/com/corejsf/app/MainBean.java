/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named("main")
@RequestScoped
public class MainBean implements Serializable {

    @Inject
    LoginBean loginData;
    private int selected=1;

    public MainBean(){
        
    }
    public String getNavigation(int navId){
        selected = navId;
        String navStr = "";
        switch  (navId){
            case 1:
                navStr="ViewUsers"; 
                break;
            case 2:
                navStr="ViewQuestions";
                break;
            case 3:
                navStr="ViewSurveys";
                break;
            default:
                break;
        }
        
        return navStr;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getStyleClass(int numId){
        if(numId == selected){
            return "selected";
        }
        return "unselected";
    }
    
    
}
