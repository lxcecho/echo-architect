package com.xc.joy.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

/**
 * @author lxcecho 909231497@qq.com
 * @since 18.05.2022
 */
public class SpyByAnnotaionTest {

    @Spy
    private List<String> list = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSpy() {
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