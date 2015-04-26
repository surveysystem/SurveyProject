/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.Question;
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.dao.QuestionJpaController;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
@Named("addQuestion")
@RequestScoped
public class AddQuestionBean implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private Question question;
    private SurveyQuestion surveyQuestion;
    public AddQuestionBean() {
        if(question == null){
            question = new Question();
        }
    }

    public String addQuestion() throws RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();
        try {
            QuestionJpaController controller = new QuestionJpaController(utx, emf);
            controller.create(question);
            return "ViewQuestions";
        } finally {
            em.close();
        }
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void editQuestion(Question q) {
        this.question = q;
    }
}
