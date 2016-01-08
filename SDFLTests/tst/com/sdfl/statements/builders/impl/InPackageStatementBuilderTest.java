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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdfl.exceptions.parsing.MissingParameterException;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Tests suite for {@link InPackageStatementBuilder}
 * @author Sylvain Cloutier
 */
public class InPackageStatementBuilderTest extends BuilderTestClass {
	public static final String VALID_CODE = "in package PACKAGE_NAME";
	public static final String INVALID_CODE_MISSING_PACKAGE_NAME = "in package";
	
	private InPackageStatement statement;

	@Test
	public void testThatThePackageNameIsSet() {
		this.buildStatementBasedOnCode(VALID_CODE);
		assertEquals("PACKAGE_NAME", this.statement.getPackageName());
	}
	
	@Test(expected = MissingParameterException.class)
	public void testThatThePackageNameIsMandatory() {
		this.buildStatementBasedOnCode(INVALID_CODE_MISSING_PACKAGE_NAME);
	}

	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		lTokens.consumeNext();
		this.statement = (InPackageStatement) new InPackageStatementBuilder(lTokens).build();
	}
}
