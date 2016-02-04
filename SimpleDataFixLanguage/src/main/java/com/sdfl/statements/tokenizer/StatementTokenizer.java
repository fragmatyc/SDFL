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
package com.sdfl.statements.tokenizer;

import java.util.LinkedList;
import java.util.List;

import com.sdfl.code.SDFLSyntax;
import com.sdfl.code.splitter.SDFLCodeSplitterFactory;
import com.sdfl.code.splitter.simpledelimiter.SimpleDelimiterCodeSplitter;



public class StatementTokenizer {
	private SimpleDelimiterCodeSplitter splitter;
	
	public StatementTokenizer() {
		this.splitter = SDFLCodeSplitterFactory.makeCodeSplitterInstance(SimpleDelimiterCodeSplitter.class);
	}
	
	public StatementTokens tokenize(String pCode) {
		StatementTokens lStatementTokens = new StatementTokens();
		
		List<String> lStringTokens = preformatAndSplitAtSpaces(pCode);
		
		this.splitter.getParameters().setKeepDelimiter(true);
		for (String lCurSyntax : SDFLSyntax.values()) {
			this.splitter.getParameters().setDelimiter(lCurSyntax);
			
			lStringTokens = splitTokensAtCurrentSyntaxDelimiter(lStringTokens);
		}
		
		lStatementTokens.addAll(lStringTokens);
		return lStatementTokens;
	}

	private List<String> splitTokensAtCurrentSyntaxDelimiter(List<String> lStringTokens) {
		List<String> lNewTokens = new LinkedList<String>();
		for (String lCurrentToken : lStringTokens) {
			if (SDFLSyntax.isSyntax(lCurrentToken)) {
				lNewTokens.add(lCurrentToken);
			} else {
				List<String> lCurrentTokenSplitted = this.splitter.split(lCurrentToken);
				lNewTokens.addAll(lCurrentTokenSplitted);
			}
		}
		
		return lNewTokens;
	}

	private List<String> preformatAndSplitAtSpaces(String pCode) {
		String lTrimedCode = pCode.trim();
		this.splitter.getParameters().setKeepDelimiter(false);
		this.splitter.getParameters().setDelimiter(" ");
		List<String> lStringTokens = this.splitter.split(lTrimedCode);
		return lStringTokens;
	}
}
