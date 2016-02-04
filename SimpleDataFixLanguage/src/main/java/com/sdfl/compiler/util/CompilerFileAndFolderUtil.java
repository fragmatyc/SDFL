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

/**
 * @author Sylvain Cloutier
 */
public interface CompilerFileAndFolderUtil {
	
	public abstract void createFolder(File pFolder);

	public abstract void deleteFolder(File pFolder);

	public abstract boolean notExists(File pFile);

	public abstract boolean exists(File pFile);

	public abstract boolean isDirectory(File pFile);

	public abstract boolean isFile(File pFile);

	public abstract void createFile(File pFileToCreate);
	
	public abstract String readFile(File pFile);

	public abstract void appendAtTheEndOfFile(File pFileToWrite, String pLine);

}