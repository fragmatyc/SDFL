package com.sdfl.statements.tokenizer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StatementTokenizerTest {

	private static final int EIGHT_TOKENS = 8;
	private static final String IN_PACKAGE_STATEMENT_CODE = "In package 2014JUN10PM";
	private static final String IN_PACKAGE_STATEMENT_CODE_MULTISPACE = "In      package      2014JUN10PM";
	private static final String STATEMENT_CODE_WITH_SYNTAX_WITHOUT_SPACER = "using template test->value";
	private static final String STATEMENT_CODE_WITH_KEYWORDS_NOT_SPACED = "usingtemplate test->value";
	private static final String STATEMENT_WITH_STRING = 
			"insert into MY_TABLE using template \"My value as String, with comma\" -> MY_COLUMN_NAME1, \"My second value with arrow -> here\" -> MY_COLUMN_NAME2";
	private static final String STATEMENT_STRING_LIMIT_LEFT = "insert into WIRING_DIAG_SHEET_REVS using template \"-GC6980001\" -> DRAWING_NO";
	private static final String STATEMENT_STRING_LIMIT_RIGHT = "insert into WIRING_DIAG_SHEET_REVS using template \"GC6980001-\" -> DRAWING_NO";
	private static final String STATEMENT_STRING_IS_SYNTAX = "insert into WIRING_DIAG_SHEET_REVS using template \"-\" -> DRAWING_NO";
	private static final String STATEMENT_STRING_WITH_CONDITIONS = "only if MY_COLUMN_1 = \"1\" and (MY_COLUMN_2 = \"1\" or MY_COLUMN_1 \"2\")";
	
	private static final String EMPTY_STATEMENT_CODE = "";
	private static final int THREE_TOKENS = 3;
	private static final int FIVE_TOKENS = 5;
	private static final int TWELVE_TOKENS = 12;
	private static final int FOUR_TOKENS = 4;
	private static final int ZERO_TOKEN = 0;

	private StatementTokenizer tokenizer;
	
	@Before
	public void setup() {
		tokenizer = new StatementTokenizer();
	}
	
	@Test
	public void testThatSyntaxWithoutSpacerAreTokenized() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_CODE_WITH_SYNTAX_WITHOUT_SPACER);
		assertEquals(FIVE_TOKENS, lTokens.count());
	}
	
	@Test
	public void testThatKeywordsWithoutSpacerAreNotTokenized() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_CODE_WITH_KEYWORDS_NOT_SPACED);
		assertEquals(FOUR_TOKENS, lTokens.count());
	}
	
	@Test
	public void testThatLineOfCodeGetsTokenizedAtSpaces() {
		StatementTokens lTokens = tokenizer.tokenize(IN_PACKAGE_STATEMENT_CODE);
		assertEquals(THREE_TOKENS, lTokens.count());
	}
	
	@Test
	public void testThatEmptyLineOfCodeReturnsZeroTokens() {
		StatementTokens lTokens = tokenizer.tokenize(EMPTY_STATEMENT_CODE);
		assertEquals(ZERO_TOKEN, lTokens.count());
	}
	
	@Test
	public void testThatMultipleSpacesAreNotTakenCareOf() {
		StatementTokens lTokens = tokenizer.tokenize(IN_PACKAGE_STATEMENT_CODE_MULTISPACE);
		assertEquals(THREE_TOKENS, lTokens.count());
	}
	
	@Test
	public void testThatStringAreNotTokenizedAtSpaces() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_WITH_STRING);
		assertEquals(TWELVE_TOKENS, lTokens.count());
	}
	
	@Test
	public void testStringLeftLimitsWithSyntaxTokens() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_STRING_LIMIT_LEFT);
		assertEquals(EIGHT_TOKENS, lTokens.count());
	}
	
	@Test
	public void testStringRightLimitsWithSyntaxTokens() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_STRING_LIMIT_RIGHT);
		assertEquals(EIGHT_TOKENS, lTokens.count());
	}
	
	@Test
	public void testStringIsSyntaxTokens() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_STRING_IS_SYNTAX);
		assertEquals(EIGHT_TOKENS, lTokens.count());
	}
	
	@Test
	public void testStringWithConditions() {
		StatementTokens lTokens = tokenizer.tokenize(STATEMENT_STRING_WITH_CONDITIONS);
		assertEquals(14, lTokens.count());
	}
}
