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
package com.sdfl.statements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Object representation of a series of code statement.
 * @author Sylvain Cloutier
 */
public class Statements implements Iterable<Statement> {

	private List<Statement> statements;
	
	public Statements() {
		this.statements = new LinkedList<>();
	}
	
	
	public void add(Statement pStatementToAdd) {
		this.statements.add(pStatementToAdd);
	}
	
	public int count() {
		return this.statements.size();
	}


	public Statement get(int pStatementId) {
		return this.statements.get(pStatementId);
	}

	@Override
	public Iterator<Statement> iterator() {
		return this.statements.iterator();
	}

}
