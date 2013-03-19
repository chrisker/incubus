package com.incubus.rule;

public abstract class OverRuledTransaction extends Exception {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 4360174147614375964L;
	private FunctionalRule functionRule;

	protected OverRuledTransaction(FunctionalRule functionRule) {
		super();
		this.functionRule = functionRule;
	}

	public FunctionalRule getFunctionRule() {
		return functionRule;
	}

	
}
