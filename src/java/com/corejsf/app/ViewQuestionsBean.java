/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.Question;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Named("questionsBean")
@SessionScoped
public class ViewQuestionsBean implements Serializable {

    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    
    private List<Question> questions;

    public ViewQuestionsBean() {
        
    }

    public String editQuestion(){
        return "AddQuestions";
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

    public void setQuestions(List<Question> questions) {
        
        this.questions = questions;
    }

}
