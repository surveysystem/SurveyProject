package com.corejsf.model;

import com.corejsf.model.SurveyQuestion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(Question.class)
public class Question_ { 

    public static volatile SingularAttribute<Question, String> questionDesc;
    public static volatile SingularAttribute<Question, Date> createdAt;
    public static volatile SingularAttribute<Question, Date> deletedAt;
    public static volatile SingularAttribute<Question, Integer> questionId;
    public static volatile SingularAttribute<Question, String> updatedBy;
    public static volatile SingularAttribute<Question, String> createdBy;
    public static volatile SingularAttribute<Question, String> questionCode;
    public static volatile CollectionAttribute<Question, SurveyQuestion> surveyQuestionCollection;
    public static volatile SingularAttribute<Question, Boolean> isActive;
    public static volatile SingularAttribute<Question, Integer> questionType;
    public static volatile SingularAttribute<Question, String> deletedBy;
    public static volatile SingularAttribute<Question, Date> updatedAt;

}