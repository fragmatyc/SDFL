package com.sdfl.statements.builders.impl;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.exceptions.parsing.InvalidConditionException;
import com.sdfl.statements.Statement;
import com.sdfl.statements.assertions.impl.NotExistAssertion;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.builders.impl.condition.OnlyIfStatementBuilder;
import com.sdfl.statements.conditions.OnlyIfStatement;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.template.UsingTemplateStatement;
import com.sdfl.statements.tokenizer.StatementTokens;


public class InsertIntoStatementBuilder extends StatementBuilder {

	public InsertIntoStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		InsertIntoStatement lInsertStatement = new InsertIntoStatement();
		
		this.tokens.consumeExpected(SDFLKeywords.INTO);
		String lTableName = this.tokens.consumeExpectedParameter();
		
		lInsertStatement.setTableName(lTableName);
		
		UsingTemplateStatementBuilder lTemplateBuilder = new UsingTemplateStatementBuilder(this.tokens);
		UsingTemplateStatement lTemplate = lTemplateBuilder.build();
		
		lInsertStatement.setTemplate(lTemplate);
		
		if (this.tokens.hasNext()) {
			// TODO: Subclass to InsertConditionStatement
			OnlyIfStatement lInsertCondition = new OnlyIfStatementBuilder(tokens).build();
			lInsertStatement.setConditionGroup(lInsertCondition.getConditionGroup());
			
			boolean lConditionIsValid = lInsertCondition.size() == 1;

			if (lConditionIsValid) {
				lConditionIsValid = lInsertStatement.getConditionGroup().get(0) instanceof NotExistAssertion;
			}
			
			if (!lConditionIsValid) {
				throw new InvalidConditionException();
			}
		}
		
		this.tokens.expectEndOfStatement();
		
		return lInsertStatement;
	}

}
