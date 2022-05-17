package com.xc.joy.mockito;

import com.xc.joy.dao.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    AccountDao accountDao = mock(AccountDao.class);



    @Test
    public void testMock() {

    }

}
