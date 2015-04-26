/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model.dao;

import com.corejsf.model.Survey;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corejsf.model.SurveyUser;
import java.util.ArrayList;
import java.util.Collection;
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.dao.exceptions.NonexistentEntityException;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
public class SurveyJpaController implements Serializable {

    public SurveyJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Survey survey) throws RollbackFailureException, Exception {
        if (survey.getSurveyUserCollection() == null) {
            survey.setSurveyUserCollection(new ArrayList<SurveyUser>());
        }
        if (survey.getSurveyQuestionCollection() == null) {
            survey.setSurveyQuestionCollection(new ArrayList<SurveyQuestion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<SurveyUser> attachedSurveyUserCollection = new ArrayList<SurveyUser>();
            for (SurveyUser surveyUserCollectionSurveyUserToAttach : survey.getSurveyUserCollection()) {
                surveyUserCollectionSurveyUserToAttach = em.getReference(surveyUserCollectionSurveyUserToAttach.getClass(), surveyUserCollectionSurveyUserToAttach.getSurveyUserId());
                attachedSurveyUserCollection.add(surveyUserCollectionSurveyUserToAttach);
            }
            survey.setSurveyUserCollection(attachedSurveyUserCollection);
            Collection<SurveyQuestion> attachedSurveyQuestionCollection = new ArrayList<SurveyQuestion>();
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestionToAttach : survey.getSurveyQuestionCollection()) {
                surveyQuestionCollectionSurveyQuestionToAttach = em.getReference(surveyQuestionCollectionSurveyQuestionToAttach.getClass(), surveyQuestionCollectionSurveyQuestionToAttach.getSurveyQuestionId());
                attachedSurveyQuestionCollection.add(surveyQuestionCollectionSurveyQuestionToAttach);
            }
            survey.setSurveyQuestionCollection(attachedSurveyQuestionCollection);
            em.persist(survey);
            for (SurveyUser surveyUserCollectionSurveyUser : survey.getSurveyUserCollection()) {
                Survey oldSurveyIdOfSurveyUserCollectionSurveyUser = surveyUserCollectionSurveyUser.getSurveyId();
                surveyUserCollectionSurveyUser.setSurveyId(survey);
                surveyUserCollectionSurveyUser = em.merge(surveyUserCollectionSurveyUser);
                if (oldSurveyIdOfSurveyUserCollectionSurveyUser != null) {
                    oldSurveyIdOfSurveyUserCollectionSurveyUser.getSurveyUserCollection().remove(surveyUserCollectionSurveyUser);
                    oldSurveyIdOfSurveyUserCollectionSurveyUser = em.merge(oldSurveyIdOfSurveyUserCollectionSurveyUser);
                }
            }
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestion : survey.getSurveyQuestionCollection()) {
                Survey oldSurveyIdOfSurveyQuestionCollectionSurveyQuestion = surveyQuestionCollectionSurveyQuestion.getSurveyId();
                surveyQuestionCollectionSurveyQuestion.setSurveyId(survey);
                surveyQuestionCollectionSurveyQuestion = em.merge(surveyQuestionCollectionSurveyQuestion);
                if (oldSurveyIdOfSurveyQuestionCollectionSurveyQuestion != null) {
                    oldSurveyIdOfSurveyQuestionCollectionSurveyQuestion.getSurveyQuestionCollection().remove(surveyQuestionCollectionSurveyQuestion);
                    oldSurveyIdOfSurveyQuestionCollectionSurveyQuestion = em.merge(oldSurveyIdOfSurveyQuestionCollectionSurveyQuestion);
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

    public void edit(Survey survey) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Survey persistentSurvey = em.find(Survey.class, survey.getSurveyId());
            Collection<SurveyUser> surveyUserCollectionOld = persistentSurvey.getSurveyUserCollection();
            Collection<SurveyUser> surveyUserCollectionNew = survey.getSurveyUserCollection();
            Collection<SurveyQuestion> surveyQuestionCollectionOld = persistentSurvey.getSurveyQuestionCollection();
            Collection<SurveyQuestion> surveyQuestionCollectionNew = survey.getSurveyQuestionCollection();
            Collection<SurveyUser> attachedSurveyUserCollectionNew = new ArrayList<SurveyUser>();
            for (SurveyUser surveyUserCollectionNewSurveyUserToAttach : surveyUserCollectionNew) {
                surveyUserCollectionNewSurveyUserToAttach = em.getReference(surveyUserCollectionNewSurveyUserToAttach.getClass(), surveyUserCollectionNewSurveyUserToAttach.getSurveyUserId());
                attachedSurveyUserCollectionNew.add(surveyUserCollectionNewSurveyUserToAttach);
            }
            surveyUserCollectionNew = attachedSurveyUserCollectionNew;
            survey.setSurveyUserCollection(surveyUserCollectionNew);
            Collection<SurveyQuestion> attachedSurveyQuestionCollectionNew = new ArrayList<SurveyQuestion>();
            for (SurveyQuestion surveyQuestionCollectionNewSurveyQuestionToAttach : surveyQuestionCollectionNew) {
                surveyQuestionCollectionNewSurveyQuestionToAttach = em.getReference(surveyQuestionCollectionNewSurveyQuestionToAttach.getClass(), surveyQuestionCollectionNewSurveyQuestionToAttach.getSurveyQuestionId());
                attachedSurveyQuestionCollectionNew.add(surveyQuestionCollectionNewSurveyQuestionToAttach);
            }
            surveyQuestionCollectionNew = attachedSurveyQuestionCollectionNew;
            survey.setSurveyQuestionCollection(surveyQuestionCollectionNew);
            survey = em.merge(survey);
            for (SurveyUser surveyUserCollectionOldSurveyUser : surveyUserCollectionOld) {
                if (!surveyUserCollectionNew.contains(surveyUserCollectionOldSurveyUser)) {
                    surveyUserCollectionOldSurveyUser.setSurveyId(null);
                    surveyUserCollectionOldSurveyUser = em.merge(surveyUserCollectionOldSurveyUser);
                }
            }
            for (SurveyUser surveyUserCollectionNewSurveyUser : surveyUserCollectionNew) {
                if (!surveyUserCollectionOld.contains(surveyUserCollectionNewSurveyUser)) {
                    Survey oldSurveyIdOfSurveyUserCollectionNewSurveyUser = surveyUserCollectionNewSurveyUser.getSurveyId();
                    surveyUserCollectionNewSurveyUser.setSurveyId(survey);
                    surveyUserCollectionNewSurveyUser = em.merge(surveyUserCollectionNewSurveyUser);
                    if (oldSurveyIdOfSurveyUserCollectionNewSurveyUser != null && !oldSurveyIdOfSurveyUserCollectionNewSurveyUser.equals(survey)) {
                        oldSurveyIdOfSurveyUserCollectionNewSurveyUser.getSurveyUserCollection().remove(surveyUserCollectionNewSurveyUser);
                        oldSurveyIdOfSurveyUserCollectionNewSurveyUser = em.merge(oldSurveyIdOfSurveyUserCollectionNewSurveyUser);
                    }
                }
            }
            for (SurveyQuestion surveyQuestionCollectionOldSurveyQuestion : surveyQuestionCollectionOld) {
                if (!surveyQuestionCollectionNew.contains(surveyQuestionCollectionOldSurveyQuestion)) {
                    surveyQuestionCollectionOldSurveyQuestion.setSurveyId(null);
                    surveyQuestionCollectionOldSurveyQuestion = em.merge(surveyQuestionCollectionOldSurveyQuestion);
                }
            }
            for (SurveyQuestion surveyQuestionCollectionNewSurveyQuestion : surveyQuestionCollectionNew) {
                if (!surveyQuestionCollectionOld.contains(surveyQuestionCollectionNewSurveyQuestion)) {
                    Survey oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion = surveyQuestionCollectionNewSurveyQuestion.getSurveyId();
                    surveyQuestionCollectionNewSurveyQuestion.setSurveyId(survey);
                    surveyQuestionCollectionNewSurveyQuestion = em.merge(surveyQuestionCollectionNewSurveyQuestion);
                    if (oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion != null && !oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion.equals(survey)) {
                        oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion.getSurveyQuestionCollection().remove(surveyQuestionCollectionNewSurveyQuestion);
                        oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion = em.merge(oldSurveyIdOfSurveyQuestionCollectionNewSurveyQuestion);
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
                Integer id = survey.getSurveyId();
                if (findSurvey(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
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
            Survey survey;
            try {
                survey = em.getReference(Survey.class, id);
                survey.getSurveyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            Collection<SurveyUser> surveyUserCollection = survey.getSurveyUserCollection();
            for (SurveyUser surveyUserCollectionSurveyUser : surveyUserCollection) {
                surveyUserCollectionSurveyUser.setSurveyId(null);
                surveyUserCollectionSurveyUser = em.merge(surveyUserCollectionSurveyUser);
            }
            Collection<SurveyQuestion> surveyQuestionCollection = survey.getSurveyQuestionCollection();
            for (SurveyQuestion surveyQuestionCollectionSurveyQuestion : surveyQuestionCollection) {
                surveyQuestionCollectionSurveyQuestion.setSurveyId(null);
                surveyQuestionCollectionSurveyQuestion = em.merge(surveyQuestionCollectionSurveyQuestion);
            }
            em.remove(survey);
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

    public List<Survey> findSurveyEntities() {
        return findSurveyEntities(true, -1, -1);
    }

    public List<Survey> findSurveyEntities(int maxResults, int firstResult) {
        return findSurveyEntities(false, maxResults, firstResult);
    }

    private List<Survey> findSurveyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Survey.class));
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

    public Survey findSurvey(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Survey.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Survey> rt = cq.from(Survey.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
