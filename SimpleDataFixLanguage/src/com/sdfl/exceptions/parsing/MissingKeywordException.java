package com.sdfl.exceptions.parsing;

@SuppressWarnings("serial")
public class MissingKeywordException extends ParsingException {
	private String keywordFound;
	private String[] missingEitherKeywords;
	
	public MissingKeywordException(String pFound, String... pKeyword) {
		this.missingEitherKeywords = pKeyword;
		this.keywordFound = pFound;
	}

	public String[] getMissingEitherKeywords() {
		return missingEitherKeywords;
	}

	public String getKeywordFound() {
		return this.keywordFound;
	}
	
}
