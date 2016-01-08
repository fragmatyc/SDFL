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

import org.junit.Before;
import org.junit.Test;

import com.sdfl.compiler.impl.oracle.OracleCodeBankExpected;
import com.sdfl.statements.assertions.Assertion;
import com.sdfl.statements.assertions.impl.EqualAssertion;
import com.sdfl.statements.assertions.impl.NotEqualAssertion;
import com.sdfl.statements.conditions.Condition.Relations;
import com.sdfl.statements.conditions.ConditionGroup;
import com.sdfl.statements.impl.UpdateStatement;

/**
 * Tests suite for {@link OracleUpdateStatementSQLCodeGenerator}
 * @author Sylvain Cloutier
 */
public class OracleUpdateStatementSQLCodeGeneratorTest {
	private UpdateStatement statement;
	
	@Before
	public void setup() {
		this.statement = new UpdateStatement();
		
		this.statement.setTableName("MY_TABLE");
		this.statement.getTemplate().put("MY_COLUMN_1", "Value1");
		this.statement.getTemplate().put("MY_COLUMN_2", "Value2");
		this.statement.getTemplate().put("MY_COLUMN_3", "Value3");
	}
	@Test
	public void testThatSimpleUpdateIsGeneratedAsExpected() {
		Assertion lAssert1 = new EqualAssertion();
		lAssert1.setLeftTerm("MY_COLUMN_1");
		lAssert1.setRightTerm("My value 1");
		lAssert1.setRelation(Relations.AND);
		
		Assertion lAssert2 = new NotEqualAssertion();
		lAssert2.setLeftTerm("MY_COLUMN_2");
		lAssert2.setRightTerm("My value 2");
		
		this.statement.getConditionGroup().add(lAssert1);
		this.statement.getConditionGroup().add(lAssert2);
		
		OracleUpdateStatementSQLCodeGenerator lGenerator = new OracleUpdateStatementSQLCodeGenerator();
		assertEquals(OracleCodeBankExpected.BASIC_UPDATE, lGenerator.generateSQLCode(this.statement));	
	}
	
	@Test
	public void testThatUpdateStatementWithSubConditionIsGeneratedAsExpected() {
		Assertion lAssert1 = new EqualAssertion();
		lAssert1.setLeftTerm("MY_COLUMN_1");
		lAssert1.setRightTerm("My value 1");
		lAssert1.setRelation(Relations.AND);
		
		ConditionGroup lSubGroup = new ConditionGroup();
		Assertion lAssert2 = new NotEqualAssertion();
		lAssert2.setLeftTerm("MY_COLUMN_2");
		lAssert2.setRightTerm("My value 2");
		lAssert2.setRelation(Relations.OR);
		
		Assertion lAssert3 = new EqualAssertion();
		lAssert3.setLeftTerm("MY_COLUMN_3");
		lAssert3.setRightTerm("My value 3");
		
		lSubGroup.add(lAssert2);
		lSubGroup.add(lAssert3);
		
		this.statement.getConditionGroup().add(lAssert1);
		this.statement.getConditionGroup().add(lSubGroup);
		
		OracleUpdateStatementSQLCodeGenerator lGenerator = new OracleUpdateStatementSQLCodeGenerator();
		assertEquals(OracleCodeBankExpected.UPDATE_WITH_SUBGROUP, 
				lGenerator.generateSQLCode(this.statement));	
	}
	
	@Test
	public void testThatComplicatedUpdateStatementIsGeneratedAsExpected() {

		this.statement.getConditionGroup().add(new EqualAssertion("MY_COLUMN_1", "Value 1", Relations.AND));
		
		ConditionGroup lSubGroup1 = new ConditionGroup();
		lSubGroup1.add(new EqualAssertion("MY_COLUMN_2", "Value 2", Relations.OR));
		lSubGroup1.add(new EqualAssertion("MY_COLUMN_2", "Value 3", Relations.OR));
		this.statement.getConditionGroup().add(lSubGroup1);
		
		ConditionGroup lSubGroup2 = new ConditionGroup();
		lSubGroup2.add(new NotEqualAssertion("MY_COLUMN_3", "Value 2", Relations.AND));
		lSubGroup2.add(new NotEqualAssertion("MY_COLUMN_3", "Value 3"));
		lSubGroup1.add(lSubGroup2);

		OracleUpdateStatementSQLCodeGenerator lGenerator = new OracleUpdateStatementSQLCodeGenerator();
		assertEquals(OracleCodeBankExpected.COMPLICATED_WHERE_CLAUSE, 
				lGenerator.generateSQLCode(this.statement));	
	}
}
