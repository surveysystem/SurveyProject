package com.corejsf.model;

import com.corejsf.model.Survey;
import com.corejsf.model.SurveyUserAnswer;
import com.corejsf.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(SurveyUser.class)
public class SurveyUser_ { 

    public static volatile SingularAttribute<SurveyUser, Survey> surveyId;
    public static volatile CollectionAttribute<SurveyUser, SurveyUserAnswer> surveyUserAnswerCollection;
    public static volatile SingularAttribute<SurveyUser, Integer> surveyUserId;
    public static volatile SingularAttribute<SurveyUser, Boolean> isFinished;
    public static volatile SingularAttribute<SurveyUser, User> userId;
    public static volatile SingularAttribute<SurveyUser, Date> finishedAt;

}