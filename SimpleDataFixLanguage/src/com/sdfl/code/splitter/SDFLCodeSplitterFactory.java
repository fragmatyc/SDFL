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

import com.sdfl.code.SDFLDelimiters;
import com.sdfl.code.splitter.commentswithdrawal.CommentsWithdrawalCodeSplitter;
import com.sdfl.code.splitter.simpledelimiter.SimpleDelimiterCodeSplitter;

/**
 * Implementation of the factory and builder pattern for SDFL specific code.
 * @author Sylvain Cloutier
 */
public class SDFLCodeSplitterFactory {
	private SDFLCodeSplitterFactory() {}

	@SuppressWarnings("unchecked")
	public static <T extends CodeSplitter> T makeCodeSplitterInstance(Class<T> pType) {
		CodeSplitter lSplitter = null;
		
		if (pType.equals(SimpleDelimiterCodeSplitter.class)) {
			lSplitter = SDFLCodeSplitterFactory.makeSimpleDelimiterCodeSplitterInstance();
		} else if (pType.equals(CommentsWithdrawalCodeSplitter.class)) {
			lSplitter = SDFLCodeSplitterFactory.makeCommentsWithdrawalCodeSplitterInstance();
		}
		
		return (T) lSplitter;
	}
	
	private static CodeSplitter makeSimpleDelimiterCodeSplitterInstance() {
		SimpleDelimiterCodeSplitter lSplitter = new SimpleDelimiterCodeSplitter();	
		
		lSplitter.getParameters().addDontSplitBetweenTwo(SDFLDelimiters.STRING_DELIMITER);
		lSplitter.getParameters().addDontSplitBetween(SDFLDelimiters.MULTI_LINE_COMMENT_START, 
				SDFLDelimiters.MULTI_LINE_COMMENT_END);
		lSplitter.getParameters().addDontSplitBetween(SDFLDelimiters.SINGLE_LINE_COMMENT, 
				System.lineSeparator());
		
		return lSplitter;
	}
	
	private static CodeSplitter makeCommentsWithdrawalCodeSplitterInstance() {
		CommentsWithdrawalCodeSplitter lSplitter = new CommentsWithdrawalCodeSplitter();	
		lSplitter.getParameters().addDontSplitBetweenTwo(SDFLDelimiters.STRING_DELIMITER);
		
		lSplitter.getParameters().addCommentsDelimiters(new DelimitersPair(
				SDFLDelimiters.SINGLE_LINE_COMMENT, 
				System.lineSeparator()));
		lSplitter.getParameters().addCommentsDelimiters(new DelimitersPair(
				SDFLDelimiters.MULTI_LINE_COMMENT_START, 
				SDFLDelimiters.MULTI_LINE_COMMENT_END));
		
		return lSplitter;
	}
	
}
