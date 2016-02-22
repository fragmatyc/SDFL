package com.sdfl.statements.tokenizer;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sdfl.code.SDFLKeywords;

public class StatementTokensTest {
	private static final String PACKAGE_NAME = "2014JUN21PM";
	private StatementTokens tokens;
	
	@Before
	public void setup() {
		tokens = new StatementTokens();
		tokens.add(SDFLKeywords.IN);
		tokens.add(SDFLKeywords.PACKAGE);
		tokens.add(PACKAGE_NAME);
	}
	
	@Test
	public void testThatStatementTokensIsAFIFO() {
		boolean lAllAsExpected = true;
		
		lAllAsExpected = lAllAsExpected && tokens.consumeNext().equals(SDFLKeywords.IN);
		lAllAsExpected = lAllAsExpected && tokens.consumeNext().equals(SDFLKeywords.PACKAGE);
		lAllAsExpected = lAllAsExpected && tokens.consumeNext().equals(PACKAGE_NAME);
		
		assertTrue(lAllAsExpected);
	}

}
