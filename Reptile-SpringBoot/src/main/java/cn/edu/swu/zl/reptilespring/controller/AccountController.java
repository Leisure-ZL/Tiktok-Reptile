package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseRequest;
import cn.edu.swu.zl.reptilespring.entity.Account;
import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.service.AccountService;
import cn.edu.swu.zl.reptilespring.service.AlarmTask;
import cn.edu.swu.zl.reptilespring.service.UserService;
import cn.edu.swu.zl.reptilespring.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Controller
public class AccountController {

    static final String SECRET = "e10adc3949ba59ab";

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping("/hello")
    public List<UserRaw> hello() {

        // AlarmTask.getUser("https://www.douyin.com/user/MS4wLjABAAAA8U_l6rBzmy7bcy6xOJel4v0RzoR_wfAubGPeJimN__4");
        return userService.getUserRankByFollower();
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<Account> login(@RequestBody BaseRequest<Account> req) throws NoSuchAlgorithmException {

        System.out.println("req:" + req.getData().toString());
        BaseResponse<Account> resp = new BaseResponse<>();

        //checksum
        String str = req.getData().getUsername() + req.getData().getPassword() + SECRET;
        if (!Objects.equals(EncryptionUtil.getHash(str, "SHA-256"), req.getChecksum())) {
            //校验不通过
            resp.setCode(-1);
            resp.setMsg("checksum fail");
            return resp;
        }


        //登录，如果未注册则注册
        Account account = accountService.login(req.getData().getUsername(), req.getData().getPassword());

        //成功查到记录
        if (account.getId() != 0) {
            resp.setCode(200);
            resp.setMsg("OK");
        } else {
            resp.setCode(10001);        //账号or密码错误
            resp.setMsg("username or password error");
        }

        resp.setData(account);
        System.out.println("resp:" + resp.getData().toString());

        return resp;

    }

}
