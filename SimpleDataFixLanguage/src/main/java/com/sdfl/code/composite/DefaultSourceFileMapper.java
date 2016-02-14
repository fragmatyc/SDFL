package com.sdfl.code.composite;

import com.sdfl.code.filter.CodeFilter;
import com.sdfl.code.splitter.simpledelimiter.LinesSeparator;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class takes in a File and splits it out into a List&lt;String&gt; using the parameters set. Created by IntelliJ
 * IDEA. User: cpt Date: 14-2-16 Time: 14:11
 */
public class DefaultSourceFileMapper
        implements SourceFileMapper {
    private final CodeFilter commentFilter;
    private final CodeFilter endOfLineFilter;
    private final CodeFilter newLineFilter;

    public DefaultSourceFileMapper(final CodeFilter commentFilter, final CodeFilter endOfLineFilter,
                                   final CodeFilter newLineFilter) {
        this.commentFilter = commentFilter;
        this.endOfLineFilter = endOfLineFilter;
        this.newLineFilter = newLineFilter;
    }

    @Override
    public List<String> apply(final Path file) {
        try {
            Stream<String> input = Files.lines(file);
            return input.map(newLineFilter::apply)
                           .map(commentFilter::apply)
                           .map(endOfLineFilter::apply)
                           .filter(s -> !s.isEmpty())
                           .collect(Collectors.toList());

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems reading file.", e);
        }
    }

}
