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
 * Remove expected code from tests suites for readability
 * @author Sylvain Cloutier
 */
public class OracleCodeBankExpected {

	public static final String BASIC_INSERT = 	
			"INSERT INTO" + System.lineSeparator() +
			"    MY_TABLE (" + System.lineSeparator() +
			"        MY_COLUMN_1," + System.lineSeparator() +
			"        MY_COLUMN_2," + System.lineSeparator() +
			"        MY_COLUMN_3," + System.lineSeparator() +
			"        MY_COLUMN_4)" + System.lineSeparator() +
			"VALUES (" + System.lineSeparator() +
			"    'Value1'," + System.lineSeparator() +
			"    'Value2'," + System.lineSeparator() +
			"    'Value3'," + System.lineSeparator() + 
			"    'Value4');";
	
	public static final String BASIC_UPDATE = 
			"UPDATE" + System.lineSeparator() +
			"    MY_TABLE" + System.lineSeparator() +
			"SET" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'Value1'," + System.lineSeparator() +
			"    MY_COLUMN_2 = 'Value2'," + System.lineSeparator() +
			"    MY_COLUMN_3 = 'Value3'" + System.lineSeparator() +
			"WHERE" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'My value 1'" + System.lineSeparator() +
			"    AND MY_COLUMN_2 != 'My value 2';";
	
	public static final String UPDATE_WITH_SUBGROUP = 
			"UPDATE" + System.lineSeparator() +
			"    MY_TABLE" + System.lineSeparator() +
			"SET" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'Value1'," + System.lineSeparator() +
			"    MY_COLUMN_2 = 'Value2'," + System.lineSeparator() +
			"    MY_COLUMN_3 = 'Value3'" + System.lineSeparator() +
			"WHERE" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'My value 1'" + System.lineSeparator() +
			"    AND (MY_COLUMN_2 != 'My value 2'"+ System.lineSeparator() + 
			"        OR MY_COLUMN_3 = 'My value 3');";

	public static final String COMPLICATED_WHERE_CLAUSE = 
			"UPDATE" + System.lineSeparator() +
			"    MY_TABLE" + System.lineSeparator() +
			"SET" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'Value1'," + System.lineSeparator() +
			"    MY_COLUMN_2 = 'Value2'," + System.lineSeparator() +
			"    MY_COLUMN_3 = 'Value3'" + System.lineSeparator() +
			"WHERE" + System.lineSeparator() +
			"    MY_COLUMN_1 = 'Value 1'" + System.lineSeparator() +
			"    AND (MY_COLUMN_2 = 'Value 2'" + System.lineSeparator() + 
			"        OR MY_COLUMN_2 = 'Value 3'" + System.lineSeparator() + 
			"        OR (MY_COLUMN_3 != 'Value 2'" + System.lineSeparator() + 
			"            AND MY_COLUMN_3 != 'Value 3'));";
	
}
