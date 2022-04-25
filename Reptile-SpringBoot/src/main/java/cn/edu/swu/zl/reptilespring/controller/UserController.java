package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("/user/inc_follower")
    public BaseResponse<List<User>> getFollowerInc(@RequestParam("size") int size){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserByFollowerInc(size));
        return resp;
    }

    @ResponseBody
    @RequestMapping("/user/inc_like")
    public BaseResponse<List<User>> getLikeInc(@RequestParam("size") int size){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserByLikeInc(size));
        return resp;
    }

    @ResponseBody
    @RequestMapping("/user/follower")
    public BaseResponse<List<User>> getFollower(@RequestParam("size") int size){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserByFollower(size));
        return resp;
    }

    @ResponseBody
    @RequestMapping("/user/like")
    public BaseResponse<List<User>> getLike(@RequestParam("size") int size){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserByLike(size));
        return resp;
    }

    @ResponseBody
    @RequestMapping("/user")
    public BaseResponse<User> getUser(@RequestParam Map<String,String> map){
        BaseResponse<User> resp = new BaseResponse<>();
        if(userService.getUser(map) == null){
            resp.setCode(-1);
            resp.setMsg("not found");
        }else {
            resp.setCode(200);
            resp.setMsg("OK");
        }
        resp.setData(userService.getUser(map));
        System.out.println("req:" + map.toString());
        System.out.println("resp:" + resp.toString());
        return resp;
    }

    @ResponseBody
    @RequestMapping("/user/vague")
    public BaseResponse<List<User>> getUserByVague(@RequestParam("s") String s){
        BaseResponse<List<User>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getUserByVague(s));
        System.out.println("req:" + s);
        System.out.println("resp:" + resp.toString());
        return resp;
    }


}
