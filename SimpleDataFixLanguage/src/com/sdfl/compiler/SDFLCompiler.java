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
package com.sdfl.compiler;

import java.util.LinkedList;
import java.util.List;

import com.sdfl.compiler.util.CompilerUtilities;
import com.sdfl.compiler.validator.StatementsValidator;
import com.sdfl.exceptions.compiling.CompilingException;
import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.impl.StepStatement;
import com.sdfl.statements.impl.UpdateStatement;


/**
 * The base class of all SDFL compilers.
 * @author Sylvain Cloutier
 */
public abstract class SDFLCompiler {
	protected CompilerParameters parameters;
	protected CompilerUtilities utilities;
	
	private List<CompilingException> compilationExceptions;
	private String initialCode;
	
	public SDFLCompiler() {
		this.utilities = new CompilerUtilities();
		this.compilationExceptions = new LinkedList<>();
	}
	
	protected abstract void preCompile();
	protected abstract void compile(Statement pStatement);
	protected abstract void compile(InPackageStatement pStatement);
	protected abstract void compile(CreateDatafixStatement pStatement);
	protected abstract void compile(ImportStatement pStatement);
	protected abstract void compile(InsertIntoStatement pStatement);
	protected abstract void compile(UpdateStatement pStatement);
	protected abstract void compile(StepStatement pStatement);
	protected abstract void postCompile();
	
	public void masterPostCompile() {
		// TODO Find a way to clean
	}
	
	public void interpretAndValidateCode() {
		this.createDestinationIfNecessary();
		
		this.utilities.getInterpreter().parseCode(this.initialCode);
		StatementsValidator lValidator = new StatementsValidator(this.utilities.getInterpreterStatements());
		lValidator.validate();
		
	}
	
	public void compile(String pCode) {
		this.initialCode = pCode;
		
		this.interpretAndValidateCode();
		this.preCompile();
		
		for (Statement lCurStatement : this.utilities.getInterpreterStatements()) {
			this.dispatchCompilationToImplementationMethods(lCurStatement);
		}
		
		this.postCompile();
		this.masterPostCompile();		
	}

	private void dispatchCompilationToImplementationMethods(
			Statement lCurStatement) {
		
		try {
			if (lCurStatement instanceof InPackageStatement) {
				this.compile((InPackageStatement) lCurStatement);
				
			} else if (lCurStatement instanceof CreateDatafixStatement) {
				this.compile((CreateDatafixStatement) lCurStatement);
				
			} else if (lCurStatement instanceof ImportStatement) {
				this.compile((ImportStatement) lCurStatement);
				
			} else if (lCurStatement instanceof InsertIntoStatement) {
				this.compile((InsertIntoStatement) lCurStatement);
				
			} else if (lCurStatement instanceof UpdateStatement) {
				this.compile((UpdateStatement) lCurStatement);
				
			} else if (lCurStatement instanceof StepStatement) {
				this.compile((StepStatement) lCurStatement);
			}
		} catch (CompilingException pCompileEx) {
			this.compilationExceptions.add(pCompileEx);
		}
	}

	private void createDestinationIfNecessary() {
		if (this.utilities.getFileHelper().notExists(this.parameters.getDestinationFolder())) {
			this.utilities.getFileHelper().createFolder(this.parameters.getDestinationFolder());
		}
	}
	
	public void setParameters(CompilerParameters pParameters) {
		this.parameters = pParameters;
	}

	public CompilerParameters getParameters() {
		return this.parameters;
	}

	public CompilerUtilities getUtilities() {
		return this.utilities;
	}

	public final List<CompilingException> getCompilationExceptions() {
		return this.compilationExceptions;
	}

	public String getInitialCode() {
		return this.initialCode;
	}

}
