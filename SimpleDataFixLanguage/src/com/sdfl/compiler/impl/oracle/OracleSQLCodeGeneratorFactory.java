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
package com.sdfl.compiler.impl.oracle;

import com.sdfl.compiler.impl.oracle.sql.statement.OracleImportStatementSQLCodeGenerator;
import com.sdfl.compiler.impl.oracle.sql.statement.OracleInsertIntoStatementSQLCodeGenerator;
import com.sdfl.compiler.impl.oracle.sql.statement.OracleUpdateStatementSQLCodeGenerator;
import com.sdfl.compiler.sql.SQLCodeGeneratorFactory;
import com.sdfl.compiler.sql.statement.ImportStatementSQLCodeGenerator;
import com.sdfl.compiler.sql.statement.InsertIntoStatementSQLCodeGenerator;
import com.sdfl.compiler.sql.statement.UpdateStatementSQLCodeGenerator;

/**
 * Base {@link SQLCodeGeneratorFactory} for Oracle SQL code
 * @author Sylvain Cloutier
 */
public class OracleSQLCodeGeneratorFactory extends SQLCodeGeneratorFactory {

	@Override
	protected InsertIntoStatementSQLCodeGenerator getForInsertIntoStatement() {
		return new OracleInsertIntoStatementSQLCodeGenerator();
	}

	@Override
	protected UpdateStatementSQLCodeGenerator getForUpdateStatement() {
		return new OracleUpdateStatementSQLCodeGenerator();
	}

	@Override
	protected ImportStatementSQLCodeGenerator getForImportStatement() {
		return new OracleImportStatementSQLCodeGenerator();
	}

}
