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
package com.sdfl.compiler.impl.oracle;

/**
 * Removed code from tests suite for readability
 * @author Sylvain Cloutier
 */
public abstract class OracleCodeBank {
	public static final String VALID_CODE_ONE_INSERT = 
			"in package MyDataFixPackage; " + System.lineSeparator() + 
			"  create datafix MYDFID - This is a test datafix; " + System.lineSeparator() + 
			"    step insertDataInTable; " + System.lineSeparator() + 
			"      insert into MY_TABLE using template "  + System.lineSeparator() + 
			"        \"Value1\" -> MY_COLUMN_1, "  + System.lineSeparator() + 
			"        \"Value2\" -> MY_COLUMN_2, " + System.lineSeparator() + 
			"        \"Value3\" -> MY_COLUMN_3;";
}
