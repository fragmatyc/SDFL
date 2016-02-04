package com.sdfl.code.splitter.simpledelimiter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SimpleDelimiterCodeSplitterTest {
	
	private static final String TEST2 = "test2";
	private static final String TEST1 = "test1";
	private static final int NB_OF_TOKENS_KEEPING_DELIMITER = 17;
	private static final String LAST_TOKEN = "yz";
	private static final String FIRST_TOKEN = "abc";
	private static final int FIRST_IDX = 0;
	private static final int NB_OF_TOKENS_WITHOUT_DELIMITER = 9;
	private static final String STRING_TO_SPLIT = "abc.def.ghi.jkl.mno.pqr.stu.vwx.yz";
	private static final String STRING_WITH_2_CHARS_DELIMITER = "abc->def->ghi->jkl->mno->pqr->stu->vwx->yz";
	private static final String DELIMITER = ".";
	private static final String DELIMITER_2_CHARS = "->";
	private static final String STRING_TO_SPLIT_WITH_ARROW = "test1->test2";
	private static final String STRING_WITH_DELIMITER_AT_START = "->test1";
	private static final String STRING_WITH_DELIMITER_AT_END = "test1->";
	private static final String STRING_TO_SPLIT_WITH_DEAD_ZONE = "abc.def.ghi.j\"kl.mno.qrs.st\"u.vwx.yz";
	private static final String STRING_TO_SPLIT_WITH_DIFFERENT_DEAD_ZONE = "abc.def.ghi.j//kl.mno.qrs.st" + System.lineSeparator() + "u.vwx.yz";;
	private SimpleDelimiterCodeSplitter splitter;
	
	@Before
	public void setup() {
		this.splitter = new SimpleDelimiterCodeSplitter();
	}
	
	@Test
	public void testThatSplitOccursCorrectlyIfDelimiterIsAtTheEnd() {
		List<String> lSplitedStr = splitKeepingDelimiter(DELIMITER_2_CHARS, STRING_WITH_DELIMITER_AT_END);

		assertEquals(TEST1, lSplitedStr.get(0));
		assertEquals(DELIMITER_2_CHARS, lSplitedStr.get(1));
	}
	
	@Test
	public void testThatSplitOccursCorrectlyIfDelimiterIsAtTheStart() {
		List<String> lSplitedStr = splitKeepingDelimiter(DELIMITER_2_CHARS, STRING_WITH_DELIMITER_AT_START);

		assertEquals(DELIMITER_2_CHARS, lSplitedStr.get(0));
		assertEquals(TEST1, lSplitedStr.get(1));
	}
	
	@Test
	public void testThat2CharsDelimiterSplitsCorrectly() {
		List<String> lSplitedStr = splitKeepingDelimiter(DELIMITER_2_CHARS, STRING_TO_SPLIT_WITH_ARROW);
		assertEquals(TEST1, lSplitedStr.get(0));
		assertEquals(DELIMITER_2_CHARS, lSplitedStr.get(1));
		assertEquals(TEST2, lSplitedStr.get(2));
	}
	
	@Test
	public void testThatSplitWithoutKeepingTheDelimiterReturnsAListOfTheProperSize() {
		List<String> lSplitedStr = splitWithoutKeepingDelimiter(DELIMITER, STRING_TO_SPLIT);
		assertEquals(NB_OF_TOKENS_WITHOUT_DELIMITER, lSplitedStr.size());
	}
	
	@Test
	public void testThatSplitSeparatesTheStringAtTheDelimiter() {
		List<String> lSplitedStr = splitWithoutKeepingDelimiter(DELIMITER, STRING_TO_SPLIT);

		int lLastIdx = lSplitedStr.size() - 1;
		
		assertEquals(FIRST_TOKEN, lSplitedStr.get(FIRST_IDX));
		assertEquals(LAST_TOKEN, lSplitedStr.get(lLastIdx));
	}

	@Test
	public void testThatSplitKeepsTheDelimiterWhenNeeded() {
		List<String> lSplitedStr = splitKeepingDelimiter(DELIMITER, STRING_TO_SPLIT);
		assertEquals(NB_OF_TOKENS_KEEPING_DELIMITER, lSplitedStr.size());
	}

	@Test
	public void testThatDelimiterCanBeMoreThatOneChar() {
		List<String> lSplitedStr = splitWithoutKeepingDelimiter(DELIMITER_2_CHARS, STRING_WITH_2_CHARS_DELIMITER);
		assertEquals(NB_OF_TOKENS_WITHOUT_DELIMITER, lSplitedStr.size());
	}
	
	@Test
	public void testThatSplitCanBeAvoidedBetweenCertain2Characters() {
		this.splitter.getParameters().setDelimiter(DELIMITER);
		this.splitter.getParameters().setKeepDelimiter(false);
		this.splitter.getParameters().addDontSplitBetweenTwo("\"");
		
		List<String> lSplitedStr = this.splitter.split(STRING_TO_SPLIT_WITH_DEAD_ZONE);
		assertEquals(6, lSplitedStr.size());
	}
	
	@Test
	public void testThatSplitCanBeAvoidedBetweenCertainLimitsWithMoreThanOneChar() {
		this.splitter.getParameters().setDelimiter(DELIMITER);
		this.splitter.getParameters().setKeepDelimiter(false);
		this.splitter.getParameters().addDontSplitBetween("//", System.lineSeparator());
		
		List<String> lSplitedStr = this.splitter.split(STRING_TO_SPLIT_WITH_DIFFERENT_DEAD_ZONE);
		assertEquals(6, lSplitedStr.size());
	}
	
	private List<String> splitWithoutKeepingDelimiter(String pDelimiter, String pString) {
		this.splitter.getParameters().setDelimiter(pDelimiter);
		this.splitter.getParameters().setKeepDelimiter(false);
		
		List<String> lSplitedStr = this.splitter.split(pString);
		return lSplitedStr;
	}

	private List<String> splitKeepingDelimiter(String pDelimiter, String pString) {
		this.splitter.getParameters().setDelimiter(pDelimiter);
		this.splitter.getParameters().setKeepDelimiter(true);
		
		List<String> lSplitedStr = this.splitter.split(pString);
		return lSplitedStr;
	}
}
