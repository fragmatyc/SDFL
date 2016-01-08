package com.sdfl.exceptions.parsing;

@SuppressWarnings("serial")
public class InvalidSyntaxException extends ParsingException {
	private final String expectedSyntax;
	private final String syntaxFound;
	
	public InvalidSyntaxException(String pKeywordFound) {
		this(null, pKeywordFound);
	}
	
	public InvalidSyntaxException(String pExpected, String pFound) {
		this.expectedSyntax = pExpected;
		this.syntaxFound = pFound;
	}

	public InvalidSyntaxException() {
		this.expectedSyntax = null;
		this.syntaxFound = null;
	}

	public String getExpectedSyntax() {
		return expectedSyntax;
	}

	public String getSyntaxFound() {
		return syntaxFound;
	}
}
