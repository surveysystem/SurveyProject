package com.corejsf.model;

import com.corejsf.model.SurveyQuestion;
import com.corejsf.model.SurveyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(SurveyUserAnswer.class)
public class SurveyUserAnswer_ { 

    public static volatile SingularAttribute<SurveyUserAnswer, SurveyQuestion> surveyQuestionId;
    public static volatile SingularAttribute<SurveyUserAnswer, String> answerText;
    public static volatile SingularAttribute<SurveyUserAnswer, SurveyUser> surveyUserId;
    public static volatile SingularAttribute<SurveyUserAnswer, Integer> surveyUserAnswerId;
    public static volatile SingularAttribute<SurveyUserAnswer, Integer> answerChoice;

}