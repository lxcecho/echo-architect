package com.xc.joy.junit4;

import com.xc.joy.junit4.basic.DatabaseDAO;
import com.xc.joy.junit4.basic.MainClass;
import com.xc.joy.junit4.basic.NetworkDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author lxcecho 909231497@qq.com
 * @since 23.05.2022
 */
public class MainClassTest {

    /**
     * @InjectMocks: 在 mockito 中，我们需要创建要测试的类的对象，然后插入它的依赖项（mocked）以完全测试行为。为此，我们使用 @InjectMocks 注解。
     * @Mock 和 @InjectMocks 不同：<a href="https://howtodoinjava.com/mockito/mockito-mock-injectmocks/">...</a>
     * @InjectMocks: creates objects and inject mocked dependencies
     * @Mock: creates mocks
     */

    @InjectMocks
    MainClass mainClass;

    @Mock
    DatabaseDAO databaseDAO;

    @Mock
    NetworkDAO networkDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validate() {
        // mainClass 方法体被执行了， 其依赖使用了 mock
        boolean saved = mainClass.save("lxcecho.txt");
        assertEquals(true, saved);
    }

    @Test
    public void validateTest() {
        boolean saved = mainClass.save("lxcecho.txt");
        assertEquals(true, saved);

        verify(databaseDAO, times(1)).save("lxcecho.txt");
        verify(networkDAO, times(1)).save("lxcecho.txt");
    }

}
