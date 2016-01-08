package com.sdfl.statements.builders.impl;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.code.SDFLSyntax;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.template.UsingTemplateStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder pattern implementation for the {@link UsingTemplateStatement}
 * @author Sylvain Cloutier
 */
public class UsingTemplateStatementBuilder extends StatementBuilder {
	
	public UsingTemplateStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public UsingTemplateStatement build() {
		UsingTemplateStatement lTemplate = new UsingTemplateStatement();
		
		this.tokens.consumeExpected(SDFLKeywords.USING);
		this.tokens.consumeExpected(SDFLKeywords.TEMPLATE);
	
		while (this.tokens.hasNext()) {
			String lCurrentValue = this.tokens.consumeExpectedParameter();
			
			this.tokens.consumeExpected(SDFLSyntax.ARROW);
			String lCurrentColumnName = this.tokens.consumeExpectedParameter();

			lTemplate.put(lCurrentColumnName, lCurrentValue);
			
			if (isTemplateStatementEnded()) {
				break;
			} else {
				this.tokens.consumeExpected(SDFLSyntax.COMMA);
			}
			
		}
		
		return lTemplate;
	}

	private boolean isTemplateStatementEnded() {
		return !this.tokens.isNextTokenEqualTo(SDFLSyntax.COMMA);
	}
}
