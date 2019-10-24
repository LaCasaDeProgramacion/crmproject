package crm.utils;

import java.sql.Date;

import crm.entities.Roles;
import crm.entities.User;

public final class UserSession {

	public static int id;
    public static String username;
    public static String firstname;
    public static String lastname;
    public static String email;
    public static boolean enable;
    public static String password;
    public static Roles roles;
    public static String token;
    public static int cin;
    public static Date dateBirth;

    public UserSession() {
    }

    

    
    public UserSession(User u) {
        id= u.getId();
        username= u.getUsername();
        email= u.getEmail();
        enable=u.isEnabled();
        password= u.getPassword();
        roles= u.getRole();
        token= u.getToken();
        dateBirth=u.getDateBirth();
        cin=u.getCin();
        lastname=u.getLastName();
        firstname=u.getFirstName();
    }
}
