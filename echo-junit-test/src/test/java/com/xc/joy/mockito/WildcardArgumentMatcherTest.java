package com.xc.joy.mockito;

import com.xc.joy.mockito.basic.SimpleService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author lxcecho 909231497@qq.com
 * @since 18.05.2022
 */
@RunWith(MockitoJUnitRunner.class)
public class WildcardArgumentMatcherTest {

    @Mock
    private SimpleService simpleService;

    @Test
    public void testWildcardArgumentMatcher() {
        when(simpleService.method1(anyInt(), anyString(), anyCollection(), isA(Serializable.class))).thenReturn(100);
        int result = simpleService.method1(1, "lxcecho", Collections.emptyList(), "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.method1(2, "Lee", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(100));
    }

    @Test
    public void testWildcardArgumentMatcherWithSpecial() {
        when(simpleService.method1(anyInt(), eq("lxcecho"), anyCollection(), isA(Serializable.class))).thenReturn(100);
        when(simpleService.method1(anyInt(), eq("Lee"), anyCollection(), isA(Serializable.class))).thenReturn(200);

        int result = simpleService.method1(1, "lxcecho", Collections.emptyList(), "Mockito");
        assertThat(result, equalTo(100));

        result = simpleService.method1(2, "Lee", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(200));

        result = simpleService.method1(2, "Leddde", Collections.emptySet(), "MockitoForJava");
        assertThat(result, equalTo(0));
    }

    @Test
    public void wildcardMethod1() {
        List<Object> emptyList = Collections.emptyList();
        doNothing().when(simpleService).method2(anyInt(), anyString(), anyCollection(), isA(Serializable.class));
        simpleService.method2(1, "lxcecho", emptyList, "Mockito");
        verify(simpleService, times(1)).method2(1, "lxcecho", emptyList, "Mockito");
        verify(simpleService, times(1)).method2(anyInt(), eq("lxcecho"), anyCollection(), isA(Serializable.class));
    }


    @After
    public void destroy() {
        reset(simpleService);
    }

}
