package com.sdfl.statements.builders.impl;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.code.SDFLSyntax;
import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

public class CreateDatafixStatementBuilder extends StatementBuilder {

	public CreateDatafixStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		
		CreateDatafixStatement lStatement = new CreateDatafixStatement();
		
		this.tokens.consumeExpected(SDFLKeywords.DATAFIX);
		String lDatafixId = this.tokens.consumeExpectedParameter();
		
		lStatement.setDatafixId(lDatafixId);
		
		String lDatafixDesc = this.rebuildDescriptionIfNecessary();
		lStatement.setDatafixDescription(lDatafixDesc);
		
		return lStatement;
	}

	private String rebuildDescriptionIfNecessary() {
		StringBuilder lDescriptionBuilder = new StringBuilder();
		
		if (this.tokens.hasNext()) {
			this.tokens.consumeExpected(SDFLSyntax.DESCRIPTION_ID_SEPARATOR);
			
			while (this.tokens.hasNext()) {
				lDescriptionBuilder.append(this.tokens.consumeNext());
				
				if (this.tokens.hasNext()) {
					lDescriptionBuilder.append(" ");
				}
			}
		}
		
		return lDescriptionBuilder.toString();
	}

}
