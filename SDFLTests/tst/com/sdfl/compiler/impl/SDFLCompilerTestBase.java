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
package com.sdfl.compiler.impl;

import static org.mockito.Mockito.spy;

import java.io.File;
import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.sdfl.compiler.SDFLCompiler;
import com.sdfl.interpreter.SDFLInterpreter;

/**
 * Base test class for compilers tests suites
 * @author Sylvain Cloutier
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class SDFLCompilerTestBase<E extends SDFLCompiler> {
	protected static final File DEFAULT_COMPILE_DESTINATION = new File("DestinationFolder");
	
	protected SDFLInterpreter interpreter;
	protected E compiler;
	
	@Spy protected CompilerFileAndFolderUtilForTest fileUtil;
	
	@Before
	@SuppressWarnings("unchecked")
	public void setup() throws InstantiationException, IllegalAccessException {
		ParameterizedType lGenericTypeOfCompiler = (ParameterizedType) getClass().getGenericSuperclass();
		Class<E> lCompilerClass = (Class<E>) lGenericTypeOfCompiler.getActualTypeArguments()[0];
		
		this.interpreter = new SDFLInterpreter();
		this.compiler = spy(lCompilerClass.newInstance());
		this.compiler.getParameters().setDestinationFolder(DEFAULT_COMPILE_DESTINATION);
		this.compiler.getUtilities().setFileHelper(this.fileUtil);
	}
	
	protected void compile(String pCode) {
		this.compiler.compile(pCode);
	}
}
