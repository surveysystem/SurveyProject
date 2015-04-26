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
@Table(name = "Question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findByQuestionId", query = "SELECT q FROM Question q WHERE q.questionId = :questionId"),
    @NamedQuery(name = "Question.findByCreatedBy", query = "SELECT q FROM Question q WHERE q.createdBy = :createdBy"),
    @NamedQuery(name = "Question.findByCreatedAt", query = "SELECT q FROM Question q WHERE q.createdAt = :createdAt"),
    @NamedQuery(name = "Question.findByDeletedBy", query = "SELECT q FROM Question q WHERE q.deletedBy = :deletedBy"),
    @NamedQuery(name = "Question.findByDeletedAt", query = "SELECT q FROM Question q WHERE q.deletedAt = :deletedAt"),
    @NamedQuery(name = "Question.findByIsActive", query = "SELECT q FROM Question q WHERE q.isActive = :isActive"),
    @NamedQuery(name = "Question.findByQuestionCode", query = "SELECT q FROM Question q WHERE q.questionCode = :questionCode"),
    @NamedQuery(name = "Question.findByQuestionDesc", query = "SELECT q FROM Question q WHERE q.questionDesc = :questionDesc"),
    @NamedQuery(name = "Question.findByQuestionType", query = "SELECT q FROM Question q WHERE q.questionType = :questionType"),
    @NamedQuery(name = "Question.findByUpdatedAt", query = "SELECT q FROM Question q WHERE q.updatedAt = :updatedAt"),
    @NamedQuery(name = "Question.findByUpdatedBy", query = "SELECT q FROM Question q WHERE q.updatedBy = :updatedBy")})
public class Question implements Serializable {
    @OneToMany(mappedBy = "questionId")
    private Collection<SurveyQuestion> surveyQuestionCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "questionId")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer questionId;
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
    @Column(name = "isActive")
    private Boolean isActive;
    @Size(max = 255)
    @Column(name = "questionCode")
    private String questionCode;
    @Size(max = 255)
    @Column(name = "questionDesc")
    private String questionDesc;
    @Column(name = "questionType")
    private Integer questionType;
    @Column(name = "updatedAt")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Size(max = 255)
    @Column(name = "updatedBy")
    private String updatedBy;

    public Question() {
    }

    public Question(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
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
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.corejsf.model.Question[ questionId=" + questionId + " ]";
    }

    @XmlTransient
    public Collection<SurveyQuestion> getSurveyQuestionCollection() {
        return surveyQuestionCollection;
    }

    public void setSurveyQuestionCollection(Collection<SurveyQuestion> surveyQuestionCollection) {
        this.surveyQuestionCollection = surveyQuestionCollection;
    }
    
}
