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
package com.sdfl.statements.impl;

import com.sdfl.statements.Statement;
import com.sdfl.statements.conditions.ConditionGroup;
import com.sdfl.statements.template.UsingTemplateStatement;

/**
 * {@link Statement} implementation of the SDFL "update" statement.
 * @author Sylvain Cloutier
 */
public class UpdateStatement extends Statement {

	private UsingTemplateStatement template;
	private ConditionGroup conditionGroup;
	private String tableName;

	public UpdateStatement() {
		this.template = new UsingTemplateStatement();
		this.conditionGroup = new ConditionGroup();
	}
	
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String pTableName) {
		this.tableName = pTableName;
	}

	public UsingTemplateStatement getTemplate() {
		return this.template;
	}

	public ConditionGroup getConditionGroup() {
		return this.conditionGroup;
	}

	public void setTemplate(UsingTemplateStatement pTemplate) {
		this.template = pTemplate;
	}

	public void setConditionGroup(ConditionGroup pConditionGroup) {
		this.conditionGroup = pConditionGroup;
	}

}
