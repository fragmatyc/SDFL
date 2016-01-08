/**
 * This file is part of the Simple Data Fix Language (SDFL) core.
 * 
 * All components of the language (compiler, interpreter, etc.) are
 * free and open source: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.UpdateStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * @author Sylvain Cloutier
 */
public class UpdateStatementBuilderTest extends BuilderTestClass {
	private static final int TWO_COLUMNS = 2;

	public static final String VALID_CODE = "update MY_TABLE using template \"Value 1\" -> MY_COLUMN_1, \"Value 2\" -> MY_COLUMN_2 only if MY_COLUMN_1 = \"10\" and MY_COLUMN_2 != \"11\"";
	public static final String MISSING_TEMPLATE = "update MY_TABLE";
	public static final String TOO_MANY_KEYWORDS = VALID_CODE + " too many keywords";
	
	private UpdateStatement statement;
	
	@Test(expected = MissingKeywordException.class)
	public void testThatTemplateIsMandatory() {
		this.buildStatementBasedOnCode(MISSING_TEMPLATE);
	}
	
	@Test(expected = MissingKeywordException.class)
	public void testThatConditionAssertionsShouldStartWithAppropriateConditionKeyword() {
		this.buildStatementBasedOnCode(TOO_MANY_KEYWORDS);
	}
	
	@Test
	public void testThatTableNameIsSet() {
		this.buildStatementBasedOnCode(VALID_CODE);
		assertEquals("MY_TABLE", this.statement.getTableName());
	}
	
	@Test
	public void testThatTemplateIsSetWithProperNumberOfColumns() {
		this.buildStatementBasedOnCode(VALID_CODE);
		assertEquals(TWO_COLUMNS, this.statement.getTemplate().getColumns().count());
	}
	
	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (UpdateStatement) new UpdateStatementBuilder(lTokens).build();
	}
	
}
