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
 * Keywords holder class. This class should only contain static Strings with
 * all lowercase reserved keywords of the language.
 * 
 * @see SDFLGrammar
 * @author Sylvain Cloutier
 */
public abstract class SDFLKeywords extends SDFLGrammar {
	public static final String IN = "in";
	public static final String PACKAGE = "package";
	public static final String CREATE = "create";
	public static final String DATAFIX = "datafix";
	public static final String IMPORT = "import";
	public static final String INTO = "into";
	public static final String USING = "using";
	public static final String TEMPLATE = "template";
	public static final String WITH = "with";
	public static final String HEADERS = "headers";
	public static final String UPDATE = "update";
	public static final String ONLY = "only";
	public static final String IF = "if";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String NOT = "not";
	public static final String EXIST = "exist";
	public static final String INSERT = "insert";
	public static final String DELETE = "delete";
	public static final String STEP = "step";
	public static final String FROM = "from";
	
	public static boolean isKeyword(String lCurrentToken) {
		return SDFLGrammar.isValidGrammar(SDFLKeywords.values(), lCurrentToken);
	}
	
	public static List<String> values() {
		return SDFLGrammar.valuesOf(SDFLKeywords.class);
	}
}
