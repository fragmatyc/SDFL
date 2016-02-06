package com.sdfl.code.filter;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:07
 */
public class SingleDelimiterCodeFilterImpl
        implements CodeFilter<String> {
private final String delimiter;

public SingleDelimiterCodeFilterImpl(final char delimiter) {
    this.delimiter = String.valueOf(delimiter);
}

@Override
public String filter(final String input) {
    return input.replace(delimiter, delimiter);
}
}
