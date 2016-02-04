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
package com.sdfl.compiler.sql;

import com.sdfl.compiler.CompilerType;
import com.sdfl.compiler.impl.oracle.OracleSQLCodeGeneratorFactory;
import com.sdfl.compiler.sql.statement.ImportStatementSQLCodeGenerator;
import com.sdfl.compiler.sql.statement.InsertIntoStatementSQLCodeGenerator;
import com.sdfl.compiler.sql.statement.UpdateStatementSQLCodeGenerator;
import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.impl.UpdateStatement;


/**
 * Master class of SQL code generator
 * @author Sylvain Cloutier
 */
public abstract class SQLCodeGeneratorFactory {
	
	protected abstract InsertIntoStatementSQLCodeGenerator getForInsertIntoStatement();
	protected abstract UpdateStatementSQLCodeGenerator getForUpdateStatement();
	protected abstract ImportStatementSQLCodeGenerator getForImportStatement();
	
	public String generateStatementSQLCode(Statement pStatement) {
		String lGeneratedCode = null;
		
		if (pStatement instanceof InsertIntoStatement) {
			lGeneratedCode = this.getForInsertIntoStatement().generateSQLCode((InsertIntoStatement) pStatement);
		} else if (pStatement instanceof UpdateStatement) {
			lGeneratedCode = this.getForUpdateStatement().generateSQLCode((UpdateStatement) pStatement);
		} else if (pStatement instanceof ImportStatement) {
			lGeneratedCode = this.getForImportStatement().generateSQLCode((ImportStatement) pStatement);
		}
		
		return lGeneratedCode;
	}
	
	public static SQLCodeGeneratorFactory makeInstance(CompilerType pType) {
		SQLCodeGeneratorFactory lFactoryReturned = null;
		
		switch (pType) {
			case ORACLE:
				lFactoryReturned = new OracleSQLCodeGeneratorFactory();
				break;
		}
		
		return lFactoryReturned;
	}
}
