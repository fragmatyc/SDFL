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
package com.sdfl.code.splitter;

import com.sdfl.code.splitter.commentswithdrawal.CommentsWithdrawalCodeSplitter;

/**
 * Base class of the code utility classes such as {@link CommentsWithdrawalCodeSplitter}
 * @author Sylvain Cloutier
 */
public abstract class CodeSplitter {
	protected int lastDelimiterFound = -1;
	protected CodeSplitterParameters parameters;
	protected String codeToSplit;
	
	public abstract CodeSplitterParameters getParameters();
	
	protected boolean shouldSkipThisDelimiter() {
		boolean lIsDelimiterBetweenLimits = false;
		
		for(DelimitersPair lCurPair : this.getParameters().getSplitLimits()) {
			
			String lStartLimit = lCurPair.getFirstDelimiter();
			String lEndLimit = lCurPair.getSecondDelimiter();
			
			int lPosOfFirstLimitStart = this.codeToSplit.indexOf(lStartLimit);
			int lPosOfLimitEnd = this.codeToSplit.indexOf(lEndLimit, lPosOfFirstLimitStart + lStartLimit.length());
			
			if (lPosOfFirstLimitStart > -1 && lPosOfLimitEnd > -1) {
				lIsDelimiterBetweenLimits = isDelimiterBetweenLimits(
						this.lastDelimiterFound,
						lPosOfFirstLimitStart + lStartLimit.length(), 
						lPosOfLimitEnd);
				
				if (lIsDelimiterBetweenLimits) {
					break;
				}
			}
		}
		
		return lIsDelimiterBetweenLimits;
	}
	

	protected void findNextUsableDelimiter(String pDelimiter) {

		this.lastDelimiterFound = this.codeToSplit.indexOf(pDelimiter);
		while (delimiterWasFound() && shouldSkipThisDelimiter()) {
			this.findNextDelimiter(pDelimiter);
		}
	}
	
	private boolean isDelimiterBetweenLimits(
			int lastDelimiterFoundIdx,
			int pLimitStartIdx,
			int pLimitEndIdx) {
		return lastDelimiterFoundIdx >= pLimitStartIdx
				&& lastDelimiterFoundIdx < pLimitEndIdx;
	}
	

	
	protected boolean hasCodeRemaining() {
		return this.codeToSplit.length() > 0;
	}

	protected boolean delimiterWasFound() {
		return this.lastDelimiterFound > -1;
	}

	protected void findNextDelimiter(String pDelimiter) {
		this.lastDelimiterFound = this.codeToSplit.indexOf(pDelimiter,
				this.lastDelimiterFound + pDelimiter.length() + 1);
	}
}
