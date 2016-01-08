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

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;

import com.sdfl.compiler.impl.SDFLCompilerTestBase;

/**
 * Tests suite for {@link OracleSDFLCompiler}
 * @author Sylvain Cloutier
 */
public class OracleSDFLCompilerTest extends SDFLCompilerTestBase<OracleSDFLCompiler> {
	private static final File PACKAGE_FOLDER = new File(DEFAULT_COMPILE_DESTINATION.getAbsolutePath() + File.separator + "MyDataFixPackage");
	private static final File RUNALL = new File(PACKAGE_FOLDER.getAbsolutePath() + File.separator + "runAll.sql");
	private static final File DATAFIX_FOLDER = new File(PACKAGE_FOLDER.getAbsolutePath() + File.separator + "MYDFID");
	
	@Test
	public void testThatCreateFolderIsCalledForThePackage() {
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).createFolder(PACKAGE_FOLDER);
	}
	
	@Test
	public void testThatInPackageStatementCreatesThePackageFolderIfNotExist() {
		when(this.fileUtil.exists(PACKAGE_FOLDER)).thenReturn(false);
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil, never()).deleteFolder(PACKAGE_FOLDER);
	}
	
	@Test
	public void testThatInPackageStatementDeletesThePackageIfExist() {
		when(this.fileUtil.exists(PACKAGE_FOLDER)).thenReturn(true);
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).deleteFolder(PACKAGE_FOLDER);
	}
	
	@Test
	public void testThatRunAllSQLFileIsCreated() {
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).createFile(RUNALL);
	}
	
	@Test
	public void testThatCreateDatafixStatementCreatesTheDataFixFolder() {
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).createFolder(DATAFIX_FOLDER);
	}
	
	@Test
	public void testThatCreateDatafixStatementCreatesARunme() {
		File lExpectedRunmeSQL = new File(DATAFIX_FOLDER.getAbsolutePath() + File.separator + "runme.sql");
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).createFile(lExpectedRunmeSQL);
	}
	
	@Test
	public void testThatStepStatementCreatesASeparatedFile() {
		File lExpectedStepFile = new File(DATAFIX_FOLDER.getAbsolutePath() + File.separator + "insertDataInTable.sql");
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).createFile(lExpectedStepFile);
	}
	
	@Test
	public void testThatCreateDatafixStatementAddRunmeCallToRunAll() {
		this.compile(OracleCodeBank.VALID_CODE_ONE_INSERT);
		verify(this.fileUtil).appendAtTheEndOfFile(RUNALL, "@@MYDFID" + File.separator + "runme.sql");
	}
	
}
