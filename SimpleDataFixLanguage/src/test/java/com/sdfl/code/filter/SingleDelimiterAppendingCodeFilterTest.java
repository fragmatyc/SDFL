package com.sdfl.code.filter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 13-2-16 Time: 14:15
 */
public class SingleDelimiterAppendingCodeFilterTest {
    private SingleDelimiterAppendingCodeFilter testSubject;

    @Test
    public void testApplyHappyFlowAppendingNewLine() {
        // GIVEN
        String input = RandomStringUtils.random(10);
        String new_line = "\n";
        testSubject = new SingleDelimiterAppendingCodeFilter(new_line);
        // WHEN
        String result = testSubject.apply(input);
        // THEN
        assertEquals(input + new_line, result);
    }

    @Test
    public void testApplyHappyFlowAppendingNewLineIsTrimmingInput() {
        // GIVEN
        String rawInput = RandomStringUtils.random(10);
        String input = " " + rawInput + " ";
        String new_line = "\n";
        testSubject = new SingleDelimiterAppendingCodeFilter(new_line);
        // WHEN
        String result = testSubject.apply(input);
        // THEN
        assertEquals(rawInput + new_line, result);
    }
}