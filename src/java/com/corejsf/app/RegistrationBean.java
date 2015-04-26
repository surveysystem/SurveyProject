/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.User;
import com.corejsf.model.dao.UserJpaController;
import com.corejsf.model.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
@Named("registration")
@RequestScoped
public class RegistrationBean implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private User user;

    public RegistrationBean() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String doRegistration() throws RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();
        try {
            User temp = (User) em.createNamedQuery("User.findByUserName").setParameter("userName", user.getUserName()).getSingleResult();
            FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Username Already Exists, Please choose another one"));
            return null;
        } catch (NoResultException nre) {
            user.setUserType(new Short("2"));
            UserJpaController controller = new UserJpaController(utx, emf);
            controller.create(user);
            return "ViewUsers";
        } finally {
            em.close();
        }
        
    }
}
