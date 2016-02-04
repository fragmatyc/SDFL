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
package com.sdfl.code.splitter.commentswithdrawal;

import com.sdfl.code.splitter.CodeSplitter;
import com.sdfl.code.splitter.DelimitersPair;

/**
 * {@link CodeSplitter} implementation to remove comments from the code.
 * @author Sylvain Cloutier
 */
public class CommentsWithdrawalCodeSplitter extends CodeSplitter {

	public CommentsWithdrawalCodeSplitter() {
		this.parameters = new CommentsWithdrawalCodeSplitterParameters();
	}
	
	@Override
	public CommentsWithdrawalCodeSplitterParameters getParameters() {
		return (CommentsWithdrawalCodeSplitterParameters) this.parameters;
	}
	
	public String removeComments(String pCode) {

		this.codeToSplit = pCode;
		if (this.codeToSplit != null) {
			
			for (DelimitersPair lCurPair : this.getParameters().getCommentsDelimiters()) {
				this.removeThoseCommentsDelimiters(lCurPair);
			}
			
		}
				
		return this.codeToSplit;
	}

	private void removeThoseCommentsDelimiters(DelimitersPair lCurPair) {
		StringBuilder lCodeBuilder = new StringBuilder();
		
		do {
			this.findNextUsableDelimiter(lCurPair.getFirstDelimiter());
			
			if (delimiterWasFound()) {
				int lFirstDelimiterIdx = this.lastDelimiterFound;
				
				this.findNextDelimiter(lCurPair.getSecondDelimiter());
				if (delimiterWasFound()) {
					this.splitCodeAtDelimitersAndAppendToCommentLessCode(lCurPair,
							lCodeBuilder, lFirstDelimiterIdx);
				}
			}
			
		} while (delimiterWasFound());
	}

	private void splitCodeAtDelimitersAndAppendToCommentLessCode(
			DelimitersPair lCurPair, StringBuilder lCodeBuilder,
			int lFirstDelimiterIdx) {
		
		lCodeBuilder.delete(0, lCodeBuilder.length());
		lCodeBuilder.append(this.codeToSplit.substring(0, lFirstDelimiterIdx).trim());
		
		String lRemainingChunk = this.codeToSplit.substring(this.lastDelimiterFound + 
				lCurPair.getSecondDelimiter().length()).trim();
		
		if (!lRemainingChunk.isEmpty()) {
			lCodeBuilder.append(" ");
			lCodeBuilder.append(lRemainingChunk);
		}
		
		this.codeToSplit = lCodeBuilder.toString();
	}
	
}
