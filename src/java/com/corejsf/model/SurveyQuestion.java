/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "SurveyQuestion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SurveyQuestion.findAll", query = "SELECT s FROM SurveyQuestion s"),
    @NamedQuery(name = "SurveyQuestion.findByChoice1", query = "SELECT s FROM SurveyQuestion s WHERE s.choice1 = :choice1"),
    @NamedQuery(name = "SurveyQuestion.findByChoice2", query = "SELECT s FROM SurveyQuestion s WHERE s.choice2 = :choice2"),
    @NamedQuery(name = "SurveyQuestion.findByChoice3", query = "SELECT s FROM SurveyQuestion s WHERE s.choice3 = :choice3"),
    @NamedQuery(name = "SurveyQuestion.findByChoice4", query = "SELECT s FROM SurveyQuestion s WHERE s.choice4 = :choice4"),
    @NamedQuery(name = "SurveyQuestion.findByChoice5", query = "SELECT s FROM SurveyQuestion s WHERE s.choice5 = :choice5"),
    @NamedQuery(name = "SurveyQuestion.findBySurveyQuestionId", query = "SELECT s FROM SurveyQuestion s WHERE s.surveyQuestionId = :surveyQuestionId")})
public class SurveyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "choice1")
    private String choice1;
    @Size(max = 255)
    @Column(name = "choice2")
    private String choice2;
    @Size(max = 255)
    @Column(name = "choice3")
    private String choice3;
    @Size(max = 255)
    @Column(name = "choice4")
    private String choice4;
    @Size(max = 255)
    @Column(name = "choice5")
    private String choice5;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "surveyQuestionId")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer surveyQuestionId;
    @OneToMany(mappedBy = "surveyQuestionId")
    private Collection<SurveyUserAnswer> surveyUserAnswerCollection;
    @JoinColumn(name = "surveyId", referencedColumnName = "surveyId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Survey surveyId;
    @JoinColumn(name = "questionId", referencedColumnName = "questionId")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Question questionId;

    public SurveyQuestion() {
    }

    public SurveyQuestion(Integer surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getChoice5() {
        return choice5;
    }

    public void setChoice5(String choice5) {
        this.choice5 = choice5;
    }

    public Integer getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(Integer surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    @XmlTransient
    public Collection<SurveyUserAnswer> getSurveyUserAnswerCollection() {
        return surveyUserAnswerCollection;
    }

    public void setSurveyUserAnswerCollection(Collection<SurveyUserAnswer> surveyUserAnswerCollection) {
        this.surveyUserAnswerCollection = surveyUserAnswerCollection;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyQuestionId != null ? surveyQuestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyQuestion)) {
            return false;
        }
        SurveyQuestion other = (SurveyQuestion) object;
        if ((this.surveyQuestionId == null && other.surveyQuestionId != null) || (this.surveyQuestionId != null && !this.surveyQuestionId.equals(other.surveyQuestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.corejsf.model.SurveyQuestion[ surveyQuestionId=" + surveyQuestionId + " ]";
    }

}
