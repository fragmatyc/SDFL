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
package com.sdfl.statements.parser;

import java.util.Arrays;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.exceptions.parsing.UnexpectedKeywordException;
import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.builders.impl.CreateDatafixStatementBuilder;
import com.sdfl.statements.builders.impl.DeleteFromStatementBuilder;
import com.sdfl.statements.builders.impl.ImportStatementBuilder;
import com.sdfl.statements.builders.impl.InPackageStatementBuilder;
import com.sdfl.statements.builders.impl.InsertIntoStatementBuilder;
import com.sdfl.statements.builders.impl.StepStatementBuilder;
import com.sdfl.statements.builders.impl.UpdateStatementBuilder;
import com.sdfl.statements.impl.ErrorStatement;
import com.sdfl.statements.tokenizer.StatementTokenizer;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Used to parse a single {@link Statement} from a statement code.
 * @author Sylvain Cloutier
 */
public class StatementParser {
	public static final String[] EXPECTED_LINE_START_KEYWORDS = {
		SDFLKeywords.IN,
		SDFLKeywords.CREATE,
		SDFLKeywords.IMPORT,
		SDFLKeywords.UPDATE,
		SDFLKeywords.INSERT,
		SDFLKeywords.DELETE,
		SDFLKeywords.STEP
	};
	
	private StatementTokenizer tokenizer;

	public StatementParser() {
		this.tokenizer = new StatementTokenizer();
	}
	
	public Statement parseStatement(String pStatementCode) {
		
		Statement lReturnStatement = null;
		StatementTokens lTokens = tokenizer.tokenize(pStatementCode);

		String lCurrentToken = lTokens.consumeNext();
		
		StatementBuilder lStatementBuilder = null;
		
		try {

			if (isTokenExpected(lCurrentToken)) {
			
				if (lCurrentToken.equals(SDFLKeywords.IN)) {
					lStatementBuilder = new InPackageStatementBuilder(lTokens);
				} else if (lCurrentToken.equals(SDFLKeywords.CREATE)) {
					lStatementBuilder = new CreateDatafixStatementBuilder(lTokens); 
				} else if (lCurrentToken.equals(SDFLKeywords.IMPORT)) {
					lStatementBuilder = new ImportStatementBuilder(lTokens);
				} else if (lCurrentToken.equals(SDFLKeywords.UPDATE)) {
					lStatementBuilder = new UpdateStatementBuilder(lTokens);
				} else if (lCurrentToken.equals(SDFLKeywords.INSERT)) {
					lStatementBuilder = new InsertIntoStatementBuilder(lTokens);
				} else if (lCurrentToken.equals(SDFLKeywords.DELETE)) {
					lStatementBuilder = new DeleteFromStatementBuilder(lTokens);
				} else if (lCurrentToken.equals(SDFLKeywords.STEP)) {
					lStatementBuilder = new StepStatementBuilder(lTokens);
				}
			
			} else {
				throw new UnexpectedKeywordException(lCurrentToken, EXPECTED_LINE_START_KEYWORDS);
			}
			
			lReturnStatement = lStatementBuilder.build();
		} catch (Exception pException) {
			lReturnStatement = new ErrorStatement(pException);
		}
		
		lReturnStatement.setCode(pStatementCode);
		return lReturnStatement;
	}

	private boolean isTokenExpected(String lCurrentToken) {
		return Arrays.asList(EXPECTED_LINE_START_KEYWORDS).contains(lCurrentToken);
	}

}
