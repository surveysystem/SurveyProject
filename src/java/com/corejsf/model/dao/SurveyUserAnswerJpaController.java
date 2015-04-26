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
import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.SurveyUserAnswer;
import com.corejsf.model.dao.exceptions.NonexistentEntityException;
import com.corejsf.model.dao.exceptions.PreexistingEntityException;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
public class SurveyUserAnswerJpaController implements Serializable {

    public SurveyUserAnswerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SurveyUserAnswer surveyUserAnswer) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SurveyQuestion surveyQuestionId = surveyUserAnswer.getSurveyQuestionId();
            if (surveyQuestionId != null) {
                surveyQuestionId = em.getReference(surveyQuestionId.getClass(), surveyQuestionId.getSurveyQuestionId());
                surveyUserAnswer.setSurveyQuestionId(surveyQuestionId);
            }
            em.persist(surveyUserAnswer);
            if (surveyQuestionId != null) {
                surveyQuestionId.getSurveyUserAnswerCollection().add(surveyUserAnswer);
                surveyQuestionId = em.merge(surveyQuestionId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSurveyUserAnswer(surveyUserAnswer.getSurveyUserAnswerId()) != null) {
                throw new PreexistingEntityException("SurveyUserAnswer " + surveyUserAnswer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SurveyUserAnswer surveyUserAnswer) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SurveyUserAnswer persistentSurveyUserAnswer = em.find(SurveyUserAnswer.class, surveyUserAnswer.getSurveyUserAnswerId());
            SurveyQuestion surveyQuestionIdOld = persistentSurveyUserAnswer.getSurveyQuestionId();
            SurveyQuestion surveyQuestionIdNew = surveyUserAnswer.getSurveyQuestionId();
            if (surveyQuestionIdNew != null) {
                surveyQuestionIdNew = em.getReference(surveyQuestionIdNew.getClass(), surveyQuestionIdNew.getSurveyQuestionId());
                surveyUserAnswer.setSurveyQuestionId(surveyQuestionIdNew);
            }
            surveyUserAnswer = em.merge(surveyUserAnswer);
            if (surveyQuestionIdOld != null && !surveyQuestionIdOld.equals(surveyQuestionIdNew)) {
                surveyQuestionIdOld.getSurveyUserAnswerCollection().remove(surveyUserAnswer);
                surveyQuestionIdOld = em.merge(surveyQuestionIdOld);
            }
            if (surveyQuestionIdNew != null && !surveyQuestionIdNew.equals(surveyQuestionIdOld)) {
                surveyQuestionIdNew.getSurveyUserAnswerCollection().add(surveyUserAnswer);
                surveyQuestionIdNew = em.merge(surveyQuestionIdNew);
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
                Integer id = surveyUserAnswer.getSurveyUserAnswerId();
                if (findSurveyUserAnswer(id) == null) {
                    throw new NonexistentEntityException("The surveyUserAnswer with id " + id + " no longer exists.");
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
            SurveyUserAnswer surveyUserAnswer;
            try {
                surveyUserAnswer = em.getReference(SurveyUserAnswer.class, id);
                surveyUserAnswer.getSurveyUserAnswerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyUserAnswer with id " + id + " no longer exists.", enfe);
            }
            SurveyQuestion surveyQuestionId = surveyUserAnswer.getSurveyQuestionId();
            if (surveyQuestionId != null) {
                surveyQuestionId.getSurveyUserAnswerCollection().remove(surveyUserAnswer);
                surveyQuestionId = em.merge(surveyQuestionId);
            }
            em.remove(surveyUserAnswer);
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

    public List<SurveyUserAnswer> findSurveyUserAnswerEntities() {
        return findSurveyUserAnswerEntities(true, -1, -1);
    }

    public List<SurveyUserAnswer> findSurveyUserAnswerEntities(int maxResults, int firstResult) {
        return findSurveyUserAnswerEntities(false, maxResults, firstResult);
    }

    private List<SurveyUserAnswer> findSurveyUserAnswerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyUserAnswer.class));
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

    public SurveyUserAnswer findSurveyUserAnswer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SurveyUserAnswer.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyUserAnswerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyUserAnswer> rt = cq.from(SurveyUserAnswer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
