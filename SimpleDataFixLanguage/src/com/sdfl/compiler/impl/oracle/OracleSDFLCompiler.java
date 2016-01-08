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

import java.io.File;

import com.sdfl.compiler.CompilerType;
import com.sdfl.compiler.SDFLCompiler;
import com.sdfl.compiler.sql.SQLCodeGeneratorFactory;
import com.sdfl.statements.Statement;
import com.sdfl.statements.impl.CreateDatafixStatement;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.impl.InPackageStatement;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.impl.StepStatement;
import com.sdfl.statements.impl.UpdateStatement;

/**
 * Compiles SDFL code into and Oracle SQL package.
 * @author Sylvain Cloutier
 */
public class OracleSDFLCompiler extends SDFLCompiler {
	private static final String EXECUTE_COMMAND = "@@";
	private static final String SQL_EXT = ".sql";
	private static final String RUN_ALL_SQL = "runAll" + SQL_EXT;
	private static final String RUNME_SQL = "runme" + SQL_EXT;

	private File packageFolderHandle;
	private File runAllFileHandle;
	private File datafixFolderHandle;
	private File runMeFileHandle;
	private File stepFileHandle;
	
	public OracleSDFLCompiler() {
		this.parameters = new OracleCompilerParameters();
		this.utilities.setCodeGenerator(SQLCodeGeneratorFactory.makeInstance(CompilerType.ORACLE));
	}

	@Override
	protected void compile(Statement pStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void compile(InPackageStatement pStatement) {
		this.deletePackageFolderIfExistAndCreate(pStatement);
		this.createRunAllSQL();
	}

	private void createRunAllSQL() {
		this.runAllFileHandle = this.buildFileHandle(this.packageFolderHandle, RUN_ALL_SQL);
		this.utilities.getFileHelper().createFile(this.runAllFileHandle);
	}

	private void deletePackageFolderIfExistAndCreate(
			InPackageStatement pStatement) {
		
		this.packageFolderHandle = this.buildFileHandle(this.parameters.getDestinationFolder(), 
				pStatement.getPackageName());
		
		if (this.utilities.getFileHelper().exists(this.packageFolderHandle)) {
			this.utilities.getFileHelper().deleteFolder(this.packageFolderHandle);
		}
		
		this.utilities.getFileHelper().createFolder(this.packageFolderHandle);
	}

	@Override
	protected void compile(CreateDatafixStatement pStatement) {
		this.createDatafixFolder(pStatement);
		this.createRunmeSQLFile();
		this.setCallToRunmeInPackageRunAll(pStatement);
	}

	@Override
	protected void compile(ImportStatement pStatement) {
		this.buildStatementSQLCodeAndAppendToStepFile(pStatement);
	}
	
	@Override
	protected void compile(UpdateStatement pStatement) {
		this.buildStatementSQLCodeAndAppendToStepFile(pStatement);
	}

	@Override
	protected void compile(StepStatement pStatement) {
		String lStepFileName = pStatement.getIdentifier() + SQL_EXT;
		
		this.stepFileHandle = this.buildFileHandle(this.datafixFolderHandle, lStepFileName);
		this.utilities.getFileHelper().createFile(this.stepFileHandle);		
		this.utilities.getFileHelper().appendAtTheEndOfFile(this.runMeFileHandle, EXECUTE_COMMAND + lStepFileName);
	
	}

	@Override
	protected void preCompile() {
		
	}

	@Override
	protected void postCompile() {
		
	}

	@Override
	protected void compile(InsertIntoStatement pStatement) {
		this.buildStatementSQLCodeAndAppendToStepFile(pStatement);
	}

	private void buildStatementSQLCodeAndAppendToStepFile(Statement pStatement) {
		String lSQLCode = this.utilities.getCodeGenerator().generateStatementSQLCode(pStatement);
		this.utilities.getFileHelper().appendAtTheEndOfFile(this.stepFileHandle, lSQLCode);
	}

	@Override
	public OracleCompilerParameters getParameters() {
		return (OracleCompilerParameters) this.parameters;
	}

	private File buildFileHandle(File pInFolder, String pFileName) {
		return new File(pInFolder.getAbsolutePath() + File.separator + pFileName);
	}	


	private void setCallToRunmeInPackageRunAll(CreateDatafixStatement pStatement) {
		this.utilities.getFileHelper().appendAtTheEndOfFile(this.runAllFileHandle, EXECUTE_COMMAND + pStatement.getDatafixId() + File.separator + RUNME_SQL);
	}

	private void createRunmeSQLFile() {
		this.runMeFileHandle = this.buildFileHandle(this.datafixFolderHandle, RUNME_SQL);
		this.utilities.getFileHelper().createFile(this.runMeFileHandle);
	}

	private void createDatafixFolder(CreateDatafixStatement pStatement) {
		this.datafixFolderHandle = this.buildFileHandle(this.packageFolderHandle, pStatement.getDatafixId());
		this.utilities.getFileHelper().createFolder(this.datafixFolderHandle);
	}
}
