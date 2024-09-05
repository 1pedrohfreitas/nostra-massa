package dev.pedrohfreitas.genericstore.configs;

public class UserLocalStorage {
	
	private static ThreadLocal<String> user = new ThreadLocal<>();
	
	public UserLocalStorage() {
	}
	
	public static void setUserID(String userID) {
		if(userID != null) {
			user.set(userID);
		} else {
			user.set("public");
		}
	}
	
	public static String getUserID() {
		return user.get();
	}
	
	public static void clear() {
		user.remove();
	}

}
