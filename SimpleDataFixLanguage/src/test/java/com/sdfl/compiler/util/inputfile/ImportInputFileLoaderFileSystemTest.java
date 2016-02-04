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
package com.sdfl.compiler.util.inputfile;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.sdfl.compiler.util.inputfile.impl.ImportInputFileLoaderFileSystemImpl;

/**
 * @author Sylvain Cloutier
 */
public class ImportInputFileLoaderFileSystemTest {
	private static final int ROW_COUNT = 2482;
	private static final File INPUT_FILE = new File("E:\\Tickets\\z-Tickets closed\\Data fixes\\USD3101129 - CRJ700 Dataload\\RAW\\DONE\\DONE_WDMS_END_IDENT_PARTS 7.xls");
	
	@Ignore@Test
	public void testRandomFile() {
		if (!INPUT_FILE.exists()) {
			fail();
		}
		
		ImportInputFileLoader lFileLoader = new ImportInputFileLoaderFileSystemImpl();
		ImportInputFile lFile = lFileLoader.load(INPUT_FILE);
		assertEquals(ROW_COUNT, lFile.countRows());
	}

}
