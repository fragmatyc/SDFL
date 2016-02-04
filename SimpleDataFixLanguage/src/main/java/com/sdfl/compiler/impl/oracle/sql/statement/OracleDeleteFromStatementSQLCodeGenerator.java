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
import com.sdfl.compiler.sql.statement.DeleteFromStatementSQLCodeGenerator;

public class OracleDeleteFromStatementSQLCodeGenerator extends
DeleteFromStatementSQLCodeGenerator {
private ConditionGroupSQLCodeGenerator conditionGroupSQLGenerator;
	public OracleDeleteFromStatementSQLCodeGenerator() {
		this.conditionGroupSQLGenerator = new ConditionGroupSQLCodeGenerator();
	}
	
	@Override
	protected String generateSQLCode() {
		this.buildDeleteHeader();	
		this.buildWhereClause();
		this.builder.append(";");
		
		return this.builder.toString();
	}

	private void buildDeleteHeader() {
		this.builder
			.append("DELETE FROM" + System.lineSeparator())
			.append("    " + this.statement.getTableName() + System.lineSeparator());
	}
	
	private void buildWhereClause() {
		this.builder.append("WHERE" + System.lineSeparator());
		this.builder.append("    ");
		
		this.builder.append(this.conditionGroupSQLGenerator.generateSQLCode(
				this.statement.getConditionGroup()));
	}


}
