package com.xc.joy.junit4;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author lxcecho 909231497@qq.com
 * @since 19.05.2022
 */
public class AssertMatcherTest {

    @Test
    public void testAssertMatcher() {
        int i = 10;

        assertThat(i, equalTo(10));
        assertThat(i, not(equalTo(20)));
        assertThat(i, is(10));
        assertThat(i, is(not(20)));
    }

    @Test
    public void testAssertMatcher2() {
        double price = 23.45;

        assertThat(price, either(equalTo(23.45)).or(equalTo(23.54)));
        assertThat(price, both(equalTo(23.45)).and(not(equalTo(23.54))));

        assertThat(price, anyOf(is(23.45), is(23.54), is(34.43), not(32.12)));

        assertThat(Stream.of(1, 2, 3).anyMatch(i -> i > 2), equalTo(true));
        assertThat(Stream.of(1, 2, 3).allMatch(i -> i > 2), equalTo(true));
    }

    @Test
    public void testAssertMatcher3() {
        double price = 23.45;
        assertThat("the double value assertion failed.", price, either(equalTo(23.45)).or(equalTo(23.54)));
    }

}
