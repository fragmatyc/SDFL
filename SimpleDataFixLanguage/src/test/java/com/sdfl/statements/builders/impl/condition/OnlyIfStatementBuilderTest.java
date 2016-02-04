package com.sdfl.statements.builders.impl.condition;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sdfl.statements.assertions.Assertion;
import com.sdfl.statements.assertions.impl.EqualAssertion;
import com.sdfl.statements.assertions.impl.NotEqualAssertion;
import com.sdfl.statements.builders.BuilderTestClass;
import com.sdfl.statements.conditions.ConditionGroup;
import com.sdfl.statements.conditions.OnlyIfStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Tests suite for {@link OnlyIfStatementBuilder}
 * @author Sylvain Cloutier
 */
public class OnlyIfStatementBuilderTest extends BuilderTestClass {
	private static final int SECOND = 1;
	private static final int FIRST = 0;
	public static final String VALID_CODE_EQUAL_ASSERTION = "only if MY_COLUMN1 = \"1\"";
	public static final String VALID_CODE_EQUAL_AND_NOT_EQUAL_ASSERTIONS = "only if MY_COLUMN1 = \"1\" and MY_COLUMN2 != \"2\"";
	public static final String VALID_CODE_CONDITION_ENDS_WITH_GROUP = "only if MY_COLUMN1 = \"1\" and (MY_COLUMN2 != \"2\" or MY_COLUMN_3 = \"3\")";
	public static final String VALID_CODE_CONDITION_STARTS_WITH_GROUP = "only if (MY_COLUMN1 = \"1\" or MY_COLUMN2 != \"2\") and MY_COLUMN_3 = \"3\"";
	public static final String VALID_CODE_CONDITION_WITH_SUBGROUPS = "only if MY_COLUMN1 = \"1\" and ((MY_COLUMN2 != \"2\" or MY_COLUMN_3 = \"3\") and (MY_COLUMN2 = \"3\" or MY_COLUMN3 = \"2\"))";
	
	private OnlyIfStatement statement;
	
	@Test
	public void testThatConditionWithOneAssertionHasASizeOfOne() {
		this.buildStatementBasedOnCode(VALID_CODE_EQUAL_ASSERTION);
		assertEquals(1, this.statement.getConditionGroup().size());
	}
	
	@Test
	public void testThatConditionWithOneAssertionHasOnlyOneConditionThatIsAnEqualAssertion() {
		this.buildStatementBasedOnCode(VALID_CODE_EQUAL_ASSERTION);
		assertThat(this.statement.getConditionGroup().get(FIRST), instanceOf(EqualAssertion.class));
	}
	
	@Test
	public void testThatConditionWithTwoAssertionsHasASizeOfTwo() {
		this.buildStatementBasedOnCode(VALID_CODE_EQUAL_AND_NOT_EQUAL_ASSERTIONS);
		assertEquals(2, this.statement.getConditionGroup().size());
	}
	
	@Test
	public void testThatConditionWithTwoAssertionsHasTheSecondConditionThatIsANotEqualAssertion() {
		this.buildStatementBasedOnCode(VALID_CODE_EQUAL_AND_NOT_EQUAL_ASSERTIONS);
		assertThat(this.statement.getConditionGroup().get(SECOND), instanceOf(NotEqualAssertion.class));
	}
	
	@Test
	public void testThatConditionWithOneAssertionAndOneConditionSubGroupHasASizeOfTwo() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_ENDS_WITH_GROUP);
		assertEquals(2, this.statement.getConditionGroup().size());
	}
	
	@Test
	public void testThatSecondConditionOfAStatementWithOneAssertionAndOneConditionSubGroupIsAConditionGroup() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_ENDS_WITH_GROUP);
		assertThat(this.statement.getConditionGroup().get(SECOND), instanceOf(ConditionGroup.class));
	}
	
	@Test
	public void testThatSecondConditionOfAStatementWithOneAssertionAndOneConditionSubGroupHasTwoAssertions() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_ENDS_WITH_GROUP);
		assertEquals(2, ((ConditionGroup) this.statement.getConditionGroup().get(SECOND)).size());
	}
	
	@Test
	public void testThatFirstConditionOfAStatementWithOneConditionSubGroupAndOneAssertionIsAConditionStatement() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_STARTS_WITH_GROUP);
		assertThat(this.statement.getConditionGroup().get(FIRST), instanceOf(ConditionGroup.class));
	}
	
	@Test
	public void testThatFirstConditionOfAStatementWithOneConditionSubGroupAndOneAssertionHasTwoAssertions() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_STARTS_WITH_GROUP);
		assertEquals(2, ((ConditionGroup) this.statement.getConditionGroup().get(FIRST)).size());
	}
	
	@Test
	public void testThatSubGroupsCanBeNested() {
		this.buildStatementBasedOnCode(VALID_CODE_CONDITION_WITH_SUBGROUPS);
		ConditionGroup lCurrentGroup = this.statement.getConditionGroup();
		ConditionGroup lCurrentCondition = (ConditionGroup) lCurrentGroup.get(SECOND);
		lCurrentCondition = (ConditionGroup) lCurrentCondition.get(FIRST);
		
		Assertion lAssertion = (Assertion) lCurrentCondition.get(SECOND);
		
		assertThat(lAssertion, instanceOf(EqualAssertion.class));
		assertEquals("MY_COLUMN_3", lAssertion.getLeftTerm());
		assertEquals("\"3\"", lAssertion.getRightTerm());
	}
	
	private void buildStatementBasedOnCode(String pCode) {
		StatementTokens lTokens = this.tokenizer.tokenize(pCode);
		this.statement = (OnlyIfStatement) new OnlyIfStatementBuilder(lTokens).build();
	}
}
