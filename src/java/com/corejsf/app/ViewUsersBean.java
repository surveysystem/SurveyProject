/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.app;

import com.corejsf.model.User;
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
@Named("usersBean")
@SessionScoped
public class ViewUsersBean implements Serializable {

    @PersistenceUnit(unitName = "SurveyProjectPU")
    EntityManagerFactory emf;

    private List<User> users;

    public ViewUsersBean() {

    }

    public String editUser() {
        return "AddUser";
    }

    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("User.findAll");
            this.users = query.getResultList();
        } finally {
            em.close();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
