/**
 * This file is part of the Simple Data Fix Language (SDFL) core.
 * 
 * All components of the language (compiler, interpreter, etc.) are
 * free and open source: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SDFL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SDFL.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Sylvain Cloutier
 */
package com.sdfl.statements.builders.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdfl.exceptions.parsing.ExpectedEndOfStatementException;
import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.DeleteFromStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Test suite for the delete statement builder
 * @author Sylvain Cloutier
 */
public class DeleteFromStatementBuilderTest extends BuilderTestClass {
	private static final int COLUMN_COUNT_1 = 1;
	private static final String MY_TABLE = "MY_TABLE";
	private static final String VALID_DELETE_STATEMENT = "delete from MY_TABLE using template \"Value 1\" -> MY_COL_1";
	private static final String INVALID_DELETE_STATEMENT_MISSING_FROM = "delete MY_TABLE using template \"Value 1\" -> MY_COL_1";
	private static final String INVALID_DELETE_STATEMENT_MISSING_TEMPLATE = "delete MY_TABLE";
	private static final String INVALID_DELETE_STATEMENT_TOO_MANY_TOKENS = VALID_DELETE_STATEMENT + " token";
	
	private DeleteFromStatement statement;
	
	@Test
	public void testThatTableNameIsSet() {
		this.buildStatementBasedOnCode(VALID_DELETE_STATEMENT);
		assertEquals(MY_TABLE, this.statement.getTableName());
	}
	
	@Test
	public void testThatTemplateHasTheProperNumberOfColumns() {
		this.buildStatementBasedOnCode(VALID_DELETE_STATEMENT);
		assertEquals(COLUMN_COUNT_1, this.statement.getTemplate().getColumns().count());
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatFromKeywordIsMandatory() {
		this.buildStatementBasedOnCode(INVALID_DELETE_STATEMENT_MISSING_FROM);
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatTemplateIsMandatory() {
		this.buildStatementBasedOnCode(INVALID_DELETE_STATEMENT_MISSING_TEMPLATE);
	}
	
	@Test(expected = ExpectedEndOfStatementException.class)
	public void testThatStatementShouldEndAfterTemplate() {
		this.buildStatementBasedOnCode(INVALID_DELETE_STATEMENT_TOO_MANY_TOKENS);
	}
	
	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (DeleteFromStatement) new DeleteFromStatementBuilder(lTokens).build();
	}

}
