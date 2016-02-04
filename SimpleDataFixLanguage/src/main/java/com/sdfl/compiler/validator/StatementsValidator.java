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
package com.sdfl.compiler.validator;

import com.sdfl.exceptions.compiling.MissingCreateDatafixStatementException;
import com.sdfl.exceptions.compiling.MissingOrMisplacedInPackageStatementException;
import com.sdfl.exceptions.compiling.StatementsContainErrorsException;
import com.sdfl.statements.Statement;
import com.sdfl.statements.Statements;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.ErrorStatement;
import com.sdfl.statements.impl.InPackageStatement;


/**
 * Used to validate the statements before compilation.
 * @author Sylvain Cloutier
 */
public class StatementsValidator {
	private Statements statements;
	
	public StatementsValidator(Statements pStatements) {
		this.statements = pStatements;
	}
	
	public void validate() {
		this.validateNoErrorsAreFound();
		this.validateInPackageStatement();
		this.validateThereIsAtLeastOneCreateDatafixStatement();
	}
	
	private void validateInPackageStatement() {
		if (!(this.statements.get(0) instanceof InPackageStatement)) {
			throw new MissingOrMisplacedInPackageStatementException();
		}
	}
	
	private void validateNoErrorsAreFound() {
		for (Statement lCurStatement : this.statements) {
			if (lCurStatement instanceof ErrorStatement) {
				throw new StatementsContainErrorsException();
			}
		}
	}
	
	private void validateThereIsAtLeastOneCreateDatafixStatement() {
		boolean lCreateDatafixStatementFound = false;
		for (Statement lCurStatement : this.statements) {
			if (lCurStatement instanceof CreateDatafixStatement) {
				lCreateDatafixStatementFound = true;
				break;
			}
		}
		
		if (!lCreateDatafixStatementFound) {
			throw new MissingCreateDatafixStatementException();
		}
	}
}
