package com.sdfl.statements.builders.impl;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder pattern implementation for the {@link InPackageStatement}
 * @author Sylvain Cloutier
 */
public class InPackageStatementBuilder extends StatementBuilder {

	public InPackageStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		
		InPackageStatement lPackStatement = new InPackageStatement();
		
		this.tokens.consumeExpected(SDFLKeywords.PACKAGE);
		String lPackageName = this.tokens.consumeExpectedParameter();
		lPackStatement.setPackageName(lPackageName);
		
		this.tokens.expectEndOfStatement();
		
		return lPackStatement;
	}
	
}
