package com.sdfl.code.splitter.simpledelimiter;

import com.sdfl.code.filter.CodeFilter;
import com.sdfl.code.filter.SingleDelimiterAppendingCodeFilter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class has a single responsibility: to split out a given String into an List of Strings based on the rules
 *  set in a passed SimpleDelimiterCodeSplitterParameters.
 *
 * Created by IntelliJ IDEA. User: cpt Date: 13-2-16 Time: 14:07
 */
public class DefaultLinesSeparator implements LinesSeparator {
    public final SimpleDelimiterCodeSplitterParameters separators;

    public DefaultLinesSeparator(final SimpleDelimiterCodeSplitterParameters separators) {
        this.separators = separators;
    }

    /**
     * To apply the rules set in the SimpleDelimiterCodeSplitterParameters of this class on the input.
     *
     * @param input The String to process
     * @return A List of String objects split out as specified in the rules set.
     */
    @Override
    public List<String> apply(final String input) {
        String delimiter = separators.getDelimiter();
        Stream<String> stream = Stream.of(input.split(delimiter));
        CodeFilter mapper;
        if (separators.isKeepDelimiter()) {
            mapper = new SingleDelimiterAppendingCodeFilter(delimiter);
        } else {
            mapper = s -> s;
        }
        return stream.map(mapper::apply).collect(Collectors.toList());
    }
}
