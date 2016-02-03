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

import com.sdfl.exceptions.parsing.ExpectedEndOfStatementException;
import com.sdfl.exceptions.parsing.MissingParameterException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.StepStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Test suite for {@link StepStatementBuilder}
 * @author Sylvain Cloutier
 */
public class StepStatementBuilderTest extends BuilderTestClass {
	private static final String STEP_STATEMENT_START = "step ";
	private static final String DUMMY_TEXT = " asd;";
	private static final String MY_STEP_ID1 = "myStepId1";
	private static final String STEP_STATEMENT_MISSING_ID = "step";
	private StepStatement statement;
	
	@Test(expected = MissingParameterException.class)
	public void testThatStepIdIsMandatory() {
		this.buildStatementBasedOnCode(STEP_STATEMENT_MISSING_ID);
	}
	
	@Test(expected = ExpectedEndOfStatementException.class)
	public void testThatTheEndOfStatementIsExceptedAfterTheStepId() {
		this.buildStatementBasedOnCode(STEP_STATEMENT_START + MY_STEP_ID1 + DUMMY_TEXT);
	}
	
	@Test
	public void testThatTheStepIdIsSetProperly() {
		this.buildStatementBasedOnCode(STEP_STATEMENT_START + MY_STEP_ID1);
		assertEquals(MY_STEP_ID1, this.statement.getIdentifier());
	}
	
	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (StepStatement) new StepStatementBuilder(lTokens).build();
	}
}
