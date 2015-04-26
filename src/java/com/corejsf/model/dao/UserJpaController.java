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
import com.corejsf.model.SurveyUser;
import com.corejsf.model.User;
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
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getSurveyUserCollection() == null) {
            user.setSurveyUserCollection(new ArrayList<SurveyUser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<SurveyUser> attachedSurveyUserCollection = new ArrayList<SurveyUser>();
            for (SurveyUser surveyUserCollectionSurveyUserToAttach : user.getSurveyUserCollection()) {
                surveyUserCollectionSurveyUserToAttach = em.getReference(surveyUserCollectionSurveyUserToAttach.getClass(), surveyUserCollectionSurveyUserToAttach.getSurveyUserId());
                attachedSurveyUserCollection.add(surveyUserCollectionSurveyUserToAttach);
            }
            user.setSurveyUserCollection(attachedSurveyUserCollection);
            em.persist(user);
            for (SurveyUser surveyUserCollectionSurveyUser : user.getSurveyUserCollection()) {
                User oldUserIdOfSurveyUserCollectionSurveyUser = surveyUserCollectionSurveyUser.getUserId();
                surveyUserCollectionSurveyUser.setUserId(user);
                surveyUserCollectionSurveyUser = em.merge(surveyUserCollectionSurveyUser);
                if (oldUserIdOfSurveyUserCollectionSurveyUser != null) {
                    oldUserIdOfSurveyUserCollectionSurveyUser.getSurveyUserCollection().remove(surveyUserCollectionSurveyUser);
                    oldUserIdOfSurveyUserCollectionSurveyUser = em.merge(oldUserIdOfSurveyUserCollectionSurveyUser);
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

    public void edit(User user) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getUserId());
            Collection<SurveyUser> surveyUserCollectionOld = persistentUser.getSurveyUserCollection();
            Collection<SurveyUser> surveyUserCollectionNew = user.getSurveyUserCollection();
            Collection<SurveyUser> attachedSurveyUserCollectionNew = new ArrayList<SurveyUser>();
            for (SurveyUser surveyUserCollectionNewSurveyUserToAttach : surveyUserCollectionNew) {
                surveyUserCollectionNewSurveyUserToAttach = em.getReference(surveyUserCollectionNewSurveyUserToAttach.getClass(), surveyUserCollectionNewSurveyUserToAttach.getSurveyUserId());
                attachedSurveyUserCollectionNew.add(surveyUserCollectionNewSurveyUserToAttach);
            }
            surveyUserCollectionNew = attachedSurveyUserCollectionNew;
            user.setSurveyUserCollection(surveyUserCollectionNew);
            user = em.merge(user);
            for (SurveyUser surveyUserCollectionOldSurveyUser : surveyUserCollectionOld) {
                if (!surveyUserCollectionNew.contains(surveyUserCollectionOldSurveyUser)) {
                    surveyUserCollectionOldSurveyUser.setUserId(null);
                    surveyUserCollectionOldSurveyUser = em.merge(surveyUserCollectionOldSurveyUser);
                }
            }
            for (SurveyUser surveyUserCollectionNewSurveyUser : surveyUserCollectionNew) {
                if (!surveyUserCollectionOld.contains(surveyUserCollectionNewSurveyUser)) {
                    User oldUserIdOfSurveyUserCollectionNewSurveyUser = surveyUserCollectionNewSurveyUser.getUserId();
                    surveyUserCollectionNewSurveyUser.setUserId(user);
                    surveyUserCollectionNewSurveyUser = em.merge(surveyUserCollectionNewSurveyUser);
                    if (oldUserIdOfSurveyUserCollectionNewSurveyUser != null && !oldUserIdOfSurveyUserCollectionNewSurveyUser.equals(user)) {
                        oldUserIdOfSurveyUserCollectionNewSurveyUser.getSurveyUserCollection().remove(surveyUserCollectionNewSurveyUser);
                        oldUserIdOfSurveyUserCollectionNewSurveyUser = em.merge(oldUserIdOfSurveyUserCollectionNewSurveyUser);
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
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            Collection<SurveyUser> surveyUserCollection = user.getSurveyUserCollection();
            for (SurveyUser surveyUserCollectionSurveyUser : surveyUserCollection) {
                surveyUserCollectionSurveyUser.setUserId(null);
                surveyUserCollectionSurveyUser = em.merge(surveyUserCollectionSurveyUser);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
