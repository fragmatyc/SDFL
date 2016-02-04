/**
 * This file is part of the Simple Data Fix Language (SDFL) core.
 * 
 * All components of the language (compiler, interpreter, etc.) are
 * free and open source: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package com.sdfl.statements.impl;

import com.sdfl.statements.Statement;

/**
 * Object representation of the "create datafix" statement.
 * @author Sylvain Cloutier
 */
public final class CreateDatafixStatement extends Statement {
	private String datafixId;
	private String datafixDescription;


	public String getDatafixId() {
		return datafixId;
	}
	
	public String getDatafixDescription() {
		return datafixDescription;
	}

	public void setDatafixId(String datafixId) {
		this.datafixId = datafixId;
	}

	public void setDatafixDescription(String datafixDescription) {
		this.datafixDescription = datafixDescription;
	}

}
