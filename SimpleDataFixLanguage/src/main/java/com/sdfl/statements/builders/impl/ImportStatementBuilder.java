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
package com.sdfl.statements.builders.impl;

import com.sdfl.code.SDFLKeywords;
import com.sdfl.statements.Statement;
import com.sdfl.statements.builders.StatementBuilder;
import com.sdfl.statements.impl.ImportStatement;
import com.sdfl.statements.template.UsingTemplateStatement;
import com.sdfl.statements.tokenizer.StatementTokens;

/**
 * Builder pattern implementation of the {@link ImportStatement}
 * @author Sylvain Cloutier
 */
public class ImportStatementBuilder extends StatementBuilder {
	private ImportStatement statement;
	
	public ImportStatementBuilder(StatementTokens pTokens) {
		super(pTokens);
	}

	@Override
	public Statement build() {
		this.statement = new ImportStatement();
		
		this.setInputFile();
		this.setIsWithHeader();
		this.setTableName();
		this.setTemplateIfNecessary();
		
		return this.statement;
	}

	private void setInputFile() {
		String lInputFile = this.tokens.consumeExpectedParameter();
		this.statement.setInputFile(lInputFile);
	}
	
	private void setIsWithHeader() {
		String lKeywordFound = this.tokens.consumeExpected(SDFLKeywords.INTO, SDFLKeywords.WITH);
		if (lKeywordFound.equals(SDFLKeywords.WITH)) {
			this.tokens.consumeExpected(SDFLKeywords.HEADERS);
			this.tokens.consumeExpected(SDFLKeywords.INTO);
			
			this.statement.setWithHeaders(true);
		}
	}
	
	private void setTableName() {
		String lTableName = this.tokens.consumeExpectedParameter();
		this.statement.setTableName(lTableName);
	}

	private void setTemplateIfNecessary() {
		if (this.tokens.hasNext()) {
			UsingTemplateStatementBuilder lTemplateBuilder = new UsingTemplateStatementBuilder(this.tokens);
			UsingTemplateStatement lTemplate = (UsingTemplateStatement) lTemplateBuilder.build();
			this.statement.setTemplate(lTemplate);
			
			this.tokens.expectEndOfStatement();
		}
	}

}
