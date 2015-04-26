/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corejsf.model.Survey;
import com.corejsf.model.Question;
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.SurveyUserAnswer;
import com.corejsf.model.dao.exceptions.NonexistentEntityException;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
public class SurveyQuestionJpaController implements Serializable {

    public SurveyQuestionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SurveyQuestion surveyQuestion) throws RollbackFailureException, Exception {
        if (surveyQuestion.getSurveyUserAnswerCollection() == null) {
            surveyQuestion.setSurveyUserAnswerCollection(new ArrayList<SurveyUserAnswer>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Survey surveyId = surveyQuestion.getSurveyId();
            if (surveyId != null) {
                surveyId = em.getReference(surveyId.getClass(), surveyId.getSurveyId());
                surveyQuestion.setSurveyId(surveyId);
            }
            Question questionId = surveyQuestion.getQuestionId();
            if (questionId != null) {
                questionId = em.getReference(questionId.getClass(), questionId.getQuestionId());
                surveyQuestion.setQuestionId(questionId);
            }
            Collection<SurveyUserAnswer> attachedSurveyUserAnswerCollection = new ArrayList<SurveyUserAnswer>();
            for (SurveyUserAnswer surveyUserAnswerCollectionSurveyUserAnswerToAttach : surveyQuestion.getSurveyUserAnswerCollection()) {
                surveyUserAnswerCollectionSurveyUserAnswerToAttach = em.getReference(surveyUserAnswerCollectionSurveyUserAnswerToAttach.getClass(), surveyUserAnswerCollectionSurveyUserAnswerToAttach.getSurveyUserAnswerId());
                attachedSurveyUserAnswerCollection.add(surveyUserAnswerCollectionSurveyUserAnswerToAttach);
            }
            surveyQuestion.setSurveyUserAnswerCollection(attachedSurveyUserAnswerCollection);
            em.persist(surveyQuestion);
            if (surveyId != null) {
                surveyId.getSurveyQuestionCollection().add(surveyQuestion);
                surveyId = em.merge(surveyId);
            }
            if (questionId != null) {
                questionId.getSurveyQuestionCollection().add(surveyQuestion);
                questionId = em.merge(questionId);
            }
            for (SurveyUserAnswer surveyUserAnswerCollectionSurveyUserAnswer : surveyQuestion.getSurveyUserAnswerCollection()) {
                SurveyQuestion oldSurveyQuestionIdOfSurveyUserAnswerCollectionSurveyUserAnswer = surveyUserAnswerCollectionSurveyUserAnswer.getSurveyQuestionId();
                surveyUserAnswerCollectionSurveyUserAnswer.setSurveyQuestionId(surveyQuestion);
                surveyUserAnswerCollectionSurveyUserAnswer = em.merge(surveyUserAnswerCollectionSurveyUserAnswer);
                if (oldSurveyQuestionIdOfSurveyUserAnswerCollectionSurveyUserAnswer != null) {
                    oldSurveyQuestionIdOfSurveyUserAnswerCollectionSurveyUserAnswer.getSurveyUserAnswerCollection().remove(surveyUserAnswerCollectionSurveyUserAnswer);
                    oldSurveyQuestionIdOfSurveyUserAnswerCollectionSurveyUserAnswer = em.merge(oldSurveyQuestionIdOfSurveyUserAnswerCollectionSurveyUserAnswer);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SurveyQuestion surveyQuestion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SurveyQuestion persistentSurveyQuestion = em.find(SurveyQuestion.class, surveyQuestion.getSurveyQuestionId());
            Survey surveyIdOld = persistentSurveyQuestion.getSurveyId();
            Survey surveyIdNew = surveyQuestion.getSurveyId();
            Question questionIdOld = persistentSurveyQuestion.getQuestionId();
            Question questionIdNew = surveyQuestion.getQuestionId();
            Collection<SurveyUserAnswer> surveyUserAnswerCollectionOld = persistentSurveyQuestion.getSurveyUserAnswerCollection();
            Collection<SurveyUserAnswer> surveyUserAnswerCollectionNew = surveyQuestion.getSurveyUserAnswerCollection();
            if (surveyIdNew != null) {
                surveyIdNew = em.getReference(surveyIdNew.getClass(), surveyIdNew.getSurveyId());
                surveyQuestion.setSurveyId(surveyIdNew);
            }
            if (questionIdNew != null) {
                questionIdNew = em.getReference(questionIdNew.getClass(), questionIdNew.getQuestionId());
                surveyQuestion.setQuestionId(questionIdNew);
            }
            Collection<SurveyUserAnswer> attachedSurveyUserAnswerCollectionNew = new ArrayList<SurveyUserAnswer>();
            for (SurveyUserAnswer surveyUserAnswerCollectionNewSurveyUserAnswerToAttach : surveyUserAnswerCollectionNew) {
                surveyUserAnswerCollectionNewSurveyUserAnswerToAttach = em.getReference(surveyUserAnswerCollectionNewSurveyUserAnswerToAttach.getClass(), surveyUserAnswerCollectionNewSurveyUserAnswerToAttach.getSurveyUserAnswerId());
                attachedSurveyUserAnswerCollectionNew.add(surveyUserAnswerCollectionNewSurveyUserAnswerToAttach);
            }
            surveyUserAnswerCollectionNew = attachedSurveyUserAnswerCollectionNew;
            surveyQuestion.setSurveyUserAnswerCollection(surveyUserAnswerCollectionNew);
            surveyQuestion = em.merge(surveyQuestion);
            if (surveyIdOld != null && !surveyIdOld.equals(surveyIdNew)) {
                surveyIdOld.getSurveyQuestionCollection().remove(surveyQuestion);
                surveyIdOld = em.merge(surveyIdOld);
            }
            if (surveyIdNew != null && !surveyIdNew.equals(surveyIdOld)) {
                surveyIdNew.getSurveyQuestionCollection().add(surveyQuestion);
                surveyIdNew = em.merge(surveyIdNew);
            }
            if (questionIdOld != null && !questionIdOld.equals(questionIdNew)) {
                questionIdOld.getSurveyQuestionCollection().remove(surveyQuestion);
                questionIdOld = em.merge(questionIdOld);
            }
            if (questionIdNew != null && !questionIdNew.equals(questionIdOld)) {
                questionIdNew.getSurveyQuestionCollection().add(surveyQuestion);
                questionIdNew = em.merge(questionIdNew);
            }
            for (SurveyUserAnswer surveyUserAnswerCollectionOldSurveyUserAnswer : surveyUserAnswerCollectionOld) {
                if (!surveyUserAnswerCollectionNew.contains(surveyUserAnswerCollectionOldSurveyUserAnswer)) {
                    surveyUserAnswerCollectionOldSurveyUserAnswer.setSurveyQuestionId(null);
                    surveyUserAnswerCollectionOldSurveyUserAnswer = em.merge(surveyUserAnswerCollectionOldSurveyUserAnswer);
                }
            }
            for (SurveyUserAnswer surveyUserAnswerCollectionNewSurveyUserAnswer : surveyUserAnswerCollectionNew) {
                if (!surveyUserAnswerCollectionOld.contains(surveyUserAnswerCollectionNewSurveyUserAnswer)) {
                    SurveyQuestion oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer = surveyUserAnswerCollectionNewSurveyUserAnswer.getSurveyQuestionId();
                    surveyUserAnswerCollectionNewSurveyUserAnswer.setSurveyQuestionId(surveyQuestion);
                    surveyUserAnswerCollectionNewSurveyUserAnswer = em.merge(surveyUserAnswerCollectionNewSurveyUserAnswer);
                    if (oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer != null && !oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer.equals(surveyQuestion)) {
                        oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer.getSurveyUserAnswerCollection().remove(surveyUserAnswerCollectionNewSurveyUserAnswer);
                        oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer = em.merge(oldSurveyQuestionIdOfSurveyUserAnswerCollectionNewSurveyUserAnswer);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = surveyQuestion.getSurveyQuestionId();
                if (findSurveyQuestion(id) == null) {
                    throw new NonexistentEntityException("The surveyQuestion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SurveyQuestion surveyQuestion;
            try {
                surveyQuestion = em.getReference(SurveyQuestion.class, id);
                surveyQuestion.getSurveyQuestionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyQuestion with id " + id + " no longer exists.", enfe);
            }
            Survey surveyId = surveyQuestion.getSurveyId();
            if (surveyId != null) {
                surveyId.getSurveyQuestionCollection().remove(surveyQuestion);
                surveyId = em.merge(surveyId);
            }
            Question questionId = surveyQuestion.getQuestionId();
            if (questionId != null) {
                questionId.getSurveyQuestionCollection().remove(surveyQuestion);
                questionId = em.merge(questionId);
            }
            Collection<SurveyUserAnswer> surveyUserAnswerCollection = surveyQuestion.getSurveyUserAnswerCollection();
            for (SurveyUserAnswer surveyUserAnswerCollectionSurveyUserAnswer : surveyUserAnswerCollection) {
                surveyUserAnswerCollectionSurveyUserAnswer.setSurveyQuestionId(null);
                surveyUserAnswerCollectionSurveyUserAnswer = em.merge(surveyUserAnswerCollectionSurveyUserAnswer);
            }
            em.remove(surveyQuestion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SurveyQuestion> findSurveyQuestionEntities() {
        return findSurveyQuestionEntities(true, -1, -1);
    }

    public List<SurveyQuestion> findSurveyQuestionEntities(int maxResults, int firstResult) {
        return findSurveyQuestionEntities(false, maxResults, firstResult);
    }

    private List<SurveyQuestion> findSurveyQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyQuestion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SurveyQuestion findSurveyQuestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SurveyQuestion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyQuestion> rt = cq.from(SurveyQuestion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
