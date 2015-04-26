package com.corejsf.model;

import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.SurveyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(Survey.class)
public class Survey_ { 

    public static volatile SingularAttribute<Survey, Integer> surveyId;
    public static volatile SingularAttribute<Survey, String> updatedBy;
    public static volatile SingularAttribute<Survey, Date> surveyDate;
    public static volatile SingularAttribute<Survey, String> surveyDesc;
    public static volatile SingularAttribute<Survey, Integer> surveyTrNo;
    public static volatile SingularAttribute<Survey, String> deletedBy;
    public static volatile CollectionAttribute<Survey, SurveyUser> surveyUserCollection;
    public static volatile SingularAttribute<Survey, Date> createdAt;
    public static volatile SingularAttribute<Survey, Date> deletedAt;
    public static volatile SingularAttribute<Survey, String> createdBy;
    public static volatile CollectionAttribute<Survey, SurveyQuestion> surveyQuestionCollection;
    public static volatile SingularAttribute<Survey, String> remarks;
    public static volatile SingularAttribute<Survey, Date> updatedAt;

}