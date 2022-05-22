package com.xc.joy.junit4;

import com.xc.joy.junit4.basic.Account;
import com.xc.joy.junit4.basic.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoRunnerTest {

    @Test
    public void testMock() {
//        AccountDao accountDao = mock(AccountDao.class);
        AccountDao accountDao = mock(AccountDao.class, Mockito.RETURNS_SMART_NULLS);
        Account account = accountDao.find("lxcecho", "123456");
        System.out.println(account);
    }

}
