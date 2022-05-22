package com.xc.joy.mockito;

import com.xc.joy.mockito.basic.Account;
import com.xc.joy.mockito.basic.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class MockByAnnotationTest {

//    @Mock
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private AccountDao accountDao;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMock() {
        Account account = accountDao.find("lxcecho", "123456");
        System.out.println(account);
    }

}
