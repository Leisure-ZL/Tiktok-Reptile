package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.Account;

public interface AccountService {

    //验证登录
    Account login(String username, String pass);

    //注册账户
    boolean register(Account account);



}
