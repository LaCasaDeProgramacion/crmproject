package crm.utils;

import java.security.SecureRandom;

public class codeGen {
	
		private static codeGen instance;
	  
	    public static codeGen getInstance(){
	        if(instance==null) 
	            instance=new codeGen();
	        return instance;
	    }

	    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    static SecureRandom rnd = new SecureRandom();

	    public String randomString(int len) 
	    {
	        StringBuilder sb = new StringBuilder(len);
	        for (int i = 0; i < len; i++) {
	            sb.append(AB.charAt(rnd.nextInt(AB.length())));
	        }
	        return sb.toString();
	    }


}
