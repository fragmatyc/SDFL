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
import com.sdfl.statements.impl.StepStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Object representation of the SDFL "step" statement.
 * @author Sylvain Cloutier
 */
public class StepStatementBuilder extends StatementBuilder {

	public StepStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		StepStatement lStep = new StepStatement();
		
		String lStepId = this.tokens.consumeExpectedParameter();
		lStep.setIdentifier(lStepId);
		
		this.tokens.expectEndOfStatement();
		
		return lStep;
	}

}
