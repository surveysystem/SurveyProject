/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.Survey;
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.SurveyUser;
import com.corejsf.model.SurveyUserAnswer;
import com.corejsf.model.dao.SurveyUserAnswerJpaController;
import com.corejsf.model.dao.SurveyUserJpaController;
import com.corejsf.model.dao.UserJpaController;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
@Named("takeSurvey")
@SessionScoped
public class TakeSurveyBean implements Serializable {

    @Inject
    LoginBean loginBean;
    @Inject
    UserDashBoardBean userDashBoard;

    @Resource
    private UserTransaction utx;

    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private Map<Integer, String> answers;
    private String answer;

    public TakeSurveyBean() {
        answers = new HashMap<>();
    }

    public Collection<SurveyQuestion> getSurveyQuestions() {
        return userDashBoard.getCurrentSurvey().getSurveyQuestionCollection();
    }

    public Survey getSurvey() {
        return userDashBoard.getCurrentSurvey();
    }

    public void setQuestionAnswer(SurveyQuestion sq) throws RollbackFailureException, Exception {
        if (answers == null) {
            answers = new HashMap<>();
        }
        answers.put(sq.getSurveyQuestionId(), answer);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String submitAnswers() throws RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();

        try {
            SurveyUserJpaController sujpaController = new SurveyUserJpaController(utx, emf);
            SurveyUser su = new SurveyUser();
            su.setFinishedAt(new Date());
            su.setIsFinished(true);
            su.setSurveyId(getSurvey());
            su.setSurveyUserAnswerCollection(null);
            su.setUserId(loginBean.getUser());
            sujpaController.create(su);
            
            SurveyUserAnswerJpaController surveyUserAnswerJpaController = new SurveyUserAnswerJpaController(utx, emf);
            Collection<SurveyUserAnswer> surveyUserAnswers = new ArrayList<>();
            for (SurveyQuestion sq : getSurveyQuestions()) {
                SurveyUserAnswer sua = new SurveyUserAnswer();
                sua.setAnswerText(answers.get(sq.getSurveyQuestionId()));
                sua.setSurveyQuestionId(sq);
                sua.setSurveyUserId(su);
                surveyUserAnswers.add(sua);
                surveyUserAnswerJpaController.create(sua);
            }
            
            UserJpaController userController = new UserJpaController(utx, emf);
            loginBean.getUser().getSurveyUserCollection().add(su);

            userController.edit(loginBean.getUser());

            return "UserDashBoard";
        } finally {
            answers = new HashMap<>();
            answer = null;
            em.close();
        }
    }

    public String cancel() {
        answers = null;
        answer = null;
        return "UserDashBoard";
    }
}
