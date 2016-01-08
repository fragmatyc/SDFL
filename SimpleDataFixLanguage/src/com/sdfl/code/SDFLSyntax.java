package com.sdfl.code;

import java.util.List;

public abstract class SDFLSyntax extends SDFLGrammar {

	public static final String ARROW = "->";
	public static final String OPEN_BRACKET = "(";
	public static final String CLOSED_BRACKET = ")";
	public static final String DESCRIPTION_ID_SEPARATOR = "-";
	public static final String COMMA = ",";
	public static final String EQUAL = "=";
	public static final String NOT_EQUAL = "!=";
	public static final String GREATER_THAN = ">";
	public static final String SMALLER_THAN = "<";

	public static boolean isSyntax(String lCurrentToken) {
		return SDFLGrammar.isValidGrammar(SDFLSyntax.values(), lCurrentToken);
	}
	
	public static List<String> values() {
		return SDFLGrammar.valuesOf(SDFLSyntax.class);
	}

}
