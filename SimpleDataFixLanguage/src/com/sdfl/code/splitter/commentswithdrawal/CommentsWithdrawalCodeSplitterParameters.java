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

import java.util.LinkedList;
import java.util.List;

import com.sdfl.code.splitter.CodeSplitterParameters;
import com.sdfl.code.splitter.DelimitersPair;

/**
 * {@link CodeSplitterParameters} implementation to be used by the {@link CommentsWithdrawalCodeSplitter}
 * @author Sylvain Cloutier
 */
public class CommentsWithdrawalCodeSplitterParameters extends CodeSplitterParameters {
	private List<DelimitersPair> commentsDelimiters;

	public CommentsWithdrawalCodeSplitterParameters() {
		this.commentsDelimiters = new LinkedList<>();
	}
	
	public List<DelimitersPair> getCommentsDelimiters() {
		return this.commentsDelimiters;
	}

	public void setCommentsDelimiters(List<DelimitersPair> pCommentsDelimiters) {
		this.commentsDelimiters = pCommentsDelimiters;
	}
	
	public void addCommentsDelimiters(DelimitersPair pCommentsDelimiters) {
		this.commentsDelimiters.add(pCommentsDelimiters);
	}
	
}
