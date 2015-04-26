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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "Survey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT s FROM Survey s"),
    @NamedQuery(name = "Survey.findBySurveyId", query = "SELECT s FROM Survey s WHERE s.surveyId = :surveyId"),
    @NamedQuery(name = "Survey.findByCreatedBy", query = "SELECT s FROM Survey s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "Survey.findByCreatedAt", query = "SELECT s FROM Survey s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Survey.findByDeletedBy", query = "SELECT s FROM Survey s WHERE s.deletedBy = :deletedBy"),
    @NamedQuery(name = "Survey.findByDeletedAt", query = "SELECT s FROM Survey s WHERE s.deletedAt = :deletedAt"),
    @NamedQuery(name = "Survey.findByRemarks", query = "SELECT s FROM Survey s WHERE s.remarks = :remarks"),
    @NamedQuery(name = "Survey.findBySurveyDate", query = "SELECT s FROM Survey s WHERE s.surveyDate = :surveyDate"),
    @NamedQuery(name = "Survey.findBySurveyDesc", query = "SELECT s FROM Survey s WHERE s.surveyDesc = :surveyDesc"),
    @NamedQuery(name = "Survey.findBySurveyTrNo", query = "SELECT s FROM Survey s WHERE s.surveyTrNo = :surveyTrNo"),
    @NamedQuery(name = "Survey.findByUpdatedAt", query = "SELECT s FROM Survey s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "Survey.findByUpdatedBy", query = "SELECT s FROM Survey s WHERE s.updatedBy = :updatedBy")})
public class Survey implements Serializable {

    @OneToMany(mappedBy = "surveyId")
    private Collection<SurveyUser> surveyUserCollection;
    @OneToMany(mappedBy = "surveyId")
    private Collection<SurveyQuestion> surveyQuestionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "surveyId")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer surveyId;
    @Size(max = 255)
    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "createdAt")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Size(max = 255)
    @Column(name = "deletedBy")
    private String deletedBy;
    @Column(name = "deletedAt")
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    @Size(max = 255)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "surveyDate")
    @Temporal(TemporalType.DATE)
    private Date surveyDate;
    @Size(max = 255)
    @Column(name = "surveyDesc")
    private String surveyDesc;
    @Column(name = "surveyTrNo")
    private Integer surveyTrNo;
    @Column(name = "updatedAt")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Size(max = 255)
    @Column(name = "updatedBy")
    private String updatedBy;

    public Survey() {
    }

    public Survey(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getSurveyDesc() {
        return surveyDesc;
    }

    public void setSurveyDesc(String surveyDesc) {
        this.surveyDesc = surveyDesc;
    }

    public Integer getSurveyTrNo() {
        return surveyTrNo;
    }

    public void setSurveyTrNo(Integer surveyTrNo) {
        this.surveyTrNo = surveyTrNo;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyId != null ? surveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.surveyId == null && other.surveyId != null) || (this.surveyId != null && !this.surveyId.equals(other.surveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.corejsf.model.Survey[ surveyId=" + surveyId + " ]";
    }

    @XmlTransient
    public Collection<SurveyUser> getSurveyUserCollection() {
        return surveyUserCollection;
    }

    public void setSurveyUserCollection(Collection<SurveyUser> surveyUserCollection) {
        this.surveyUserCollection = surveyUserCollection;
    }

    @XmlTransient
    public Collection<SurveyQuestion> getSurveyQuestionCollection() {
        return surveyQuestionCollection;
    }

    public void setSurveyQuestionCollection(Collection<SurveyQuestion> surveyQuestionCollection) {
        this.surveyQuestionCollection = surveyQuestionCollection;
    }

}
