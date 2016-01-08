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
package com.sdfl.code.splitter.simpledelimiter;

import java.util.LinkedList;
import java.util.List;

import com.sdfl.code.splitter.CodeSplitter;

/**
 * This is an implementation of the {@link CodeSplitter} that is used to split 
 * text based code at a single delimiter with parameters defined by 
 * {@link SimpleDelimiterCodeSplitterParameters}.
 * @author Sylvain Cloutier
 */
public class SimpleDelimiterCodeSplitter extends CodeSplitter {
	private List<String> splittedTokens;
	
	public SimpleDelimiterCodeSplitter() {
		this.parameters = new SimpleDelimiterCodeSplitterParameters();
	}
	
	public SimpleDelimiterCodeSplitter(SimpleDelimiterCodeSplitterParameters pParameters) {
		this.parameters = pParameters;
	}
	
	public SimpleDelimiterCodeSplitterParameters getParameters() {
		return (SimpleDelimiterCodeSplitterParameters) this.parameters;
	}
	
	public List<String> split(String pCode) {
		
		this.splittedTokens = new LinkedList<String>();
		this.codeToSplit = pCode;
		
		String lDelimiter = this.getParameters().getDelimiter();
		if (this.codeToSplit != null) {
			
			do {
				this.findNextUsableDelimiter(lDelimiter);
				if (delimiterWasFound()) {
					this.addChuckToTokens();
					this.addDelimiterToTokensIfNecessary();
					this.findNextUsableDelimiter(lDelimiter);
				}
			} while (delimiterWasFound());
			
			this.addRemainingCodeIfAny();
		}
				
		return this.splittedTokens;
	}

	private void addChuckToTokens() {
		this.addToken();
		this.truncateRemainingString();
	}

	private void truncateRemainingString() {
		int lTruncateRemainingStringAtIdx = this.lastDelimiterFound + 
				this.getParameters().getDelimiter().length();
		this.codeToSplit = this.codeToSplit.substring(lTruncateRemainingStringAtIdx).trim();
	}

	private void addToken() {
		int lIdxEndOfToken = this.lastDelimiterFound;
		
		String lTokenToAdd = this.codeToSplit.substring(0, lIdxEndOfToken).trim();
		if (lTokenToAdd.length() > 0) {
			this.splittedTokens.add(lTokenToAdd);
		}
	}

	private void addDelimiterToTokensIfNecessary() {
		if (this.getParameters().isKeepDelimiter()) {
			this.splittedTokens.add(this.getParameters().getDelimiter());
		}
	}

	private void addRemainingCodeIfAny() {
		if (hasCodeRemaining()) {
			this.splittedTokens.add(this.codeToSplit);
			this.codeToSplit = null;
		}
	}
}
