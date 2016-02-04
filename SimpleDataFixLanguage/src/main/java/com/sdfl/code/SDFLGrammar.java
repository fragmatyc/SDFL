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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * This class is an abstract class that uses reflection to find the declared fields of
 * its subclasses. Those fields contains the different reserved keywords for the language.
 * 
 * @see SDFLDelimiters
 * @see SDFLKeywords
 * @see SDFLSyntax 
 * @author Sylvain Cloutier
 */
public abstract class SDFLGrammar {
	protected static List<String> valuesOf(Class<? extends SDFLGrammar> pGrammarClass) {
		List<String> lValues = new LinkedList<>();
		for (Field lCurField : pGrammarClass.getDeclaredFields()) {
			try {
				lValues.add((String) lCurField.get(String.class));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
			}
		}
		
		Collections.sort(lValues, new StringComparatorByLength());
		return lValues;
	}
	
	protected static boolean isValidGrammar(List<String> lValues, String lCurrentToken) {
		boolean lFound = false;
		
		for (String lCurSyntax : lValues) {
			if (lCurSyntax.equals(lCurrentToken)) {
				lFound = true;
				break;
			}
		}
		
		return lFound;
	}
}
