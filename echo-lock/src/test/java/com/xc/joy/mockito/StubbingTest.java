package com.xc.joy.mockito;

import com.xc.joy.service.StubbingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class StubbingTest {

    @Mock
    List<String> list;

    @Before
    public void init() {
        this.list = mock(ArrayList.class);
    }

    @Test
    public void testHowToUseStubbing() {
        when(list.get(0)).thenReturn("first");
        assertThat(list.get(0), equalTo("first"));

        when(list.get(anyInt())).thenThrow(new RuntimeException());

        try {
            list.get(0);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }

    @Test
    public void testHowToUseStubbingVoidMethod() {
        doNothing().when(list).clear();
        list.clear();
        verify(list, times(1)).clear();

        doThrow(RuntimeException.class).when(list).clear();

        try {
            list.clear();
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }

    @Test
    public void testStubbingDoReturn() {
        when(list.get(0)).thenReturn("first");
        doReturn("second").when(list).get(1);
        assertThat(list.get(0), equalTo("first"));
        assertThat(list.get(1), equalTo("second"));
    }

    @Test
    public void testStubbingIterator() {
        when(list.size()).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4);

        assertThat(list.size(), equalTo(1));
        assertThat(list.size(), equalTo(2));
        assertThat(list.size(), equalTo(3));
        assertThat(list.size(), equalTo(4));
    }

    @Test
    public void testStubbingWithAnswer() {
        when(list.get(anyInt())).thenAnswer(answer->{
            Integer index = answer.getArgument(0, Integer.class);
            return String.valueOf(index * 10);
        });
        assertThat(list.get(0), equalTo("0"));
        assertThat(list.get(999), equalTo(9990));
    }

    @Test
    public void testStubbingWithRealCall() {
        StubbingService stubbingService = mock(StubbingService.class);
        when(stubbingService.getS()).thenReturn("lxcecho");
        assertThat(stubbingService.getS(), equalTo("lxcecho"));

        // 调用真实的方法
        when(stubbingService.getI()).thenCallRealMethod();
        assertThat(stubbingService.getI(), equalTo(10));
    }

    @After
    public void destroy() {
        reset(list);
    }

}
