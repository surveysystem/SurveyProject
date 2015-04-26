/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.SurveyUser;
import com.corejsf.model.SurveyUserAnswer;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named("result")
@SessionScoped
public class ViewResultsBean implements Serializable {

    @Inject
    ViewSurveysBean viewSurvey;

    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private List<SurveyUser> surveyUsers;

    public ViewResultsBean() {

    }

    public List<SurveyUser> getSurveyUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("SurveyUser.findBySurveyId").setParameter("surveyId", viewSurvey.getCurrentSurvey());
            this.surveyUsers = query.getResultList();
        } finally {
            em.close();
        }
        return surveyUsers;
    }

    public List<SurveyUserAnswer> getSurveyUserAnswers() {
        EntityManager em = emf.createEntityManager();
        List<SurveyUserAnswer> userAnswers = new ArrayList<>();
        try {
            for (SurveyUser surveyUser : getSurveyUsers()) {
                Query query = em.createNamedQuery("SurveyUserAnswer.findBySurveyUserId").setParameter("surveyUserId", surveyUser);
                userAnswers.addAll(query.getResultList());
            }

        } finally {
            em.close();
        }
        return userAnswers;
    }

    public void setSurveyUsers(List<SurveyUser> surveys) {
        this.surveyUsers = surveys;
    }

}
