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
package com.sdfl.compiler.util;

import com.sdfl.compiler.sql.SQLCodeGeneratorFactory;
import com.sdfl.compiler.util.inputfile.ImportInputFileLoader;
import com.sdfl.compiler.util.inputfile.impl.ImportInputFileLoaderFileSystemImpl;
import com.sdfl.interpreter.SDFLInterpreter;
import com.sdfl.statements.Statements;

/**
 * Bundle of the classes used by the compiler, for readability.
 * @author Sylvain Cloutier
 */
public class CompilerUtilities {
	private CompilerFileAndFolderUtil fileHelper;
	private SQLCodeGeneratorFactory codeGenerator;
	private ImportInputFileLoader fileLoader;
	private SDFLInterpreter interpreter;
	
	public CompilerUtilities() {
		this.fileHelper = new CompilerFileAndFolderUtilImpl();
		this.fileLoader = new ImportInputFileLoaderFileSystemImpl();
		this.interpreter = new SDFLInterpreter();
	}
	
	public CompilerFileAndFolderUtil getFileHelper() {
		return this.fileHelper;
	}
	
	public void setFileHelper(CompilerFileAndFolderUtil pFileHelper) {
		this.fileHelper = pFileHelper;
	}
	
	public SQLCodeGeneratorFactory getCodeGenerator() {
		return this.codeGenerator;
	}
	
	public void setCodeGenerator(SQLCodeGeneratorFactory pCodeGenerator) {
		this.codeGenerator = pCodeGenerator;
	}
	
	public ImportInputFileLoader getFileLoader() {
		return this.fileLoader;
	}
	
	public void setFileLoader(ImportInputFileLoader pFileLoader) {
		this.fileLoader = pFileLoader;
	}
	
	public SDFLInterpreter getInterpreter() {
		return this.interpreter;
	}
	
	public void setInterpreter(SDFLInterpreter pInterpreter) {
		this.interpreter = pInterpreter;
	}
	
	public Statements getInterpreterStatements() {
		return this.interpreter.getStatements();
	}
	
}
