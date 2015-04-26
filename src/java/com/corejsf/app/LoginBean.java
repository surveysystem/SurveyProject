/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author user
 */
@Named("login")
@SessionScoped
public class LoginBean implements Serializable {

    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private User user;
    private String username, password;

    public LoginBean() {
    }

    public String doLogin() {
        EntityManager em = emf.createEntityManager();
        try {
            user = (User) em.createNamedQuery("User.findByUserName").setParameter("userName", username).getSingleResult();

            if (!user.getPassword().equals(password)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Wrong Password"));
                user = null;
                return null;
            }
            if (user.getUserType() == 1) {
                return "ViewUsers";
            } else { //regular users
                return "UserDashBoard";
            }
        } catch (NoResultException nre) {
            user = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Bad Username"));
            return null;
        } catch (Exception ex) {
            user = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Something went wrong, please try again later"));
            return null;
        } finally {
            em.close();
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void checkLogin(ComponentSystemEvent event) {
        if (user == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            handler.performNavigation("index");
        }
    }

    public String logout() {
        user = null;
        return "index?face-redirect=true";
    }

}
