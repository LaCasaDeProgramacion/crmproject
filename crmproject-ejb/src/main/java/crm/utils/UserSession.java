/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm.utils;

import java.sql.Date;

import crm.entities.Roles;
import crm.entities.User;

public final class UserSession {

    private static UserSession instance;

    private String username, email, confirm,token,firstName,lastName,password;
    private int id,cin;
    private Roles role;
    private Date dateBirth;
    private boolean enabled;
    

    

    public UserSession(String username, String password, int id) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
	}



	public UserSession(String username, String email, String confirm, String token, String firstName, String lastName,
			String password, int id, int cin, Roles role, Date dateBirth, boolean enabled) {
		super();
		this.username = username;
		this.email = email;
		this.confirm = confirm;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.id = id;
		this.cin = cin;
		this.role = role;
		this.dateBirth = dateBirth;
		this.enabled = enabled;
	}



	public UserSession() {
    }
    
    

    public static UserSession getInstance(User user) {
        //if (instance == null) {
            instance = new UserSession(user.getUsername(), user.getEmail(), user.getConfirm(), user.getToken(), user.getFirstName(), 
            		user.getLastName(), user.getPassword(), user.getId(), user.getCin(), user.getRole(), user.getDateBirth(), user.isEnabled());
        //}
        return instance;
    }

    public static UserSession getInstance() {
        if(instance == null)
        {
            instance = new UserSession();
        }
        return instance;
    }

    /*public static UserSession getInstance(String username, String email) {
        if (instance == null) {
            instance = new UserSession(username, email);
        }
        return instance;
    }*/

   

    public void cleanUserSession() {

        instance = null;

    }



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getConfirm() {
		return confirm;
	}



	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getCin() {
		return cin;
	}



	public void setCin(int cin) {
		this.cin = cin;
	}



	public Roles getRole() {
		return role;
	}



	public void setRole(Roles role) {
		this.role = role;
	}



	public Date getDateBirth() {
		return dateBirth;
	}



	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public static void setInstance(UserSession instance) {
		UserSession.instance = instance;
	}

   

}
