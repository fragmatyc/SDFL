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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sdfl.exceptions.compiling.CannotCreateFileException;
import com.sdfl.exceptions.compiling.CannotCreateFolderException;
import com.sdfl.exceptions.compiling.CannotDeleteFolderException;
import com.sdfl.exceptions.compiling.CannotReadFileException;
import com.sdfl.exceptions.compiling.CannotWriteToFileException;

/**
 * Utility class to be used by the compilers in order to
 * manipulate files.
 * 
 * @author Sylvain Cloutier
 */
public class CompilerFileAndFolderUtilImpl implements CompilerFileAndFolderUtil {
	@Override
	public void createFolder(File pFolder) {
		boolean lFolderCreated = pFolder.mkdir();
		if (!lFolderCreated) {
			throw new CannotCreateFolderException(pFolder);
		}
	}

	@Override
	public void deleteFolder(File pFolder) {
		try {
			FileUtils.deleteDirectory(pFolder);
		} catch (IOException e) {
			throw new CannotDeleteFolderException(pFolder);
		}
	}
	
	@Override
	public boolean notExists(File pFile) {
		return !this.exists(pFile);
	}
	
	@Override
	public boolean exists(File pFile) {
		return pFile.exists();
	}
	
	@Override
	public boolean isDirectory(File pFile) {
		return pFile.isDirectory();
	}
	
	@Override
	public boolean isFile(File pFile) {
		return pFile.isFile();
	}

	@Override
	public void createFile(File pFileToCreate) {
		try {
			pFileToCreate.createNewFile();
		} catch (IOException e) {
			throw new CannotCreateFileException(pFileToCreate);
		}
	}
	
	@Override
	public void appendAtTheEndOfFile(File pFileToWrite, String pLine) {
		try {
			FileUtils.writeStringToFile(pFileToWrite, pLine + System.lineSeparator(), true);
		} catch (IOException e) {
			throw new CannotWriteToFileException(pFileToWrite, pLine);
		}
	}

	@Override
	public String readFile(File pFile) {
		String lContent = null;
		
		try {
			lContent = FileUtils.readFileToString(pFile);
		} catch (IOException e) {
			throw new CannotReadFileException(pFile);
		}
		
		return lContent;
	}
}
