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
 * Test suite for StepStatementBuilder
 * @author Sylvain Cloutier
 */
public class StepStatementBuilderTest extends BuilderTestClass {
	private StepStatement statement;
	
	@Test(expected = MissingParameterException.class)
	public void testThatStepIdIsMandatory() {
		this.buildStatementBasedOnCode("step");
	}
	
	@Test(expected = ExpectedEndOfStatementException.class)
	public void testThatTheEndOfStatementIsExceptedAfterTheStepId() {
		this.buildStatementBasedOnCode("step stepId1 asd;");
	}
	
	@Test
	public void testThatTheStepIdIsSetProperly() {
		this.buildStatementBasedOnCode("step myStepId1");
		assertEquals("myStepId1", this.statement.getIdentifier());
	}
	
	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (StepStatement) new StepStatementBuilder(lTokens).build();
	}
}
