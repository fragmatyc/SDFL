package com.sdfl.exceptions.parsing;

@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {
	public ParsingException() {}
	public ParsingException(String pMessage) {
		super(pMessage);
	}
}	
