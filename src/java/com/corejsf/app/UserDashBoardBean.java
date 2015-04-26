/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import com.corejsf.model.*;
import java.util.Objects;

/**
 *
 * @author Michael
 */
@Named("userDashBoardBean")
@SessionScoped
public class UserDashBoardBean implements Serializable {

    @Inject
    LoginBean loginBean;
    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private Survey currentSurvey;
    private List<Survey> surveys;

    public UserDashBoardBean() {

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

    public String takeSurvey(Survey survey) {
        currentSurvey = survey;
        return "TakeSurvey";
    }

    public Survey getCurrentSurvey() {
        return currentSurvey;
    }

    public void setCurrentSurvey(Survey currentSurvey) {
        this.currentSurvey = currentSurvey;
    }

    public boolean isCompleted(Survey s) {
        for (SurveyUser user : s.getSurveyUserCollection()) {
            if (Objects.equals(user.getUserId().getUserId(), loginBean.getUser().getUserId())) {
                return true;
            }
        }

        return false;

    }

}
