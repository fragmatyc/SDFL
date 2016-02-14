package com.sdfl.code.filter;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:07
 */
public class SingleDelimiterCodeFilterImpl
        implements CodeFilter {
    private final String delimiter;

    public SingleDelimiterCodeFilterImpl(final char delimiter) {
        this.delimiter = String.valueOf(delimiter);
    }

    @Override
    public String apply(final String input) {
        return input.replaceAll(delimiter, "").trim();
    }
}
