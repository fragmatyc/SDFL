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
package com.sdfl.compiler.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.sdfl.compiler.SDFLCompiler;
import com.sdfl.exceptions.compiling.MissingCreateDatafixStatementException;
import com.sdfl.exceptions.compiling.MissingOrMisplacedInPackageStatementException;
import com.sdfl.exceptions.compiling.StatementsContainErrorsException;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.InPackageStatement;

/**
 * Test suite for the {@link SDFLCompiler}
 * @author Sylvain Cloutier
 */
public class SDFLCompilerTest extends SDFLCompilerTestBase<SDFLCompilerForTests> {
	private static final String CODE_WITHOUT_IN_PACKAGE = "create datafix mydf - allo;";
	private static final String ONLY_IN_PACKAGE =
			"in package MY_PACKAGE; ";
	private static final String VALID_CODE_HEADERS =
			ONLY_IN_PACKAGE + System.lineSeparator() + 
			"  create datafix USD1231234 - Test Oracle's compiler; ";
	private static final String INVALID_CODE_WITH_ERROR_STATEMENTS = VALID_CODE_HEADERS + System.lineSeparator() + 
			"asdasdadsasdasdasdasdasd";
	
	@Test(expected = MissingCreateDatafixStatementException.class)
	public void testThatStatementsContainAtLeastOneCreateDataFixStatement() {
		this.compile(ONLY_IN_PACKAGE);
	}
	
	@Test(expected = StatementsContainErrorsException.class)
	public void testThatStatementsContainErrorsExceptionIsWhenStatementsContainsError() {
		this.compile(INVALID_CODE_WITH_ERROR_STATEMENTS);
	}
	
	@Test
	public void testThatDestinationFolderIsCreatedIfNotExist() {
		when(this.fileUtil.notExists(DEFAULT_COMPILE_DESTINATION)).thenReturn(true);
		this.compile(VALID_CODE_HEADERS);
		verify(this.fileUtil).createFolder(DEFAULT_COMPILE_DESTINATION);
	}
	
	@Test
	public void testThatDestinationFolderIsNotCreatedIfExist() {
		when(this.fileUtil.exists(DEFAULT_COMPILE_DESTINATION)).thenReturn(true);
		this.compile(VALID_CODE_HEADERS);
		verify(this.fileUtil, never()).createFolder(DEFAULT_COMPILE_DESTINATION);
	}

	@Test
	public void testThatInPackageStatetmentCompilationMethodIsCalled() {
		this.compile(VALID_CODE_HEADERS);
		verify(this.compiler).compile(any(InPackageStatement.class));
	}
	
	@Test
	public void testThatCreateDatafixStatetmentCompilationMethodIsCalled() {
		this.compile(VALID_CODE_HEADERS);
		verify(this.compiler).compile(any(CreateDatafixStatement.class));
	}
	
	@Test(expected = MissingOrMisplacedInPackageStatementException.class)
	public void testThatMissingOrMisplacedInPackageStatementExceptionIsThrownIfNotFoundAtFirstLine() {
		this.compile(CODE_WITHOUT_IN_PACKAGE);
	}
		
}
