package com.sdfl.issuetests;

import com.sdfl.code.composite.DefaultSourceFileMapper;
import com.sdfl.code.filter.CodeFilter;
import com.sdfl.code.filter.DelimiterPairCodeFilterImpl;
import com.sdfl.code.filter.SingleDelimiterCodeFilterImpl;
import com.sdfl.code.splitter.DelimitersPair;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * Tests issue 4 by using the file issue4input.txt.
 *
 * Created by IntelliJ IDEA. User: cpt Date: 14-2-16 Time: 14:00
 */
public class Issue4Test {
    private DefaultSourceFileMapper testSubject;

    @Test
    public void issueTest() {
        // GIVEN
        DelimitersPair delimitersPair = new DelimitersPair("/*", "*/");
        CodeFilter commentFilter = new DelimiterPairCodeFilterImpl(delimitersPair);
        CodeFilter endOfLineFilter = new SingleDelimiterCodeFilterImpl(';');
        CodeFilter newLineFilter = new SingleDelimiterCodeFilterImpl(new String("\n").charAt(0));
        testSubject = new DefaultSourceFileMapper(commentFilter, endOfLineFilter, newLineFilter);
        String file = Thread.currentThread().getContextClassLoader().getResource("issue4input.txt").getFile();
        Path filePath = new File(file).toPath();
        // WHEN
        List<String> actual = testSubject.apply(filePath);
        // THEN
        assertEquals(2, actual.size());
        assertEquals("Line of code 1", actual.get(0));
        assertEquals("Line of code 2", actual.get(1));
    }
}
