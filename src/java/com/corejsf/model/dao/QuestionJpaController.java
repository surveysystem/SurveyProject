/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model.dao;

import com.corejsf.model.Question;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corejsf.model.SurveyQuestion;
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
public class QuestionJpaController implements Serializable {

    public QuestionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Question question) throws RollbackFailureException, Exception {
        if (question.getSurveyQuestionCollection() == null) {
            question.setSurveyQuestionCollection(new ArrayList<SurveyQuestion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<SurveyQuestion> attachedSurveyQuestionCollection = new ArrayList<SurveyQuestion>();
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestionToAttach : question.getSurveyQuestionCollection()) {
                surveyQuestionCollectionSurveyQuestionToAttach = em.getReference(surveyQuestionCollectionSurveyQuestionToAttach.getClass(), surveyQuestionCollectionSurveyQuestionToAttach.getSurveyQuestionId());
                attachedSurveyQuestionCollection.add(surveyQuestionCollectionSurveyQuestionToAttach);
            }
            question.setSurveyQuestionCollection(attachedSurveyQuestionCollection);
            em.persist(question);
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestion : question.getSurveyQuestionCollection()) {
                Question oldQuestionIdOfSurveyQuestionCollectionSurveyQuestion = surveyQuestionCollectionSurveyQuestion.getQuestionId();
                surveyQuestionCollectionSurveyQuestion.setQuestionId(question);
                surveyQuestionCollectionSurveyQuestion = em.merge(surveyQuestionCollectionSurveyQuestion);
                if (oldQuestionIdOfSurveyQuestionCollectionSurveyQuestion != null) {
                    oldQuestionIdOfSurveyQuestionCollectionSurveyQuestion.getSurveyQuestionCollection().remove(surveyQuestionCollectionSurveyQuestion);
                    oldQuestionIdOfSurveyQuestionCollectionSurveyQuestion = em.merge(oldQuestionIdOfSurveyQuestionCollectionSurveyQuestion);
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

    public void edit(Question question) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Question persistentQuestion = em.find(Question.class, question.getQuestionId());
            Collection<SurveyQuestion> surveyQuestionCollectionOld = persistentQuestion.getSurveyQuestionCollection();
            Collection<SurveyQuestion> surveyQuestionCollectionNew = question.getSurveyQuestionCollection();
            Collection<SurveyQuestion> attachedSurveyQuestionCollectionNew = new ArrayList<SurveyQuestion>();
            for (SurveyQuestion surveyQuestionCollectionNewSurveyQuestionToAttach : surveyQuestionCollectionNew) {
                surveyQuestionCollectionNewSurveyQuestionToAttach = em.getReference(surveyQuestionCollectionNewSurveyQuestionToAttach.getClass(), surveyQuestionCollectionNewSurveyQuestionToAttach.getSurveyQuestionId());
                attachedSurveyQuestionCollectionNew.add(surveyQuestionCollectionNewSurveyQuestionToAttach);
            }
            surveyQuestionCollectionNew = attachedSurveyQuestionCollectionNew;
            question.setSurveyQuestionCollection(surveyQuestionCollectionNew);
            question = em.merge(question);
            for (SurveyQuestion surveyQuestionCollectionOldSurveyQuestion : surveyQuestionCollectionOld) {
                if (!surveyQuestionCollectionNew.contains(surveyQuestionCollectionOldSurveyQuestion)) {
                    surveyQuestionCollectionOldSurveyQuestion.setQuestionId(null);
                    surveyQuestionCollectionOldSurveyQuestion = em.merge(surveyQuestionCollectionOldSurveyQuestion);
                }
            }
            for (SurveyQuestion surveyQuestionCollectionNewSurveyQuestion : surveyQuestionCollectionNew) {
                if (!surveyQuestionCollectionOld.contains(surveyQuestionCollectionNewSurveyQuestion)) {
                    Question oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion = surveyQuestionCollectionNewSurveyQuestion.getQuestionId();
                    surveyQuestionCollectionNewSurveyQuestion.setQuestionId(question);
                    surveyQuestionCollectionNewSurveyQuestion = em.merge(surveyQuestionCollectionNewSurveyQuestion);
                    if (oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion != null && !oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion.equals(question)) {
                        oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion.getSurveyQuestionCollection().remove(surveyQuestionCollectionNewSurveyQuestion);
                        oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion = em.merge(oldQuestionIdOfSurveyQuestionCollectionNewSurveyQuestion);
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
                Integer id = question.getQuestionId();
                if (findQuestion(id) == null) {
                    throw new NonexistentEntityException("The question with id " + id + " no longer exists.");
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
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getQuestionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with id " + id + " no longer exists.", enfe);
            }
            Collection<SurveyQuestion> surveyQuestionCollection = question.getSurveyQuestionCollection();
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestion : surveyQuestionCollection) {
                surveyQuestionCollectionSurveyQuestion.setQuestionId(null);
                surveyQuestionCollectionSurveyQuestion = em.merge(surveyQuestionCollectionSurveyQuestion);
            }
            em.remove(question);
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

    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Question.class));
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

    public Question findQuestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Question> rt = cq.from(Question.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
