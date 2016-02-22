package com.sdfl.code.filter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 13-2-16 Time: 14:15
 */
public class SingleDelimiterCodeFilterImplTest {
    private SingleDelimiterCodeFilterImpl testSubject;

    @Test
    public void testApplyHappyFlow() {
        // GIVEN
        Character delimiter = ';';
        String rawInput = RandomStringUtils.randomAlphanumeric(25);
        String input = rawInput + delimiter;
        testSubject = new SingleDelimiterCodeFilterImpl(delimiter);
        // WHEN
        String result = testSubject.apply(input);
        // THEN
        assertEquals(rawInput, result);
    }
}