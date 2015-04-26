/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "SurveyUserAnswer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SurveyUserAnswer.findAll", query = "SELECT s FROM SurveyUserAnswer s"),
    @NamedQuery(name = "SurveyUserAnswer.findBySurveyUserAnswerId", query = "SELECT s FROM SurveyUserAnswer s WHERE s.surveyUserAnswerId = :surveyUserAnswerId"),
    @NamedQuery(name = "SurveyUserAnswer.findByAnswerChoice", query = "SELECT s FROM SurveyUserAnswer s WHERE s.answerChoice = :answerChoice"),
    @NamedQuery(name = "SurveyUserAnswer.findBySurveyUserId", query = "SELECT s FROM SurveyUserAnswer s WHERE s.surveyUserId = :surveyUserId"),
    @NamedQuery(name = "SurveyUserAnswer.findByAnswerText", query = "SELECT s FROM SurveyUserAnswer s WHERE s.answerText = :answerText")})
public class SurveyUserAnswer implements Serializable {
    @JoinColumn(name = "surveyUserId", referencedColumnName = "surveyUserId")
    @ManyToOne
    private SurveyUser surveyUserId;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SurveyUserAnswerId")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer surveyUserAnswerId;
    @Column(name = "answerChoice")
    private Integer answerChoice;
    @Size(max = 1000)
    @Column(name = "answerText")
    private String answerText;
    @JoinColumn(name = "surveyQuestionId", referencedColumnName = "surveyQuestionId")
    @ManyToOne
    private SurveyQuestion surveyQuestionId;

    public SurveyUserAnswer() {
    }

    public SurveyUserAnswer(Integer surveyUserAnswerId) {
        this.surveyUserAnswerId = surveyUserAnswerId;
    }

    public Integer getSurveyUserAnswerId() {
        return surveyUserAnswerId;
    }

    public void setSurveyUserAnswerId(Integer surveyUserAnswerId) {
        this.surveyUserAnswerId = surveyUserAnswerId;
    }

    public Integer getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(Integer answerChoice) {
        this.answerChoice = answerChoice;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public SurveyQuestion getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(SurveyQuestion surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyUserAnswerId != null ? surveyUserAnswerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyUserAnswer)) {
            return false;
        }
        SurveyUserAnswer other = (SurveyUserAnswer) object;
        if ((this.surveyUserAnswerId == null && other.surveyUserAnswerId != null) || (this.surveyUserAnswerId != null && !this.surveyUserAnswerId.equals(other.surveyUserAnswerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.corejsf.model.SurveyUserAnswer[ surveyUserAnswerId=" + surveyUserAnswerId + " ]";
    }

    public SurveyUser getSurveyUserId() {
        return surveyUserId;
    }

    public void setSurveyUserId(SurveyUser surveyUserId) {
        this.surveyUserId = surveyUserId;
    }
    
}
