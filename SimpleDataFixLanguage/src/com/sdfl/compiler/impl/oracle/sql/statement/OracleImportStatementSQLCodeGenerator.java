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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sdfl.compiler.sql.statement.ImportStatementSQLCodeGenerator;
import com.sdfl.compiler.util.inputfile.ImportInputFile;
import com.sdfl.compiler.util.inputfile.ImportInputFileLoader;
import com.sdfl.compiler.util.inputfile.ImportInputFileRow;
import com.sdfl.compiler.util.inputfile.impl.ImportInputFileLoaderFileSystemImpl;
import com.sdfl.exceptions.compiling.TemplateColumnNotFoundInInputFileException;
import com.sdfl.statements.impl.InsertIntoStatement;
import com.sdfl.statements.template.UsingTemplateStatement;


/**
 * @author Sylvain Cloutier
 */
public class OracleImportStatementSQLCodeGenerator extends
	ImportStatementSQLCodeGenerator {
	
	private ImportInputFileLoader loader;
	private Map<String, Integer> mappingOfHeaders;
	private OracleInsertIntoStatementSQLCodeGenerator insertGenerator;

	public OracleImportStatementSQLCodeGenerator() {
		this.loader = new ImportInputFileLoaderFileSystemImpl();
		this.mappingOfHeaders = new HashMap<>();
		this.insertGenerator = new OracleInsertIntoStatementSQLCodeGenerator();
	}
	
	@Override
	protected String generateSQLCode() {

		File lFile = new File(this.statement.getInputFile());
		ImportInputFile lInputFile = this.loader.load(lFile);
		
		int lStartAtRowId = this.defineStartRowIdxAndColumnsHeaderIfNecessary(lInputFile); 

		for (int lCurRowId = lStartAtRowId; lCurRowId < lInputFile.countRows(); lCurRowId++) {
			ImportInputFileRow lCurRow = lInputFile.get(lCurRowId);
			
			InsertIntoStatement lInsertStatement = buildInsertIntoStatement(lCurRow);
			this.builder.append(insertGenerator.generateSQLCode(lInsertStatement));
			
			this.appendIfNotLast(System.lineSeparator(), lInputFile.countRows(), lCurRowId);
		}
		
		return this.builder.toString();
	}

	private InsertIntoStatement buildInsertIntoStatement(
			ImportInputFileRow lCurRow) {
		
		InsertIntoStatement lInsertStatement = new InsertIntoStatement();
		lInsertStatement.setTableName(this.statement.getTableName());

		for (String lCurColAsStr : this.statement.getTemplate().getColumns()) {
			UsingTemplateStatement lInsertTemplate = lInsertStatement.getTemplate();
			
			lCurColAsStr = this.statement.getTemplate().get(lCurColAsStr);
			int lCurColIdx = this.defineColumnIdx(lCurColAsStr);
			String lValue = lCurRow.getColumnValue(lCurColIdx);
			
			lInsertTemplate.put(this.statement.getTemplate().get(lCurColAsStr), lValue);
		}
		
		return lInsertStatement;
	}

	private int defineColumnIdx(String lCurColAsStr) {
		int lCurColIdx = 0;
		
		if (this.statement.isWithHeaders()) {
			lCurColIdx = this.mappingOfHeaders.get(lCurColAsStr);
		} else {
			lCurColIdx = Integer.valueOf(lCurColAsStr.replace("#", "")) - 1;
		}
		
		return lCurColIdx;
	}

	private int defineStartRowIdxAndColumnsHeaderIfNecessary(ImportInputFile lInputFile) {
		int lStartAtRowId = 0;
		
		if (this.statement.isWithHeaders()) {
			this.defineColumnHeadersVsIdx(lInputFile);
			lStartAtRowId = 1;
		}
		
		return lStartAtRowId;
	}

	private void defineColumnHeadersVsIdx(ImportInputFile lInputFile) {
		ImportInputFileRow lCurRow = lInputFile.get(0);
		
		for (String lCurColName : this.statement.getTemplate().getColumns()) {
			boolean lColumnWasFound = false;
			
			for (int lCurHeaderColIdx = 0; lCurHeaderColIdx < lCurRow.countColumns(); lCurHeaderColIdx++) {
				if (lCurColName.equals(lCurRow.getColumnValue(lCurHeaderColIdx))) {
					this.mappingOfHeaders.put(lCurColName, lCurHeaderColIdx);
					lColumnWasFound = true;
				}
			}
			
			if (!lColumnWasFound) {
				throw new TemplateColumnNotFoundInInputFileException(lCurColName);
			}
		}
	}

	public void setLoader(ImportInputFileLoader pLoader) {
		this.loader = pLoader;
	}

}
