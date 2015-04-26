package com.corejsf.model;

import com.corejsf.model.SurveyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-22T22:20:16")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> updatedBy;
    public static volatile SingularAttribute<User, Boolean> isActive;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> deletedBy;
    public static volatile SingularAttribute<User, String> userCode;
    public static volatile CollectionAttribute<User, SurveyUser> surveyUserCollection;
    public static volatile SingularAttribute<User, Date> createdAt;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, Date> deletedAt;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> createdBy;
    public static volatile SingularAttribute<User, String> phone;
    public static volatile SingularAttribute<User, Short> userType;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Date> updatedAt;

}