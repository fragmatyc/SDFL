package com.sdfl.statements.tokenizer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.code.SDFLSyntax;
import com.sdfl.exceptions.parsing.ExpectedEndOfStatementException;
import com.sdfl.exceptions.parsing.InvalidSyntaxException;
import com.sdfl.exceptions.parsing.MissingKeywordException;
import com.sdfl.exceptions.parsing.MissingParameterException;
import com.sdfl.exceptions.parsing.UnexpectedKeywordException;

public class StatementTokens {
	private Queue<String> tokens;
	
	public StatementTokens() {
		this.tokens = new LinkedList<>();
	}

	public void addAll(Collection<String> pTokens) {
		this.tokens.addAll(pTokens);
	}
	
	public void add(String pToken) {
		this.tokens.add(pToken);
	}

	public int count() {
		return this.tokens.size();
	}

	public String consumeNext() {
		return this.tokens.poll();
	}
	
	public boolean isNextTokenEqualTo(String pKeyword) {
		return this.hasNext() && this.tokens.peek().equals(pKeyword);
	}
	
	public String consumeExpected(String... pKeywords) {
		
		if (!this.hasNext()) {
			throw new MissingKeywordException(null, pKeywords);
		}
		
		String lCurrentToken = this.consumeNext();
		
		List<String> lKeywordsAsList = Arrays.asList(pKeywords);
		if (!lKeywordsAsList.contains(lCurrentToken)) {
			String lFirstExpectedKeyword = pKeywords[0];
			
			if (SDFLKeywords.isKeyword(lFirstExpectedKeyword)) {
				if (SDFLKeywords.isKeyword(lCurrentToken)) {
					throw new UnexpectedKeywordException(lCurrentToken, pKeywords);
				} else {
					throw new MissingKeywordException(lCurrentToken, pKeywords);
				}
			} else if (SDFLSyntax.isSyntax(lFirstExpectedKeyword)) {
				throw new InvalidSyntaxException(lCurrentToken);				
			}
		}
		
		return lCurrentToken;
		
	}
	
	public String consumeExpectedParameter() {
		if (!this.hasNext()) {
			throw new MissingParameterException();
		}
		
		return this.consumeNext();
	}
	
	public void expectEndOfStatement() {
		if (this.hasNext()) {
			throw new ExpectedEndOfStatementException(this.consumeNext());
		}
	}
	
	public boolean hasNext() {
		return !this.tokens.isEmpty();
	}
	
	@Override
	public String toString() {
		String lStrRepresentation = null;
		
		if (this.tokens != null) {
			lStrRepresentation = this.tokens.toString();
		}
		
		return lStrRepresentation;
	}
}
