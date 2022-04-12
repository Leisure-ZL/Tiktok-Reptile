package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.AccountDao;
import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.entity.Account;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public boolean check(String username, String pass) {

        return accountDao.checkAccount(username, pass);

    }

}
