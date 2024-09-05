package dev.pedrohfreitas.genericstore.configs;

public class TerminalLocalStorage {
	
	private static ThreadLocal<String> terminal = new ThreadLocal<>();
	
	public TerminalLocalStorage() {
	}
	
	public static void setTerminalID(String terminalID) {
		if(terminalID != null) {
			terminal.set(terminalID);
		} else {
			terminal.set("public");
		}
	}
	
	public static String getTerminalID() {
		return terminal.get();
	}
	
	public static void clear() {
		terminal.remove();
	}

}
