/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.Question;
import com.corejsf.model.Survey;
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.dao.SurveyJpaController;
import com.corejsf.model.dao.SurveyQuestionJpaController;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
@Named("addSurvey")
@SessionScoped
public class AddSurveyBean implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private List<Question> questions;
    private Survey survey;
    private final List<Question> selectedQuestions = new ArrayList<>();

    public AddSurveyBean() {
        survey = new Survey();
    }

    public List<Question> getQuestions() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Question.findAll");
            this.questions = query.getResultList();
        } finally {
            em.close();
        }
        return questions;
    }

    public void toggleSelected(Question question) {
        if (selectedQuestions.contains(question)) {
            selectedQuestions.remove(question);
        } else {
            selectedQuestions.add(question);
        }
    }

    public String addSurvey() throws RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();

        //TODO get selected questions
        try {
            SurveyJpaController surveyController = new SurveyJpaController(utx, emf);
            surveyController.create(survey);
            

            SurveyQuestionJpaController surveyQuestionController = new SurveyQuestionJpaController(utx, emf);

            for (Question selectedQuestion : selectedQuestions) {
                SurveyQuestion sq = new SurveyQuestion();
                sq.setQuestionId(selectedQuestion);
                sq.setSurveyId(survey);
                //TODO: set choices to be displayed for multiple choice later
                surveyQuestionController.create(sq);
            }
            survey = new Survey();
            return "ViewSurveys";
        } finally {
            em.close();
        }
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

}
