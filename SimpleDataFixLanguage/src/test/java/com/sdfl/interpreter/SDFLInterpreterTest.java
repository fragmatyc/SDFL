package com.sdfl.interpreter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.impl.UpdateStatement;

public class SDFLInterpreterTest {
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final int FIRST_STATEMENT = 0;
	private static final int SECOND_STATEMENT = 1;
	private static final int THIRD_STATEMENT = 2;
	private static final int FOURTH_STATEMENT = 3;
	private static final int ZERO_STATEMENT = 0;
	private static final int FOUR_STATEMENTS = 4;
	private static final String PACKAGE_NAME = "abc123";
	private static final String SECOND_LINE_OF_CODE = "create datafix DataFixName - Data fix description";
	private static final String VALID_CODE = 
			"in package " + PACKAGE_NAME + "; " + LINE_SEPARATOR +  
			"    " + SECOND_LINE_OF_CODE + "; " + LINE_SEPARATOR +
			"    // Single line comments to be removed " + LINE_SEPARATOR +
			"    import myFile.csv into MY_TABLE using template " + LINE_SEPARATOR +
			"        #1 -> MY_COLUMN_1," + LINE_SEPARATOR +
			"        #2 -> MY_COLUMN_2," + LINE_SEPARATOR +
			"        #3 -> MY_COLUMN_3; " + LINE_SEPARATOR + 
			"" + LINE_SEPARATOR + 
			"    /* " + LINE_SEPARATOR + 
			"     * Multiline comments to be removed " + LINE_SEPARATOR +
			"     */ " + LINE_SEPARATOR + 
			"    update MY_TABLE using template " + LINE_SEPARATOR + 
			"        \"Value 1\" -> MY_COLUMN_1, " + LINE_SEPARATOR +
			"        \"Value 2\" -> MY_COLUMN_2;";
			
	private static final String EMPTY_CODE = "";
	private static final String NULL_CODE = null;
	
	private SDFLInterpreter interpreter;
	
	@Before
	public void setup() {
		interpreter = new SDFLInterpreter();
	}
	
	@Test
	public void testThatTheCodeCanBeSplittedInStatements() {
		interpreter.parseCode(VALID_CODE);
		assertEquals(FOUR_STATEMENTS, interpreter.getStatements().count());
	}
	
	@Test 
	public void testThatEmptyCodeReturnsZeroStatement() {
		interpreter.parseCode(EMPTY_CODE);
		assertEquals(ZERO_STATEMENT, interpreter.getStatements().count());
	}
	
	@Test
	public void testThatNullCodeReturnsZeroStatement() {
		interpreter.parseCode(NULL_CODE);
		assertEquals(ZERO_STATEMENT, interpreter.getStatements().count());
	}
	
	@Test
	public void testThatFirstStatementIsAPackageDefinitionStatement() {
		interpreter.parseCode(VALID_CODE);
		
		Statement lFirstStatement = interpreter.getStatements().get(FIRST_STATEMENT);
		assertThat(lFirstStatement, instanceOf(InPackageStatement.class));
	}
	
	@Test
	public void testThatSecondStatementIsADataFixCreationStatement() {
		interpreter.parseCode(VALID_CODE);
		
		Statement lSecondStatement = interpreter.getStatements().get(SECOND_STATEMENT);
		assertThat(lSecondStatement, instanceOf(CreateDatafixStatement.class));
	}
	
	@Test
	public void testThatThirdStatementIsAnImportStatementWhenParsed() {
		interpreter.parseCode(VALID_CODE);
		
		Statement lThirdStatement = interpreter.getStatements().get(THIRD_STATEMENT);
		assertThat(lThirdStatement, instanceOf(ImportStatement.class));
	}
	
	@Test
	public void testThatFourthStatementIsAnUpdateStatement() {
		interpreter.parseCode(VALID_CODE);
		
		Statement lFourthStatement = interpreter.getStatements().get(FOURTH_STATEMENT);
		assertThat(lFourthStatement, instanceOf(UpdateStatement.class));
	}
	
}
