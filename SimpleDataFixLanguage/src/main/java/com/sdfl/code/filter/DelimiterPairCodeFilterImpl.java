package com.sdfl.code.filter;

import com.sdfl.code.splitter.DelimitersPair;

import java.util.function.Consumer;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:14
 */
public class DelimiterPairCodeFilterImpl implements CodeFilter<String> {
private final DelimitersPair delimitersPair;

public DelimiterPairCodeFilterImpl(final DelimitersPair delimitersPair) {
    this.delimitersPair = delimitersPair;
}

@Override
public String filter(final String input) {
    String result = input;
    String leftDelimiter =  delimitersPair.getFirstDelimiter();
    int left = input.indexOf(leftDelimiter);
    if (left >= 0) {
        String rightDelimiter = delimitersPair.getSecondDelimiter();
        int right = input.indexOf(rightDelimiter, left + leftDelimiter.length());
        if (right >= 0) {
            result = input.substring(0, left).concat(input.substring(right + rightDelimiter.length(),
                                                                     input.length()));
            result = filter(result);
        }
    }
    return result;
}

}
