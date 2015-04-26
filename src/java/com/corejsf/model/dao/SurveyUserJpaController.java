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
import com.corejsf.model.User;
import com.corejsf.model.Survey;
import com.corejsf.model.SurveyUser;
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
public class SurveyUserJpaController implements Serializable {

    public SurveyUserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SurveyUser surveyUser) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User userId = surveyUser.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                surveyUser.setUserId(userId);
            }
            Survey surveyId = surveyUser.getSurveyId();
            if (surveyId != null) {
                surveyId = em.getReference(surveyId.getClass(), surveyId.getSurveyId());
                surveyUser.setSurveyId(surveyId);
            }
            em.persist(surveyUser);
            if (userId != null) {
                userId.getSurveyUserCollection().add(surveyUser);
                userId = em.merge(userId);
            }
            if (surveyId != null) {
                surveyId.getSurveyUserCollection().add(surveyUser);
                surveyId = em.merge(surveyId);
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

    public void edit(SurveyUser surveyUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SurveyUser persistentSurveyUser = em.find(SurveyUser.class, surveyUser.getSurveyUserId());
            User userIdOld = persistentSurveyUser.getUserId();
            User userIdNew = surveyUser.getUserId();
            Survey surveyIdOld = persistentSurveyUser.getSurveyId();
            Survey surveyIdNew = surveyUser.getSurveyId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                surveyUser.setUserId(userIdNew);
            }
            if (surveyIdNew != null) {
                surveyIdNew = em.getReference(surveyIdNew.getClass(), surveyIdNew.getSurveyId());
                surveyUser.setSurveyId(surveyIdNew);
            }
            surveyUser = em.merge(surveyUser);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getSurveyUserCollection().remove(surveyUser);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getSurveyUserCollection().add(surveyUser);
                userIdNew = em.merge(userIdNew);
            }
            if (surveyIdOld != null && !surveyIdOld.equals(surveyIdNew)) {
                surveyIdOld.getSurveyUserCollection().remove(surveyUser);
                surveyIdOld = em.merge(surveyIdOld);
            }
            if (surveyIdNew != null && !surveyIdNew.equals(surveyIdOld)) {
                surveyIdNew.getSurveyUserCollection().add(surveyUser);
                surveyIdNew = em.merge(surveyIdNew);
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
                Integer id = surveyUser.getSurveyUserId();
                if (findSurveyUser(id) == null) {
                    throw new NonexistentEntityException("The surveyUser with id " + id + " no longer exists.");
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
            SurveyUser surveyUser;
            try {
                surveyUser = em.getReference(SurveyUser.class, id);
                surveyUser.getSurveyUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyUser with id " + id + " no longer exists.", enfe);
            }
            User userId = surveyUser.getUserId();
            if (userId != null) {
                userId.getSurveyUserCollection().remove(surveyUser);
                userId = em.merge(userId);
            }
            Survey surveyId = surveyUser.getSurveyId();
            if (surveyId != null) {
                surveyId.getSurveyUserCollection().remove(surveyUser);
                surveyId = em.merge(surveyId);
            }
            em.remove(surveyUser);
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

    public List<SurveyUser> findSurveyUserEntities() {
        return findSurveyUserEntities(true, -1, -1);
    }

    public List<SurveyUser> findSurveyUserEntities(int maxResults, int firstResult) {
        return findSurveyUserEntities(false, maxResults, firstResult);
    }

    private List<SurveyUser> findSurveyUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyUser.class));
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

    public SurveyUser findSurveyUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SurveyUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyUser> rt = cq.from(SurveyUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
