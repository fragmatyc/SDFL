package com.sdfl.code.composite;

import com.sdfl.code.filter.CodeFilter;
import com.sdfl.code.filter.DelimiterPairCodeFilterImpl;
import com.sdfl.code.filter.SingleDelimiterCodeFilterImpl;
import com.sdfl.code.splitter.DelimitersPair;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 14-2-16 Time: 14:39
 */
public class DefaultSourceFileMapperTest {
    private DefaultSourceFileMapper testSubject;
    private String inputText = "/* Comment */\nLine of code 1;\n" +
                                       "Line of code 2;";
    @Test
    public void apply()
            throws IOException {
        // GIVEN
        DelimitersPair delimitersPair = new DelimitersPair("/*", "*/");
        CodeFilter commentFilter = new DelimiterPairCodeFilterImpl(delimitersPair);
        CodeFilter endOfLineFilter = new SingleDelimiterCodeFilterImpl(';');
        CodeFilter newLineFilter = new SingleDelimiterCodeFilterImpl(new String("\n").charAt(0));
        testSubject = new DefaultSourceFileMapper(commentFilter, endOfLineFilter, newLineFilter);
        File tempFile = File.createTempFile("DefaultSourceFileMapperTest", "txt");
        tempFile.deleteOnExit();
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write(inputText);
        writer.close();
        Path filePath = new File(tempFile.toURI()).toPath();
        // WHEN
        List<String> actual = testSubject.apply(filePath);
        // THEN
        assertEquals(2, actual.size());
        assertEquals("Line of code 1", actual.get(0));
        assertEquals("Line of code 2", actual.get(1));
    }
}