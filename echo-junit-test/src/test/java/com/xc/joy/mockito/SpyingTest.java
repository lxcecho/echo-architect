package com.xc.joy.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author lxcecho 909231497@qq.com
 * @since 18.05.2022
 */
@RunWith(MockitoJUnitRunner.class)
public class SpyingTest {

    @Test
    public void testSpy() {
        List<String> realList = new ArrayList<>();
        List<String> list = spy(realList);

        list.add("mockito");
        list.add("spy");

        assertThat(list.get(0), equalTo("mockito"));
        assertThat(list.get(1), equalTo("spy"));
        assertThat(list.isEmpty(), equalTo(false));

        when(list.isEmpty()).thenReturn(true);
        when(list.size()).thenReturn(0);

        assertThat(list.get(0), equalTo("mockito"));
        assertThat(list.get(1), equalTo("spy"));
        assertThat(list.isEmpty(), equalTo(true));
        assertThat(list.size(), equalTo(0));

    }

}
