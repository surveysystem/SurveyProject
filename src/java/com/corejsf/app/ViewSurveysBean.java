/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.Survey;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Named("surveyBean")
@SessionScoped
public class ViewSurveysBean implements Serializable {

    @Inject
    LoginBean loginData;
    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private List<Survey> surveys;
    private Survey currentSurvey;
    
    public ViewSurveysBean() {

    }

    public String editUser() {
        return "AddUser";
    }

    public List<Survey> getSurveys() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Survey.findAll");
            this.surveys = query.getResultList();
        } finally {
            em.close();
        }
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
    public String viewResult(Survey survey){
        this.currentSurvey = survey;
        return "Results";
    }

    public Survey getCurrentSurvey() {
        return currentSurvey;
    }
    
    public void setCurrentSurvey(Survey currentSurvey) {
        this.currentSurvey = currentSurvey;
    }
}
