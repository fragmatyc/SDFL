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

import com.sdfl.statements.Statement;

/**
 * Representation of the "using template" statement.
 * @author Sylvain Cloutier
 */
public class UsingTemplateStatement extends Statement {
	private Columns columns;

	public UsingTemplateStatement() {
		this.columns = new Columns();
	}
	
	public Columns getColumns() {
		return this.columns;
	}

	public String getColumn(int pId) {
		return this.columns.get(pId);
	}

	public void put(String pKey, String pValue) {
		this.columns.put(pKey, pValue);
	}

	public String get(String pColumnName) {
		return this.columns.get(pColumnName);
	}

}
