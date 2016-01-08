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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a row of a data input file.
 * @author Sylvain Cloutier
 */
public class ImportInputFileRow {
	private Map<Integer, String> columnsValues;
	
	public ImportInputFileRow() {
		this.columnsValues = new LinkedHashMap<>();
	}
	
	public String getColumnValue(int pColumnId) {
		String lValue = this.columnsValues.get(pColumnId);
		return lValue;
	}
	
	public int countColumns() {
		return this.columnsValues.keySet().size();
	}

	public void setColumn(int pColumnId, String pStringColumnValue) {
		this.columnsValues.put(pColumnId, pStringColumnValue);
	}
}
