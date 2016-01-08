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
package com.sdfl.compiler.sql;

import com.sdfl.code.SDFLDelimiters;

/**
 * @author Sylvain Cloutier
 */
public abstract class SQLCodeGenerator {
	protected StringBuilder builder;

	public SQLCodeGenerator() {
		this.builder = new StringBuilder();
	}
	
	protected void resetBuilder() {
		this.builder.delete(0, this.builder.length());
	}

	protected String removeStringDelimiterIfNecessary(String pStringValue) {
		if (this.hasStringDelimiters(pStringValue)) {
			pStringValue = pStringValue.substring(1, pStringValue.length() - 1);
		}
		
		return pStringValue;
	}

	protected boolean hasStringDelimiters(String pStringValue) {
		return pStringValue.startsWith(SDFLDelimiters.STRING_DELIMITER) && pStringValue.endsWith(SDFLDelimiters.STRING_DELIMITER);
	}

	protected void appendIfNotLast(String pToAppend, int pCount, int pCurrentIdx) {
		if (pCurrentIdx < pCount - 1) {
			this.builder.append(pToAppend);
		}
	}

}