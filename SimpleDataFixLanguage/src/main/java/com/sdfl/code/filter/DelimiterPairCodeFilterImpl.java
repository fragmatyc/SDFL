package com.sdfl.code.filter;

import com.sdfl.code.splitter.DelimitersPair;

/**
 * Implementing CodeFilter this class removes text between pairs of delimiters - including the delimiters, returning
 *  the remaining text.
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:14
 */
public class DelimiterPairCodeFilterImpl
        implements CodeFilter {
    private final DelimitersPair delimitersPair;

    public DelimiterPairCodeFilterImpl(final DelimitersPair delimitersPair) {
        this.delimitersPair = delimitersPair;
    }

    /**
     * Removes text between a pair of delimiters - including the delimiters. This method will recurse until no delimiter
     *  pairs remain.
     *
     *  @param input The String containing the text to be cleaned up
     *  @return The cleaned up text
     */
    @Override
    public String apply(final String input) {
        String result = input;
        String leftDelimiter = delimitersPair.getFirstDelimiter();
        int left = input.indexOf(leftDelimiter);
        if (left >= 0) {
            String rightDelimiter = delimitersPair.getSecondDelimiter();
            int right = input.indexOf(rightDelimiter, left + leftDelimiter.length());
            if (right >= 0) {
                result = input.substring(0, left).concat(input.substring(right + rightDelimiter.length(),
                                                                         input.length()));
                result = apply(result);
            }
        }
        return result;
    }
}
