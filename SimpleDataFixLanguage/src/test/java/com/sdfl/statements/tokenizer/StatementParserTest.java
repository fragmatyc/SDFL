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
package com.sdfl.statements.tokenizer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.ErrorStatement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.parser.StatementParser;

public class StatementParserTest {
	private static final String IN_PACKAGE_STATEMENT_CODE = "in package ASDASD";
	private static final String TOO_MANY_TOKENS_IN_PACKAGE_STATEMENT_CODE = "in package ASDASD otherToken";
	private static final String DATAFIX_ID = "DFID";
	private static final String DATAFIX_DESCRIPTION = "Data fix description";
	private static final String CREATE_DATAFIX_STATEMENT_CODE = "create datafix " + DATAFIX_ID + " - " + DATAFIX_DESCRIPTION;
	private static final String IMPORT_STATEMENT_CODE = "import myFile.csv into MY_TABLE";
	
	private StatementParser parser;
	
	@Before
	public void setup() {
		this.parser = new StatementParser();
	}
	
	@Test
	public void testThatInPackageStatementCodeReturnsAPackageDefinitionStatement() {
		Statement lStatement = parser.parseStatement(IN_PACKAGE_STATEMENT_CODE);
		assertThat(lStatement, instanceOf(InPackageStatement.class));
	}
	
	@Test
	public void testThatCreateDataFixStatementCodeReturnsADataFixCreationStatement() {
		Statement lStatement = parser.parseStatement(CREATE_DATAFIX_STATEMENT_CODE);
		assertThat(lStatement, instanceOf(CreateDatafixStatement.class));
	}
	
	@Test
	public void testThatImportStatementCodeReturnsAnImportStatement() {
		Statement lStatement = parser.parseStatement(IMPORT_STATEMENT_CODE);
		assertThat(lStatement, instanceOf(ImportStatement.class));
	}
	
	@Test
	public void testThatInPackageStatementWithTooManyTokenReturnsAnErrorStatement() {
		Statement lStatement = parser.parseStatement(TOO_MANY_TOKENS_IN_PACKAGE_STATEMENT_CODE);
		assertThat(lStatement, instanceOf(ErrorStatement.class));
	}
	
	
}
