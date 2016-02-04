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
package com.sdfl.compiler.sql.statement;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sdfl.compiler.sql.SQLCodeGenerator;
import com.sdfl.statements.assertions.Assertion;
import com.sdfl.statements.assertions.impl.EqualAssertion;
import com.sdfl.statements.assertions.impl.NotEqualAssertion;
import com.sdfl.statements.conditions.Condition;
import com.sdfl.statements.conditions.ConditionGroup;

/**
 * @author Sylvain Cloutier
 */
public class ConditionGroupSQLCodeGenerator extends SQLCodeGenerator {
	private static final int INIT_INDENT_LEVEL = 1;
	
	public String generateSQLCode(ConditionGroup pGroup) {
		this.resetBuilder();
		this.buildConditionGroup(pGroup.getConditions(), INIT_INDENT_LEVEL);
		return this.builder.toString();
	}
	
	private void buildConditionGroup(List<Condition> pConditionsInGroup, int pIndentLevel) {
		
		for (int lCurConditionIdx = 0; lCurConditionIdx < pConditionsInGroup.size(); lCurConditionIdx ++) {
			
			Condition lCurCondition = pConditionsInGroup.get(lCurConditionIdx);
			if (lCurCondition instanceof Assertion) {
				this.buildAssertion(lCurCondition);
			} else if (lCurCondition instanceof ConditionGroup) {
				this.builder.append("(");
				this.buildConditionGroup(((ConditionGroup) lCurCondition).getConditions(), pIndentLevel + 1);
				this.builder.append(")");
			}
			
			this.appendIfNotLast(System.lineSeparator(), pConditionsInGroup.size(), lCurConditionIdx);
			this.addRelationKeywordIfNecessary(pIndentLevel, lCurCondition);
		}
	}

	private void buildAssertion(Condition lCurCondition) {
		this.builder.append(((Assertion) lCurCondition).getLeftTerm());
		
		if (lCurCondition instanceof EqualAssertion) {
			this.builder.append(" = ");
		} else if (lCurCondition instanceof NotEqualAssertion) {
			this.builder.append(" != ");
		}
		
		this.builder.append("'" + ((Assertion) lCurCondition).getRightTerm() + "'");
	}
	
	private void addRelationKeywordIfNecessary(int pIndentLevel,
			Condition lCurCondition) {
		if (lCurCondition.getRelation() != null) {
			this.builder.append(StringUtils.repeat("    ", pIndentLevel));
			
			String lStringOfRelation = null;
			switch (lCurCondition.getRelation()) {
				case AND:
					lStringOfRelation = "AND";
					break;

				case OR:
					lStringOfRelation = "OR";
					break;
			}
			
			this.builder.append(lStringOfRelation + " ");
		}
	}
}
