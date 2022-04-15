package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.AccountDao;
import cn.edu.swu.zl.reptilespring.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public Account login(String username, String pass) {

        if(accountDao.checkUsername(username)){
            return accountDao.checkAccount(username, pass);
        }else{
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(pass);
            register(account);  //先注册
            account = accountDao.checkAccount(username, pass);  //再查询一次
            return account;
        }
    }

    @Override
    public boolean register(Account account) {

        return accountDao.registerAccount(account);

    }

}
