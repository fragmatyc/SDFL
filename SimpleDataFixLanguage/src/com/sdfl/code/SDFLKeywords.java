package com.sdfl.code;

import java.util.List;

public abstract class SDFLKeywords extends SDFLGrammar {
	public static final String IN = "in";
	public static final String PACKAGE = "package";
	public static final String CREATE = "create";
	public static final String DATAFIX = "datafix";
	public static final String IMPORT = "import";
	public static final String INTO = "into";
	public static final String USING = "using";
	public static final String TEMPLATE = "template";
	public static final String WITH = "with";
	public static final String HEADERS = "headers";
	public static final String UPDATE = "update";
	public static final String ONLY = "only";
	public static final String IF = "if";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String NOT = "not";
	public static final String EXIST = "exist";
	public static final String INSERT = "insert";
	public static final String DELETE = "delete";
	public static final String STEP = "step";
	public static final String FROM = "from";
	
	public static boolean isKeyword(String lCurrentToken) {
		return SDFLGrammar.isValidGrammar(SDFLKeywords.values(), lCurrentToken);
	}
	
	public static List<String> values() {
		return SDFLGrammar.valuesOf(SDFLKeywords.class);
	}
}
