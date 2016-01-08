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
package com.sdfl.compiler.util.inputfile.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sdfl.compiler.util.inputfile.ImportInputFile;
import com.sdfl.compiler.util.inputfile.ImportInputFileLoader;
import com.sdfl.compiler.util.inputfile.ImportInputFileRow;
import com.sdfl.exceptions.compiling.FileNotFoundException;
import com.sdfl.exceptions.compiling.UnrecongnizedFileFormatException;

/**
 * File system implementation of {@link ImportInputFileLoader}
 * @author Sylvain Cloutier
 */
public class ImportInputFileLoaderFileSystemImpl extends ImportInputFileLoader {
	private static final int FIRST_SHEET = 0;

	@Override
	public ImportInputFile load(File pDataFile) {
		ImportInputFile lImportInputFile = new ImportInputFile();
		
		if (!pDataFile.exists()) {
			throw new FileNotFoundException(pDataFile);
		}
		
		try (FileInputStream lWorkbookFIS = new FileInputStream(pDataFile)) {
			Workbook lWB = null;
			
			if (pDataFile.getName().toLowerCase().endsWith("xls")) {
				lWB = new HSSFWorkbook(lWorkbookFIS);
			} else if (pDataFile.getName().toLowerCase().endsWith("xlsx")) {
				lWB = new XSSFWorkbook(lWorkbookFIS);
			} else {
				throw new UnrecongnizedFileFormatException(pDataFile);
			}
			
			Sheet lFirstSheet = lWB.getSheetAt(FIRST_SHEET);
			for (Row lCurRow : lFirstSheet) {
				ImportInputFileRow lCurInputFileRow = this.loadRow(lCurRow);
				lImportInputFile.add(lCurInputFileRow);
			}
		} catch (IOException e) {
			throw new FileNotFoundException(pDataFile);
		}
		
		return lImportInputFile;
	}

	private ImportInputFileRow loadRow(Row lCurRow) {
		ImportInputFileRow lCurInputFileRow = new ImportInputFileRow();
		
		for (Cell lCurCell : lCurRow) {
			lCurInputFileRow.setColumn(lCurCell.getColumnIndex(),
					lCurCell.getStringCellValue());
		}
		
		return lCurInputFileRow;
	}

}
