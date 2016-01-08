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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sdfl.code.splitter.DelimitersPair;

/**
 * @author Sylvain Cloutier
 */
public class CommentsWithdrawalCodeSplitterTest {
	private static final String ONE_CHAR_DELIMITER = "this is a regular string 'this is comments' this is the end";
	private static final String EXCEPTED_RESULT_OF_ONE_CHAR_DELIMITER = "this is a regular string this is the end";
	private static final String TWO_CHAR_DELIMITER = "this is a regular string /*this is comments*/ this is the end";
	private static final String EXCEPTED_RESULT_OF_TWO_CHAR_DELIMITER = "this is a regular string this is the end";
	private static final String CHAR_DELIMITER_IN_STRING = "this is a regular string this is \"comments/* this is\" the /* end */";
	private static final String EXCEPTED_RESULT_OF_CHAR_DELIMITER_IN_STRING = "this is a regular string this is \"comments/* this is\" the";
	
	private CommentsWithdrawalCodeSplitter splitter;
	
	@Before
	public void setup() {
		this.splitter = new CommentsWithdrawalCodeSplitter();
	}
	
	@Test
	public void testThatSingleCharacterDelimitersAreSplittedCorrectly() {
		this.splitter.getParameters().addCommentsDelimiters(new DelimitersPair("'", "'"));
		String lActual = this.splitter.removeComments(ONE_CHAR_DELIMITER);
		assertEquals(EXCEPTED_RESULT_OF_ONE_CHAR_DELIMITER, lActual);
	}
	
	@Test
	public void testThatMultiCharactersDelimitersAreSplittedCorrectly() {
		this.splitter.getParameters().addCommentsDelimiters(new DelimitersPair("/*", "*/"));
		String lActual = this.splitter.removeComments(TWO_CHAR_DELIMITER);
		assertEquals(EXCEPTED_RESULT_OF_TWO_CHAR_DELIMITER, lActual);
	}
	
	@Test
	public void testThatRemovalCanBeBlockedBetweenCertainChars() {
		this.splitter.getParameters().addDontSplitBetweenTwo("\"");
		this.splitter.getParameters().addCommentsDelimiters(new DelimitersPair("/*", "*/"));
		String lActual = this.splitter.removeComments(CHAR_DELIMITER_IN_STRING);
		assertEquals(EXCEPTED_RESULT_OF_CHAR_DELIMITER_IN_STRING, lActual);
	}
}
