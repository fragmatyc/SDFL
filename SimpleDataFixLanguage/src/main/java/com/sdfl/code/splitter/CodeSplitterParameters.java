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

import java.util.ArrayList;
import java.util.List;

/**
 * Base class of the parameters to be used by the {@link CodeSplitter}
 * @author Sylvain Cloutier
 */
public abstract class CodeSplitterParameters {
	protected List<DelimitersPair> splitLimits;
	
	public CodeSplitterParameters() {
		this.splitLimits = new ArrayList<>();
	}

	public List<DelimitersPair> getSplitLimits() {
		return this.splitLimits;
	}

	public void setSplitLimits(List<DelimitersPair> pSplitLimits) {
		this.splitLimits = pSplitLimits;
	}
	
	public void addDontSplitBetweenTwo(String pLimit) {
		this.addDontSplitBetween(pLimit, pLimit);
	}
	
	public void addDontSplitBetween(String pStart, String pEnd) {
		this.splitLimits.add(new DelimitersPair(pStart, pEnd));
	}
}
