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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Object representation of a data input file (XLS, CSV, etc.)
 * @author Sylvain Cloutier
 */
public class ImportInputFile implements Iterable<ImportInputFileRow> {
	public List<ImportInputFileRow> rows;
	
	public ImportInputFile() {
		this.rows = new ArrayList<>();
	}

	public void add(ImportInputFileRow pRow) {
		this.rows.add(pRow);
	}
	
	public ImportInputFileRow get(int pRowId) {
		return this.rows.get(pRowId);
	}
	
	public int countRows() {
		return this.rows.size();
	}
	
	@Override
	public Iterator<ImportInputFileRow> iterator() {
		return this.rows.iterator();
	}
	
}
