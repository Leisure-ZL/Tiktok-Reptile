package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("/user/inc_follower")
    public BaseResponse<List<User>> getFollowerInc(){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserRankByIncremental());
        return resp;
    }


}
