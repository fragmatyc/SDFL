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
package com.sdfl.compiler.impl.oracle.sql.statement;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sdfl.compiler.impl.oracle.OracleCodeBankExpected;
import com.sdfl.compiler.util.inputfile.ImportInputFile;
import com.sdfl.compiler.util.inputfile.ImportInputFileLoader;
import com.sdfl.compiler.util.inputfile.ImportInputFileRow;
import com.sdfl.exceptions.compiling.TemplateColumnNotFoundInInputFileException;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.template.UsingTemplateStatement;

/**
 * Test suite for {@link OracleImportStatementSQLCodeGenerator}
 * @author Sylvain Cloutier
 */
@RunWith(MockitoJUnitRunner.class)
public class OracleImportStatementSQLCodeGeneratorTest {
	private static final String FILE_NAME = "any.xls";
	private static final String MY_TABLE = "MY_TABLE";
	private static final String VALUE_4 = "Value4";
	private static final String VALUE_3 = "Value3";
	private static final String VALUE_2 = "Value2";
	private static final String VALUE_1 = "Value1";
	private static final String IDX_4 = "#4";
	private static final String IDX_3 = "#3";
	private static final String IDX_2 = "#2";
	private static final String IDX_1 = "#1";
	private static final String MY_COLUMN_4 = "MY_COLUMN_4";
	private static final String MY_COLUMN_3 = "MY_COLUMN_3";
	private static final String MY_COLUMN_2 = "MY_COLUMN_2";
	private static final String MY_COLUMN_1 = "MY_COLUMN_1";
	private static final String WRONG_COLUMN_HEADER = "WRONG COLUMN";
	private static final String HEADER_COL_4 = "My column header 4";
	private static final String HEADER_COL_3 = "My column header 3";
	private static final String HEADER_COL_2 = "My column header 2";
	private static final String HEADER_COL_1 = "My column header 1";
	
	
	private OracleImportStatementSQLCodeGenerator generator;
	private ImportStatement statement;
	private ImportInputFile inputFile;
	@Mock private ImportInputFileLoader loader;
	
	@Before
	public void setup() {
		this.generator = new OracleImportStatementSQLCodeGenerator();
		this.statement = new ImportStatement();
		this.statement.setInputFile(FILE_NAME);
		this.statement.setTableName(MY_TABLE);
		
		this.inputFile = new ImportInputFile();

		when(this.loader.load(any())).thenReturn(this.inputFile);
		this.generator.setLoader(this.loader);
	}
	
	@Test
	public void testThatImportFileWithoutHeaderGetsTransformedIntoInsertSQLStatement() {
		
		this.addDataToInputFile(VALUE_1, VALUE_2, VALUE_3, VALUE_4);
		
		UsingTemplateStatement lTemplate = new UsingTemplateStatement();
		lTemplate.put(MY_COLUMN_1, IDX_1);
		lTemplate.put(MY_COLUMN_2, IDX_2);
		lTemplate.put(MY_COLUMN_3, IDX_3);
		lTemplate.put(MY_COLUMN_4, IDX_4);
		this.statement.setTemplate(lTemplate);
		
		String lActual = this.generator.generateSQLCode(this.statement);
		assertEquals(OracleCodeBankExpected.BASIC_INSERT, lActual);
	}
	
	@Test
	public void testThatImportFileWithHeaderGetsTransformedIntoInsertSQLStatement() {
		this.defineStatementWithHeadersData();
		
		UsingTemplateStatement lTemplate = new UsingTemplateStatement();
		lTemplate.put(MY_COLUMN_1, HEADER_COL_1);
		lTemplate.put(MY_COLUMN_2, HEADER_COL_2);
		lTemplate.put(MY_COLUMN_3, HEADER_COL_3);
		lTemplate.put(MY_COLUMN_4, HEADER_COL_4);
		this.statement.setTemplate(lTemplate);
		
		String lActual = this.generator.generateSQLCode(this.statement);
		assertEquals(OracleCodeBankExpected.BASIC_INSERT, lActual);
	}
	
	@Test(expected = TemplateColumnNotFoundInInputFileException.class)
	public void testThatBadTemplateWithNonExistingColumnLabelThrowsAnException() {
		this.defineStatementWithHeadersData();
		
		UsingTemplateStatement lTemplate = new UsingTemplateStatement();
		lTemplate.put(MY_COLUMN_1, HEADER_COL_1);
		lTemplate.put(MY_COLUMN_2, HEADER_COL_2);
		lTemplate.put(MY_COLUMN_3, HEADER_COL_3);
		lTemplate.put(MY_COLUMN_4, WRONG_COLUMN_HEADER);
		this.statement.setTemplate(lTemplate);
		
		this.generator.generateSQLCode(this.statement);
	}

	private void defineStatementWithHeadersData() {
		this.statement.setWithHeaders(true);
		
		this.addDataToInputFile(HEADER_COL_1, HEADER_COL_2, HEADER_COL_3, HEADER_COL_4);
		this.addDataToInputFile(VALUE_1, VALUE_2, VALUE_3, VALUE_4);
	}
	
	private void  addDataToInputFile(String... pValues) {
		ImportInputFileRow lRow1 = new ImportInputFileRow();
		
		for (int i = 0; i < pValues.length; i++) {
			lRow1.setColumn(i, pValues[i]);
		}

		this.inputFile.add(lRow1);
		
	}
}
