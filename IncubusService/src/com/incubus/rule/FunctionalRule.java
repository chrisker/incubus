package com.incubus.rule;

public enum FunctionalRule {
	ENTITY_EQUITY("EQUITY IS MANDATORY"),
	ENTITY_SEGREGATION("SEGREGATION IS MANDATORY");
	
	private String message;

	private FunctionalRule(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
