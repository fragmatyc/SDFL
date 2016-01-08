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
package com.sdfl.interpreter;

import com.sdfl.code.splitter.SDFLCodeSplitterFactory;
import com.sdfl.code.splitter.commentswithdrawal.CommentsWithdrawalCodeSplitter;
import com.sdfl.code.splitter.simpledelimiter.SimpleDelimiterCodeSplitter;
import com.sdfl.statements.Statement;
import com.sdfl.statements.Statements;
import com.sdfl.statements.parser.StatementParser;

/**
 * This class is the SDFL code interpreter. It basically
 * transforms source code into {@link Statement}.
 * @author Sylvain Cloutier
 */
public class SDFLInterpreter {
	private static final String STATEMENT_SEPARATOR = ";";
	
	private SimpleDelimiterCodeSplitter simpleDelimiterSplitter;
	private CommentsWithdrawalCodeSplitter commentsWithdrawalSplitter;
	
	private StatementParser statementParser;
	private Statements statements;

	public SDFLInterpreter() {
		this.statementParser = new StatementParser();
		this.statements = new Statements();
		
		this.simpleDelimiterSplitter = SDFLCodeSplitterFactory.makeCodeSplitterInstance(SimpleDelimiterCodeSplitter.class);
		this.commentsWithdrawalSplitter = SDFLCodeSplitterFactory.makeCodeSplitterInstance(CommentsWithdrawalCodeSplitter.class);
	}
	
	public void parseCode(String pCode) {
		
		if (this.isCodeParsable(pCode)) {

			String lPreformattedCode = this.preformatCode(pCode);
			
			this.simpleDelimiterSplitter.getParameters().setDelimiter(STATEMENT_SEPARATOR);
			this.simpleDelimiterSplitter.getParameters().setKeepDelimiter(false);
			
			for (String lCurrentStatement : this.simpleDelimiterSplitter.split(lPreformattedCode)) {
				Statement lParsedStatement = this.statementParser.parseStatement(lCurrentStatement);
				this.statements.add(lParsedStatement);
			}
		}
	}
	
	private boolean isCodeParsable(String pCode) {
		return pCode != null && !pCode.isEmpty();
	}

	private String preformatCode(String pCode) {
		String lCommentLessCode = this.commentsWithdrawalSplitter.removeComments(pCode);
		return this.removeCarriageReturns(lCommentLessCode);
	}

	private String removeCarriageReturns(String pCode) {
		StringBuilder lBuilder = new StringBuilder();
		this.simpleDelimiterSplitter.getParameters().setDelimiter(System.lineSeparator());
		this.simpleDelimiterSplitter.getParameters().setKeepDelimiter(false);
	
		for (String lCurrentLineOfCode : this.simpleDelimiterSplitter.split(pCode)) {
			String lTrimmedLine = lCurrentLineOfCode.trim();
			
			if (lTrimmedLine.length() > 0) {
				lBuilder.append(lTrimmedLine);
				lBuilder.append(" ");
			}
		}
		
		return lBuilder.toString();
	}

	public Statements getStatements() {
		return this.statements;
	}
	
}
