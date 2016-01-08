package com.sdfl.statements.builders.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdfl.exceptions.parsing.InvalidSyntaxException;
import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

public class CreateDatafixStatementBuilderTest extends BuilderTestClass {
	private static final String DATAFIX_ID = "MyDataFixID";
	private static final String DATAFIX_DESC = "My data fix description";
	private static final String DATAFIX_STATEMENT_CODE = "create datafix " + DATAFIX_ID + " - " + DATAFIX_DESC;
	private static final String DATAFIX_STATEMENT_CODE_MISSING_DATAFIX_KEYWORD = "create MyDataFixId - MyDescription";
	private static final String DATAFIX_STATEMENT_CODE_MISSING_CREATE_KEYWORD = "datafix MyDataFixId - MyDescription";
	private static final String DATAFIX_STATEMENT_CODE_SPACE_IN_ID = "create datafix MyData FixId - MyDescription";
	private static final String DATAFIX_STATEMENT_CODE_MISSING_DESCRIPTION_SEPARATOR 
			= "create datafix MyDataFixId MyDescription";
	
	private CreateDatafixStatement statement;
	
	@Test
	public void testThatDataFixCreationStatementSetsTheDatafixId() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE);
		assertEquals(DATAFIX_ID, this.statement.getDatafixId());
	}
	
	@Test
	public void testThatDataFixCreationStatementSetsTheDescription() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE);
		assertEquals(DATAFIX_DESC, this.statement.getDatafixDescription());
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatBuildWhenDatafixKeywordIsMissingThrowsAMissingKeywordException() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE_MISSING_DATAFIX_KEYWORD);
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatBuildWhenCreateKeywordIsMissingThrowsAMissingKeywordException() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE_MISSING_CREATE_KEYWORD);
	}
	
	@Test(expected = InvalidSyntaxException.class)
	public void testThatBuildWhenIdHasSpaceThrowsAnInvalidSyntaxException() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE_SPACE_IN_ID);
	}

	@Test(expected = InvalidSyntaxException.class)
	public void testThatBuildWhenMissingDescriptionSeparatorThrowsAnInvalidSyntaxException() {
		buildDataFixCreationStatementBasedOnCode(DATAFIX_STATEMENT_CODE_MISSING_DESCRIPTION_SEPARATOR);
	}
	
	private void buildDataFixCreationStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (CreateDatafixStatement) new CreateDatafixStatementBuilder(lTokens).build();
	}
}
