package com.xc.joy.mockito;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author lxcecho 909231497@qq.com
 * @since 20.05.2022
 */
public class SimpleTest {

    @Test
    public void test() {
        assertThat(1, lessThanOrEqualTo(3));
        assertThat(1, greaterThanOrEqualTo(3));
    }

}
