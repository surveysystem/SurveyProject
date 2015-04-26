/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "SurveyUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SurveyUser.findAll", query = "SELECT s FROM SurveyUser s"),
    @NamedQuery(name = "SurveyUser.findBySurveyUserId", query = "SELECT s FROM SurveyUser s WHERE s.surveyUserId = :surveyUserId"),
    @NamedQuery(name = "SurveyUser.findByUserId", query = "SELECT s FROM SurveyUser s WHERE s.userId = :userId"),
    @NamedQuery(name = "SurveyUser.findBySurveyId", query = "SELECT s FROM SurveyUser s WHERE s.surveyId = :surveyId"),
    @NamedQuery(name = "SurveyUser.findByFinishedAt", query = "SELECT s FROM SurveyUser s WHERE s.finishedAt = :finishedAt"),
    @NamedQuery(name = "SurveyUser.findByIsFinished", query = "SELECT s FROM SurveyUser s WHERE s.isFinished = :isFinished")})
public class SurveyUser implements Serializable {
    @OneToMany(mappedBy = "surveyUserId", cascade = CascadeType.PERSIST)
    private Collection<SurveyUserAnswer> surveyUserAnswerCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "surveyUserId")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer surveyUserId;
    @Column(name = "finishedAt")
    @Temporal(TemporalType.DATE)
    private Date finishedAt;
    @Column(name = "isFinished")
    private Boolean isFinished;
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User userId;
    @JoinColumn(name = "surveyId", referencedColumnName = "surveyId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Survey surveyId;

    public SurveyUser() {
    }

    public SurveyUser(Integer surveyUserId) {
        this.surveyUserId = surveyUserId;
    }

    public Integer getSurveyUserId() {
        return surveyUserId;
    }

    public void setSurveyUserId(Integer surveyUserId) {
        this.surveyUserId = surveyUserId;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyUserId != null ? surveyUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyUser)) {
            return false;
        }
        SurveyUser other = (SurveyUser) object;
        if ((this.surveyUserId == null && other.surveyUserId != null) || (this.surveyUserId != null && !this.surveyUserId.equals(other.surveyUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.corejsf.model.SurveyUser[ surveyUserId=" + surveyUserId + " ]";
    }

    @XmlTransient
    public Collection<SurveyUserAnswer> getSurveyUserAnswerCollection() {
        return surveyUserAnswerCollection;
    }

    public void setSurveyUserAnswerCollection(Collection<SurveyUserAnswer> surveyUserAnswerCollection) {
        this.surveyUserAnswerCollection = surveyUserAnswerCollection;
    }
    
}
