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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdfl.compiler.impl.oracle.OracleCodeBankExpected;
import com.sdfl.statements.impl.InsertIntoStatement;

/**
 * Tests suite for {@link OracleInsertIntoStatementSQLCodeGenerator}
 * @author Sylvain Cloutier
 */
public class OracleInsertIntoStatementSQLCodeGeneratorTest {
	
	@Test
	public void testThatBasicInsertIntoIsGeneratedAsExpected() {
		InsertIntoStatement lStatement = new InsertIntoStatement();
		
		lStatement.setTableName("MY_TABLE");
		lStatement.getTemplate().put("MY_COLUMN_1", "Value1");
		lStatement.getTemplate().put("MY_COLUMN_2", "Value2");
		lStatement.getTemplate().put("MY_COLUMN_3", "Value3");
		lStatement.getTemplate().put("MY_COLUMN_4", "Value4");
		
		OracleInsertIntoStatementSQLCodeGenerator lGenerator = new OracleInsertIntoStatementSQLCodeGenerator();
		assertEquals(OracleCodeBankExpected.BASIC_INSERT, lGenerator.generateSQLCode(lStatement));
	}
}
