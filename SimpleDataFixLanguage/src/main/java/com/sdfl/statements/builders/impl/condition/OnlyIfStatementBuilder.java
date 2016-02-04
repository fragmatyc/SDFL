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
package com.sdfl.statements.builders.impl.condition;

import java.util.Stack;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.code.SDFLSyntax;
import com.sdfl.statements.assertions.Assertion;
import com.sdfl.statements.assertions.impl.EqualAssertion;
import com.sdfl.statements.assertions.impl.NotEqualAssertion;
import com.sdfl.statements.assertions.impl.NotExistAssertion;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.conditions.Condition;
import com.sdfl.statements.conditions.Condition.Relations;
import com.sdfl.statements.conditions.ConditionGroup;
import com.sdfl.statements.conditions.OnlyIfStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder pattern implemented for {@link OnlyIfStatement}
 * @author Sylvain Cloutier
 */
public class OnlyIfStatementBuilder extends StatementBuilder {
	private Stack<ConditionGroup> conditionGroupStack;
	
	public OnlyIfStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
		
		this.conditionGroupStack = new Stack<>();
	}

	@Override
	public OnlyIfStatement build() {
		this.tokens.consumeExpected(SDFLKeywords.ONLY);
		this.tokens.consumeExpected(SDFLKeywords.IF);
		
		OnlyIfStatement lConditionStatement = new OnlyIfStatement();
		this.conditionGroupStack.push(lConditionStatement.getConditionGroup());
		this.parseConditionGroupFromTokens();
		
		return lConditionStatement;
	}

	private void parseConditionGroupFromTokens() {
		Condition lCurCondition = null;
		
		if (this.tokens.isNextTokenEqualTo(SDFLSyntax.OPEN_BRACKET)) {

			this.tokens.consumeExpected(SDFLSyntax.OPEN_BRACKET);
			
			lCurCondition = new ConditionGroup();
			this.conditionGroupStack.peek().add(lCurCondition);
			this.conditionGroupStack.push((ConditionGroup) lCurCondition);
			
			this.parseConditionGroupFromTokens();
			
			this.tokens.consumeExpected(SDFLSyntax.CLOSED_BRACKET);
			this.conditionGroupStack.pop();
			
		} else {

			lCurCondition = parseAssertion();
			this.conditionGroupStack.peek().add(lCurCondition);
		}
		
		
		if (this.tokens.hasNext()) {
			if (!this.tokens.isNextTokenEqualTo(SDFLSyntax.CLOSED_BRACKET)) {
				String lRelation = this.tokens.consumeExpected(SDFLKeywords.AND, SDFLKeywords.OR);
				
				Relations lEnumValueOfRelation = null;
				
				switch (lRelation) {
					case SDFLKeywords.AND:
						lEnumValueOfRelation = Relations.AND;
						break;
					case SDFLKeywords.OR:
						lEnumValueOfRelation = Relations.OR;
						break;
				}
				
				lCurCondition.setRelation(lEnumValueOfRelation);
				
				this.parseConditionGroupFromTokens();
			}
		}
	}

	private Assertion parseAssertion() {
		String lFirstTerm = null;
		String lSecondTerm = null;
		String lOperator = null;
		
		Assertion lCurAssertion = null;
		
		if (this.tokens.isNextTokenEqualTo(SDFLKeywords.NOT)) {
			this.tokens.consumeExpected(SDFLKeywords.NOT);
			this.tokens.consumeExpected(SDFLKeywords.EXIST);
			
			lCurAssertion = new NotExistAssertion();
		} else {
			lFirstTerm = this.tokens.consumeExpectedParameter();
			lOperator = this.tokens.consumeExpected(
					SDFLSyntax.EQUAL, 
					SDFLSyntax.NOT_EQUAL);
			
			lSecondTerm = this.tokens.consumeExpectedParameter();
			
			switch (lOperator) {
				case SDFLSyntax.EQUAL:
					lCurAssertion = new EqualAssertion();
					break;
				case SDFLSyntax.NOT_EQUAL:
					lCurAssertion = new NotEqualAssertion();
					break;
			}
		}
		
		lCurAssertion.setLeftTerm(lFirstTerm);
		lCurAssertion.setRightTerm(lSecondTerm);
	
		return lCurAssertion;
	}

}
