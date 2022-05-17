package com.xc.joy.controller;

import com.xc.joy.dao.AccountDao;
import com.xc.joy.model.Account;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class AccountLoginController {

    private final AccountDao accountDao;

    public AccountLoginController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account account = accountDao.find(username, password);
        if (account == null) {
            return "/login";
        } else {
            return "/index";
        }
    }
}
