package com.xc.joy.mockito;

import com.xc.joy.model.MockitoDemo;
import com.xc.joy.service.MockDemoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class DeepMockTest {

   /* @Mock // testDeepMock0
    private MockDemoService mockDemoService;*/

    @Mock(answer = Answers.RETURNS_DEEP_STUBS) // testDeepMock2
    private MockDemoService mockDemoService;

    @Mock // testDeepMock1
    private MockitoDemo mockitoDemo;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeepMock0() {
        MockitoDemo mockitoDemo = mockDemoService.get();
        mockitoDemo.foo();
    }

    @Test
    public void testDeepMock1() {
        // stubbling...
        when(mockDemoService.get()).thenReturn(mockitoDemo);
        MockitoDemo result = mockDemoService.get();
        result.foo();
    }

    @Test
    public void testDeepMock2() {
        mockDemoService.get().foo();
    }

}
