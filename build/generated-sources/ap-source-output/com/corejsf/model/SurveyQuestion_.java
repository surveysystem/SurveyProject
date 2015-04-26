package com.corejsf.model;

import com.corejsf.model.Question;
import com.corejsf.model.Survey;
import com.corejsf.model.SurveyUserAnswer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(SurveyQuestion.class)
public class SurveyQuestion_ { 

    public static volatile SingularAttribute<SurveyQuestion, Integer> surveyQuestionId;
    public static volatile SingularAttribute<SurveyQuestion, Survey> surveyId;
    public static volatile SingularAttribute<SurveyQuestion, Question> questionId;
    public static volatile CollectionAttribute<SurveyQuestion, SurveyUserAnswer> surveyUserAnswerCollection;
    public static volatile SingularAttribute<SurveyQuestion, String> choice5;
    public static volatile SingularAttribute<SurveyQuestion, String> choice3;
    public static volatile SingularAttribute<SurveyQuestion, String> choice4;
    public static volatile SingularAttribute<SurveyQuestion, String> choice1;
    public static volatile SingularAttribute<SurveyQuestion, String> choice2;

}