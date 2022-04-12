package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Hello {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/hello")
    public List<User> hello() {
        return userService.getUserRankByFollower();
    }

}
