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
package com.sdfl.compiler.impl.oracle.sql.statement;

import com.sdfl.compiler.sql.statement.ConditionGroupSQLCodeGenerator;
import com.sdfl.compiler.sql.statement.UpdateStatementSQLCodeGenerator;
import com.sdfl.statements.template.Columns;

/**
 * Implementation of the {@link UpdateStatementSQLCodeGenerator} for Oracle
 * @author Sylvain Cloutier
 */
public class OracleUpdateStatementSQLCodeGenerator extends UpdateStatementSQLCodeGenerator {
	private ConditionGroupSQLCodeGenerator conditionGroupSQLGenerator;
	
	public OracleUpdateStatementSQLCodeGenerator() {
		this.conditionGroupSQLGenerator = new ConditionGroupSQLCodeGenerator();
	}
	
	@Override
	public String generateSQLCode() {
		this.buildUpdateHeader();
		this.buildColumnsTemplate();
		this.buildWhereClause();
		
		this.builder.append(";");
		return this.builder.toString();
	}

	private void buildUpdateHeader() {
		this.builder.append("UPDATE" + System.lineSeparator());
		this.builder.append("    " + this.statement.getTableName() + System.lineSeparator());
		this.builder.append("SET" + System.lineSeparator());
	}

	private void buildColumnsTemplate() {
		Columns lTemplateColumns = this.statement.getTemplate().getColumns();
		for (int lCurColIdx = 0; lCurColIdx < lTemplateColumns.count(); lCurColIdx++) {
			String lCurColName = lTemplateColumns.get(lCurColIdx);
			String lCurColVal = lTemplateColumns.get(lCurColName);
			
			lCurColVal = this.removeStringDelimiterIfNecessary(lCurColVal);
			
			this.builder.append("    " + lCurColName + " = '" + lCurColVal + "'");
			
			this.appendIfNotLast(",", lTemplateColumns.count(), lCurColIdx); 
			
			this.builder.append(System.lineSeparator());
		}
	}

	private void buildWhereClause() {
		this.builder.append("WHERE" + System.lineSeparator());
		this.builder.append("    ");
		
		this.builder.append(this.conditionGroupSQLGenerator.generateSQLCode(
				this.statement.getConditionGroup()));
	}
}
