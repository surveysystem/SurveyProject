package com.corejsf;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

   // or import javax.faces.bean.SessionScoped;
 
@Named("aaaaa") // or @ManagedBean(name="user")  
@SessionScoped     
public class User implements Serializable {
  private String name;
  private String password;
  private String email;
  private Date dateOfBirth;
  private String address;
  private String state;
  private int zip;
  private int age;
  private boolean isLogin;
  
  
  public String getName() { return name; }
  public void setName(String newValue) { name = newValue; }
    
  public String getPassword() { return password; }
  public void setPassword(String newValue) { password = newValue; }  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
  
    public void checkLogin(ComponentSystemEvent event)
    {
        if(!isLogin)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler)context.getApplication().getNavigationHandler();
            handler.performNavigation("index");
        }
    }
    public String Login()
    {
        isLogin =true;
        return "TeaStore";
    }
  
    public String Logout()
    {
        isLogin =false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
}
