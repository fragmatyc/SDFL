package com.sdfl.code.composite;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

/**
 * This interface ties the Function generics to the input type File and output type List&lt;String&gt;.
 * Created by IntelliJ IDEA. User: cpt Date: 14-2-16 Time: 14:06
 */
public interface SourceFileMapper
        extends Function<Path, List<String>> {
}
