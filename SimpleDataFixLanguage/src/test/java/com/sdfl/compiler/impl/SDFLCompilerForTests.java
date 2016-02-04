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
package com.sdfl.compiler.impl;

import com.sdfl.compiler.CompilerParameters;
import com.sdfl.compiler.SDFLCompiler;
import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.DeleteFromStatement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.impl.StepStatement;
import com.sdfl.statements.impl.UpdateStatement;

/**
 * Only to test {@link SDFLCompiler}.
 * @see SDFLCompilerTest
 * @author Sylvain Cloutier
 */
public class SDFLCompilerForTests extends SDFLCompiler {

	public SDFLCompilerForTests() {
		this.parameters = new CompilerParameters() {};
	}
	
	@Override
	protected void compile(Statement pStatement) {
	}

	@Override
	protected void compile(InPackageStatement pStatement) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void compile(CreateDatafixStatement pStatement) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void compile(ImportStatement pStatement) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void compile(InsertIntoStatement pStatement) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void compile(UpdateStatement pStatement) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void preCompile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postCompile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void compile(StepStatement pStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void compile(DeleteFromStatement pStatement) {
		// TODO Auto-generated method stub
		
	}

}
