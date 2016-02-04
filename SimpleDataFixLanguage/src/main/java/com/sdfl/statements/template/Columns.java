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
package com.sdfl.statements.template;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The columns definition of a {@link UsingTemplateStatement}
 * @author Sylvain Cloutier
 */
public class Columns implements Iterable<String> {
	private Map<String, String> columnNamesValue;
	
	public Columns() {
		this.columnNamesValue = new LinkedHashMap<>();
	}
	
	public int count() {
		return this.columnNamesValue.values().size();
	}

	public String get(int pId) {
		String lColumnFound = null;
		
		int i = 0;
		Iterator<String> lIterator = this.columnNamesValue.keySet().stream().iterator();
		
		while (lIterator.hasNext()) {
			String lCurrentColumn = lIterator.next();
			
			if (i == pId) {
				lColumnFound = lCurrentColumn;
				break;
			}
			
			i ++;
		}
		
		return lColumnFound;
	}

	public void put(String pKey, String pValue) {
		this.columnNamesValue.put(pKey, pValue);
	}

	public String get(String pColumnName) {
		return this.columnNamesValue.get(pColumnName);
	}

	@Override
	public Iterator<String> iterator() {
		return this.columnNamesValue.keySet().stream().iterator();
	}

}
