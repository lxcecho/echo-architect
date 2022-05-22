package com.xc.joy.mockito;

import com.xc.joy.mockito.basic.Account;
import com.xc.joy.mockito.basic.AccountDao;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class MockitoByRuleTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AccountDao accountDao;

    @Test
    public void testMock() {
        Account account = accountDao.find("lxcecho", "123456");
        System.out.println(account);
    }


}
