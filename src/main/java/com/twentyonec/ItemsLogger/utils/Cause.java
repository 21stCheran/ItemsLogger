package com.twentyonec.ItemsLogger.utils;

public enum Cause {

	RESTART("Restart"),
	JOIN("Join"),
	QUIT("Quit"),
	DEATH("Death");
	
	private final String cause;
	
	Cause(final String cause) {
		this.cause = cause;
	}
	
	public String getCause() {
        return this.cause;
    }
	
}
