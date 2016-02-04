package com.sdfl.compiler.impl.oracle.sql.statement;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdfl.compiler.impl.oracle.OracleCodeBankExpected;
import com.sdfl.statements.assertions.impl.EqualAssertion;
import com.sdfl.statements.conditions.Condition;
import com.sdfl.statements.conditions.ConditionGroup;
import com.sdfl.statements.impl.DeleteFromStatement;

public class OracleDeleteStatementSQLCodeGeneratorTest {
	@Test
	public void testThatBasicDeleteFromStatementGeneratedAsExpected() {
		DeleteFromStatement lStatement = new DeleteFromStatement();
		
		lStatement.setTableName("MY_TABLE");
		lStatement.setConditionGroup(new ConditionGroup());
		Condition equal = new EqualAssertion("MY_COLUMN_1", "\"Value1\"");
		lStatement.getConditionGroup().add(equal);
		
		OracleDeleteFromStatementSQLCodeGenerator lGenerator = new OracleDeleteFromStatementSQLCodeGenerator();
		assertEquals(OracleCodeBankExpected.BASIC_DELETE, lGenerator.generateSQLCode(lStatement));
	}
}
