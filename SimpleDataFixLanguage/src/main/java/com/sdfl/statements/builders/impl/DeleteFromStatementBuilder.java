/**
 * This file is part of the Simple Data Fix Language (SDFL) core.
 * 
 * All components of the language (compiler, interpreter, etc.) are
 * free and open source: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import com.sdfl.code.SDFLKeywords;
import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.builders.impl.condition.OnlyIfStatementBuilder;
import com.sdfl.statements.conditions.OnlyIfStatement;
import com.sdfl.statements.impl.DeleteFromStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder class of {@link DeleteFromStatement}
 * @author Sylvain Cloutier
 */
public class DeleteFromStatementBuilder extends StatementBuilder {

	public DeleteFromStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		DeleteFromStatement lStatement = new DeleteFromStatement();
		
		this.tokens.consumeExpected(SDFLKeywords.FROM);
		
		String lTableName = this.tokens.consumeExpectedParameter();

		System.out.println("Table name: " + lTableName);
		lStatement.setTableName(lTableName);
		
		OnlyIfStatement lInsertCondition = new OnlyIfStatementBuilder(tokens).build();
		lStatement.setConditionGroup(lInsertCondition.getConditionGroup());
		
		this.tokens.expectEndOfStatement();
		
		return lStatement;
	}

}
