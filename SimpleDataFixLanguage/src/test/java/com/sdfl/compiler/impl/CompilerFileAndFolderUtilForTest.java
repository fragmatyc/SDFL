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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sdfl.compiler.util.CompilerFileAndFolderUtil;


/**
 * @author Sylvain Cloutier
 */
public class CompilerFileAndFolderUtilForTest implements CompilerFileAndFolderUtil {
	private Map<File, StringBuilder> filesAndContent;
	
	public CompilerFileAndFolderUtilForTest() {
		this.filesAndContent = new HashMap<>();
	}
	
	@Override
	public void createFolder(File pFolder) {
		
	}

	@Override
	public void deleteFolder(File pFolder) {
		
	}

	@Override
	public boolean notExists(File pFile) {
		return false;
	}

	@Override
	public boolean exists(File pFile) {
		return false;
	}

	@Override
	public boolean isDirectory(File pFile) {
		return false;
	}

	@Override
	public boolean isFile(File pFile) {
		return false;
	}

	@Override
	public void createFile(File pFileToCreate) {
		
	}

	@Override
	public void appendAtTheEndOfFile(File pFileToWrite, String pLine) {
		StringBuilder lBuilder = this.filesAndContent.get(pFileToWrite);
		
		if (lBuilder == null) {
			lBuilder = new StringBuilder();
			this.filesAndContent.put(pFileToWrite, lBuilder);
		}
		
		lBuilder.append(pLine);
	}

	@Override
	public String readFile(File pFile) {
		return this.filesAndContent.get(pFile).toString();
	}
	
}
