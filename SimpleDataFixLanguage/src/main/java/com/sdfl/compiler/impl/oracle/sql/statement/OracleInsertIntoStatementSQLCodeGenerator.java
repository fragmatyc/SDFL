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

import com.sdfl.compiler.sql.statement.InsertIntoStatementSQLCodeGenerator;
import com.sdfl.statements.template.Columns;

/**
 * Oracle implementation of the {@link InsertIntoStatementSQLCodeGenerator}
 * @author Sylvain Cloutier
 */
public class OracleInsertIntoStatementSQLCodeGenerator extends
		InsertIntoStatementSQLCodeGenerator {

	@Override
	public String generateSQLCode() {
		
		this.buildInsertHeader();	
		this.buildColumnsListingForInsert();
		this.buildValuesListingForInsert();
		
		return this.builder.toString();
	}

	private void buildInsertHeader() {
		this.builder.append("INSERT INTO" + System.lineSeparator());
		this.builder.append("    " + this.statement.getTableName() + " (" + System.lineSeparator());
	}

	private void buildValuesListingForInsert() {

		this.builder.append("VALUES (" + System.lineSeparator());
		
		int lCurColIdx = 0;
		Columns lTemplateCols = this.statement.getTemplate().getColumns();
		
		for (String lCurCol : lTemplateCols) {
			String lCurColumnValue = lTemplateCols.get(lCurCol);
			
			lCurColumnValue = this.removeStringDelimiterIfNecessary(lCurColumnValue);

			this.builder.append("    '" + lCurColumnValue + "'");
			
			if (lCurColIdx < lTemplateCols.count() - 1) {
				this.builder.append(",");
				this.builder.append(System.lineSeparator());
			} else {
				this.builder.append(");");
			}
			
			lCurColIdx++;
		}
	}

	private void buildColumnsListingForInsert() {

		Columns lTemplateCols = this.statement.getTemplate().getColumns();
		
		for (int lCurColIdx = 0; lCurColIdx < lTemplateCols.count(); lCurColIdx ++) {
			String lCurColumn = lTemplateCols.get(lCurColIdx);
			this.builder.append("        " + lCurColumn);
			
			if (lCurColIdx < lTemplateCols.count() - 1) {
				this.builder.append(",");
			} else {
				this.builder.append(")");
			}
			
			this.builder.append(System.lineSeparator());
		}
	}
	
}
