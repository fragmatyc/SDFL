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
package com.sdfl.statements.builders.impl;

import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.builders.impl.condition.OnlyIfStatementBuilder;
import com.sdfl.statements.conditions.OnlyIfStatement;
import com.sdfl.statements.impl.UpdateStatement;
import com.sdfl.statements.template.UsingTemplateStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder pattern implementation of {@link UpdateStatement}
 * @author Sylvain Cloutier
 */
public class UpdateStatementBuilder extends StatementBuilder {

	public UpdateStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		UpdateStatement lUpdateStatement = new UpdateStatement();
				
		setTableName(lUpdateStatement);
		setTemplate(lUpdateStatement);
		
		if (this.tokens.hasNext()) {
			OnlyIfStatement lConditionStatement = new OnlyIfStatementBuilder(tokens).build();
			lUpdateStatement.setConditionGroup(lConditionStatement.getConditionGroup());
		}

		this.tokens.expectEndOfStatement();
		
		return lUpdateStatement;
	}

	private void setTemplate(UpdateStatement lUpdateStatement) {
		UsingTemplateStatementBuilder lTemplateBuilder = new UsingTemplateStatementBuilder(this.tokens);
		UsingTemplateStatement lTemplate = lTemplateBuilder.build();
		
		lUpdateStatement.setTemplate(lTemplate);
	}

	private void setTableName(UpdateStatement lUpdateStatement) {
		String lTableName = this.tokens.consumeExpectedParameter();
		lUpdateStatement.setTableName(lTableName);
	}

}
