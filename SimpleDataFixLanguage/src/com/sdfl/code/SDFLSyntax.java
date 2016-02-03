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
package com.sdfl.code;

import java.util.List;

/**
 * Syntax holder class of all SDFL syntax and operators.
 * 
 * @author Sylvain Cloutier
 */
public abstract class SDFLSyntax extends SDFLGrammar {

	public static final String ARROW = "->";
	public static final String OPEN_BRACKET = "(";
	public static final String CLOSED_BRACKET = ")";
	public static final String DESCRIPTION_ID_SEPARATOR = "-";
	public static final String COMMA = ",";
	public static final String EQUAL = "=";
	public static final String NOT_EQUAL = "!=";
	public static final String GREATER_THAN = ">";
	public static final String SMALLER_THAN = "<";

	public static boolean isSyntax(String lCurrentToken) {
		return SDFLGrammar.isValidGrammar(SDFLSyntax.values(), lCurrentToken);
	}
	
	public static List<String> values() {
		return SDFLGrammar.valuesOf(SDFLSyntax.class);
	}

}
