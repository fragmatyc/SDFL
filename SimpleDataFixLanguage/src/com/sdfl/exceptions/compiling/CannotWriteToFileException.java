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
package com.sdfl.exceptions.compiling;

import java.io.File;

/**
 * @author Sylvain Cloutier
 */
@SuppressWarnings("serial")
public class CannotWriteToFileException extends CompilingException {

	private File targetFile;
	private String dataToBeWritten;
	
	public CannotWriteToFileException(File pFileToWrite, String pData) {
		this.targetFile = pFileToWrite;
		this.dataToBeWritten = pData;
	}

	public File getTargetFile() {
		return this.targetFile;
	}

	public String getDataToBeWritten() {
		return this.dataToBeWritten;
	}
	
}
