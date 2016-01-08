package com.sdfl.exceptions.parsing;

@SuppressWarnings("serial")
public class ExpectedEndOfStatementException extends ParsingException {
	private String tokenFound;
	
	public ExpectedEndOfStatementException(String pTokenFound) {
		this.tokenFound = pTokenFound;
	}

	public String getTokenFound() {
		return this.tokenFound;
	}
	
}
