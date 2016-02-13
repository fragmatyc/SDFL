package com.sdfl.code.filter;

import com.sdfl.code.splitter.DelimitersPair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:32
 */
public class DelimiterPairCodeFilterImplTest {

    private DelimiterPairCodeFilterImpl testSubject;
    private DelimitersPair              delimitersPair;

    @Before
    public void setUp() {
        delimitersPair = mock(DelimitersPair.class);
        testSubject = new DelimiterPairCodeFilterImpl(delimitersPair);
    }

    @After
    public void tearDown() {
        delimitersPair = null;
        testSubject = null;
    }

    @Test
    public void filterPairInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("/*");
        given(delimitersPair.getSecondDelimiter()).willReturn("*/");
        String input = "This is /*must be left out */the correct outcome";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome", actual);

    }

    @Test
    public void filterMultiplePairsInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("/*");
        given(delimitersPair.getSecondDelimiter()).willReturn("*/");
        String input = "This is /*must be left out */the correct outcome, even with /*must be left out */multiple pairs";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome, even with multiple pairs", actual);

    }

    @Test
    public void filterMultipleUnmatchedPairsInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("/*");
        given(delimitersPair.getSecondDelimiter()).willReturn("*/");
        String input = "This is /*must be left out */the correct outcome, even with must be left in */multiple pairs";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome, even with must be left in */multiple pairs", actual);

    }

    @Test
    public void filterSinglePairInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("|");
        given(delimitersPair.getSecondDelimiter()).willReturn("|");
        String input = "This is |must be left out |the correct outcome";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome", actual);

    }

    @Test
    public void filterMultipleSinglePairsInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("|");
        given(delimitersPair.getSecondDelimiter()).willReturn("|");
        String input = "This is |must be left out |the correct outcome, even with |must be left out |multiple pairs";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome, even with multiple pairs", actual);

    }

    @Test
    public void filterUnmatchedMultipleSinglePairsInString() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("|");
        given(delimitersPair.getSecondDelimiter()).willReturn("|");
        String input = "This is |must be left out |the correct outcome, even with must be left in |multiple pairs";
        // WHEN
        String actual = testSubject.apply(input);
        // THEN
        assertEquals("This is the correct outcome, even with must be left in |multiple pairs", actual);

    }

    @Test
    public void filterUnmatchedMultipleSinglePairsInStringAsFunction() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("|");
        given(delimitersPair.getSecondDelimiter()).willReturn("|");
        String input = "This is |must be left out |the correct outcome, even with must be left in |multiple pairs";
        // WHEN
        Function<String, String> f = (s) -> testSubject.apply(s);
        String actual = f.apply(input);
        // THEN
        assertEquals("This is the correct outcome, even with must be left in |multiple pairs", actual);
    }

    @Test
    public void filterUnmatchedMultipleSinglePairsInStringsThroughStream() {
        // GIVEN
        given(delimitersPair.getFirstDelimiter()).willReturn("|");
        given(delimitersPair.getSecondDelimiter()).willReturn("|");
        String input = "This is |must be left out |the correct outcome, even with must be left in |multiple pairs";
        // WHEN
        Stream<String> stream = Stream.of(input);
        List<String> actual = stream.map(s -> testSubject.apply(s)).collect(Collectors.toList());
        // THEN
        assertEquals("This is the correct outcome, even with must be left in |multiple pairs", actual.get(0));
    }
}