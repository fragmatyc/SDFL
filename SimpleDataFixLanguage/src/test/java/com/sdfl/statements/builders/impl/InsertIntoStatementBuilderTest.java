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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sdfl.exceptions.parsing.InvalidConditionException;
import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.statements.assertions.impl.NotExistAssertion;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Tests suite for {@link InsertIntoStatement}
 * @author Sylvain Cloutier
 */
public class InsertIntoStatementBuilderTest extends BuilderTestClass {
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String COLUMN_2 = "MY_COLUMN_2";
	private static final String COLUMN_2_VALUE = "\"My Value 2\"";
	private static final String INSERT_INTO_STATEMENT = 
			"insert into " + TABLE_NAME + " using template \"My Value 1\" -> MY_COLUMN_1, \"My Value 2\" -> " + COLUMN_2 + "";
	private static final String INSERT_INTO_MISSING_TEMPLATE = 
			"insert into MY_TABLE";
	private static final String INSERT_INTO_WITH_GOOD_CONDITION = 
			"insert into MY_TABLE using template \"test\" -> MY_COL only if not exist";
	private static final String INSERT_INTO_WITH_BAD_CONDITION =
			"insert into MY_TABLE using template \"test\" -> MY_COL only if COLUM_1 = \"10\"";
	
	private InsertIntoStatement statement;
	
	@Test(expected = MissingKeywordException.class)
	public void testThatTemplateIsMandatory() {
		buildInsertIntoBasedOnCode(INSERT_INTO_MISSING_TEMPLATE);
	}
	
	@Test
	public void testThatConditionNotExistsAssertionIsAllowed() {
		buildInsertIntoBasedOnCode(INSERT_INTO_WITH_GOOD_CONDITION);
		assertThat(this.statement.getConditionGroup().getConditions().get(0), instanceOf(NotExistAssertion.class));
	}
	
	@Test(expected = InvalidConditionException.class)
	public void testThatConditionOtherThanNotExistsAssertionAreNotAllowed() {
		buildInsertIntoBasedOnCode(INSERT_INTO_WITH_BAD_CONDITION);
	}
	
	@Test
	public void testThatTableNameIsSetAfterParsing() {
		buildInsertIntoBasedOnCode(INSERT_INTO_STATEMENT);
		assertEquals(TABLE_NAME, this.statement.getTableName());
	}
	
	@Test
	public void testThatTemplateHasProperColumnCount() {
		buildInsertIntoBasedOnCode(INSERT_INTO_STATEMENT);
		assertEquals(2, this.statement.getTemplate().getColumns().count());
	}
	
	@Test
	public void testThatColumnValueCanBeRetrievedAfterBuild() {
		buildInsertIntoBasedOnCode(INSERT_INTO_STATEMENT);
		assertEquals(COLUMN_2_VALUE, this.statement.getTemplate().getColumns().get(COLUMN_2));
	}
	
	private void buildInsertIntoBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (InsertIntoStatement) new InsertIntoStatementBuilder(lTokens).build();
	}

}
