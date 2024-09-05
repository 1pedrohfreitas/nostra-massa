package dev.pedrohfreitas.genericstore.configs;

public class TenantLocalStorage {
	
	private static ThreadLocal<String> tenant = new ThreadLocal<>();
	
	public TenantLocalStorage() {
	}
	
	public static void setTenantID(String tenantID) {
		if(tenantID != null) {
			tenant.set(tenantID);
		} else {
			tenant.set("public");
		}
	}
	
	public static String getTenantID() {
		return tenant.get();
	}
	
	public static void clear() {
		tenant.remove();
	}

}
