package com.sdfl.statements.builders.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sdfl.exceptions.parsing.ExpectedEndOfStatementException;
import com.sdfl.exceptions.parsing.InvalidSyntaxException;
import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.exceptions.parsing.UnexpectedKeywordException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

public class ImportStatementBuilderTest extends BuilderTestClass {
	private static final String MODSUM_NUMBER = "\"Modsum number\"";
	private static final String MOD_NO = "MOD_NO";
	private static final String MY_COLUMN_2 = "my_column_2";
	private static final String COLUMN_VALUE_2 = "#2";
	private static final int THREE_COLUMNS = 3;
	private static final int ZERO_COLUMNS = 0;
	private static final String EXPECTED_TABLE_NAME = "my_table";
	private static final String EXPECTED_INPUT_FILE = "myfile.csv";
	private static final String VALID_IMPORT_WITHOUT_TEMPLATE = 
			"import " + EXPECTED_INPUT_FILE + " into " + EXPECTED_TABLE_NAME;
	private static final String VALID_IMPORT_WITH_TEMPLATE = 
			"import " + EXPECTED_INPUT_FILE + " into " + EXPECTED_TABLE_NAME + " using template #1 -> my_column_1, #2 -> my_column_2, #3 -> my_column_3";
	private static final String INVALID_STATEMENT_MISSING_ARROW =
			"import myFile.csv into MY_TABLE using template #1 my_column_1, #2 -> my_column_2, #3 -> my_column_3";
	private static final String INVALID_STATEMENT_MISSING_COMMA =
			"import myFile.csv into MY_TABLE using template #1 -> my_column_1, #2 -> my_column_2 #3 -> my_column_3";
	private static final String INVALID_STATEMENT_MISSING_USING_KEYWORD =
			"import myFile.csv into MY_TABLE template #1 -> my_column_1, #2 -> my_column_2, #3 -> my_column_3";
	private static final String INVALID_STATEMENT_MISSING_TEMAPLATE_KEYWORD =
			"import myFile.csv into MY_TABLE using #1 -> my_column_1, #2 -> my_column_2, #3 -> my_column_3";
	private static final String IMPORT_STATEMENT_WITH_HEADERS_KEYWORD = 
			"import MyImportFile.xlsx with headers into MODSUM_REVISIONS using template \"Modsum number\" -> MOD_NO, \"Modsum revsion\" -> MOD_REV";
	private static final String IMPORT_STATEMENT_NOT_ENDED_PROPERLY = 
			VALID_IMPORT_WITH_TEMPLATE + " asdasda";
	
	private ImportStatement statement;
	
	@Test
	public void testThatInputFileIsSetProperlyOnImportStatement() {
		buildImportStatementBasedOnCode(VALID_IMPORT_WITHOUT_TEMPLATE);
		assertEquals(EXPECTED_INPUT_FILE, this.statement.getInputFile());
	}
	
	@Test
	public void testThatTableNameIsSetProperlyOnImportStatement() {
		buildImportStatementBasedOnCode(VALID_IMPORT_WITHOUT_TEMPLATE);
		assertEquals(EXPECTED_TABLE_NAME, this.statement.getTableName());
	}
	
	@Test
	public void testThatTemplateIsEmptyIfNotProvided() {
		buildImportStatementBasedOnCode(VALID_IMPORT_WITHOUT_TEMPLATE);
		assertEquals(ZERO_COLUMNS, this.statement.getTemplate().getColumns().count());
	}
	
	@Test
	public void testThatTemplateHasProperNumberOfColumnsWhenDefined() {
		buildImportStatementBasedOnCode(VALID_IMPORT_WITH_TEMPLATE);
		assertEquals(THREE_COLUMNS, this.statement.getTemplate().getColumns().count());
	}
	
	@Test
	public void testThatColumnsCanBeFetchFromTemplate() {
		buildImportStatementBasedOnCode(VALID_IMPORT_WITH_TEMPLATE);
		assertEquals(COLUMN_VALUE_2, this.statement.getTemplate().get(MY_COLUMN_2));
	}
	
	@Test
	public void testThatWithHeadersFlagIsSetWhenKeywordsAreThere() {
		buildImportStatementBasedOnCode(IMPORT_STATEMENT_WITH_HEADERS_KEYWORD);
		assertTrue(this.statement.isWithHeaders());
	}
	
	@Test
	public void testThatTemplateWithHeadersIsProperlySet() {
		buildImportStatementBasedOnCode(IMPORT_STATEMENT_WITH_HEADERS_KEYWORD);
		assertEquals(MODSUM_NUMBER, this.statement.getTemplate().get(MOD_NO));
	}
	
	@Test(expected = InvalidSyntaxException.class)
	public void testThatTemplateWithMissingArrowThrowsAnInvalidSyntaxException() {
		buildImportStatementBasedOnCode(INVALID_STATEMENT_MISSING_ARROW);
	}
	
	@Test(expected = ExpectedEndOfStatementException.class)
	public void testThatTemplateWithMissingCommaThrowsAnExpectedEndOfStatementException() {
		buildImportStatementBasedOnCode(INVALID_STATEMENT_MISSING_COMMA);
	}
	
	@Test(expected = UnexpectedKeywordException.class)
	public void testThatTemplateWithMissingUsingKeywordThrowsAnInvalidSyntaxException() {
		buildImportStatementBasedOnCode(INVALID_STATEMENT_MISSING_USING_KEYWORD);
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatTemplateWithMissingTemplateKeywordThrowsAnInvalidSyntaxException() {
		buildImportStatementBasedOnCode(INVALID_STATEMENT_MISSING_TEMAPLATE_KEYWORD);
	}
	
	@Test(expected = ExpectedEndOfStatementException.class) 
	public void testThatImportStatementShouldBeProperlyEnded() {
		buildImportStatementBasedOnCode(IMPORT_STATEMENT_NOT_ENDED_PROPERLY);
	}

	private void buildImportStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (ImportStatement) new ImportStatementBuilder(lTokens).build();
	}

}
