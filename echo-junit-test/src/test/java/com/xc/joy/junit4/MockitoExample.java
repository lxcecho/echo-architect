package com.xc.joy.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 *
 * Mockito-用不同的参数验证多个方法调用: <a href="https://howtodoinjava.com/mockito/verify-multiple-method-arguments/">...</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoExample {
    @Mock
    Map<String, Integer> map = new HashMap<>();

    @Captor
    ArgumentCaptor<String> keyCaptor;

    @Captor
    ArgumentCaptor<Integer> valueCaptor;

    @Test
    public void saveTest() {
        map.put("A", 1);
        map.put("B", 2);

        // 1. verify method was invoked N times
        verify(map, times(2)).put(keyCaptor.capture(), valueCaptor.capture());

        List<String> keys = keyCaptor.getAllValues();
        List<Integer> values = valueCaptor.getAllValues();

        // 2. verify method argument values as List
        assertEquals(Arrays.asList("A", "B"), keys);
        assertEquals(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)), values);

        // 3. Verify method arguments separately
        assertEquals("A", keys.get(0));
        assertEquals("B", keys.get(1));

        assertEquals(Integer.valueOf(1), values.get(0));
        assertEquals(Integer.valueOf(2), values.get(1));

    }

}
