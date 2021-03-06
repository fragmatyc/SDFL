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
package com.sdfl.compiler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sdfl.compiler.impl.oracle.OracleSDFLCompiler;
import com.sdfl.exceptions.compiling.CompilingException;

/**
 * @author Sylvain Cloutier
 */
public class CommandLineInterface {
	public static void main(String... pArgs) throws IOException {
		
		SDFLCompiler lCompiler = new OracleSDFLCompiler();
		String lCode = FileUtils.readFileToString(new File(pArgs[0]));
		
		lCompiler.getParameters().setDestinationFolder(new File("E:\\CompiledSDFL"));
		lCompiler.compile(lCode);
		
		for (CompilingException lCurEx : lCompiler.getCompilationExceptions()) {
			System.out.println(lCurEx);
		}
	}
}

