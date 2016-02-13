package com.sdfl.code.filter;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 13-2-16 Time: 14:11
 */
public class SingleDelimiterAppendingCodeFilter
        implements CodeFilter {
    private final String delimiter;

    public SingleDelimiterAppendingCodeFilter(final String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    /**
     * This method appends the delimiter at the end of the passed String
     * @param input The String to be appended
     * @result The String with the delimiter appended
     */
    public String apply(final String input) {
        return input.trim() + delimiter;
    }
}
