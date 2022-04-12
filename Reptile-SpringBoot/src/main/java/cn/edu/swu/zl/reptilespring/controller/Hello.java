package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.entity.Account;
import cn.edu.swu.zl.reptilespring.entity.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.service.AccountService;
import cn.edu.swu.zl.reptilespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class Hello {

    @Autowired
    private UserService userService;
    private AccountService accountService;

    @ResponseBody
    @RequestMapping("/hello")
    public List<User> hello() {
        return userService.getUserRankByFollower();
    }

    @ResponseBody
    @RequestMapping("/login")
    public BaseResponse<Account> login(@RequestParam Map<String, String> map) {

        System.out.println("login:" + map.get("username"));

        //TODO:checksum

        BaseResponse<Account> resp = new BaseResponse<>();
        Account account = new Account();

        //检查用户名、密码是否正确
        if(accountService.check(map.get("username"), map.get("password"))) {
            resp.setCode(200);
            resp.setMsg("OK");
        }else {
            resp.setCode(-1);
            resp.setMsg("check fail");
        }
        resp.setChecksum("123456"); //TODO:checksum
        resp.setData(account);

        return resp;

    }

}
