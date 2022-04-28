package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseRequest;
import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.Account;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserCollect;
import cn.edu.swu.zl.reptilespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
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

    @ResponseBody
    @PostMapping("/user/collect")
    public BaseResponse<String> collectUser(@RequestBody BaseRequest<UserCollect> req) throws NoSuchAlgorithmException {
        System.out.println("req:" + req.getData().toString());
        BaseResponse<String> resp = new BaseResponse<>();
        boolean res = userService.collectUser(req.getData());
        if(res){
            resp.setCode(200);
            resp.setMsg("OK");
        }else {
            resp.setCode(-1);
            resp.setMsg("FAILED");
        }
        resp.setData("collect");
        System.out.println("resp:" + resp.toString());
        return resp;
    }

    @ResponseBody
    @PostMapping("/user/un_collect")
    public BaseResponse<String> unCollectUser(@RequestBody BaseRequest<UserCollect> req) throws NoSuchAlgorithmException {
        BaseResponse<String> resp = new BaseResponse<>();
        boolean res = userService.unCollectUser(req.getData());
        if(res){
            resp.setCode(200);
            resp.setMsg("OK");
        }else {
            resp.setCode(-1);
            resp.setMsg("FAILED");
        }
        resp.setData("unCollect");
        return resp;
    }


    @ResponseBody
    @RequestMapping("/user/collect/count")
    public BaseResponse<Integer> getCollectCount(@RequestParam("id") int id){
        BaseResponse<Integer> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getCollectCount(id));
        System.out.println("req:" + id);
        System.out.println("resp:" + resp.toString());
        return resp;
    }


    @ResponseBody
    @RequestMapping("/user/collect/list")
    public BaseResponse<List<UserCollect>> getCollectList(@RequestParam("id") int id){
        BaseResponse<List<UserCollect>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(userService.getCollectList(id));
        return resp;
    }

    @ResponseBody
    @PostMapping("/user/collect/is")
    public BaseResponse<Boolean> isCollect(@RequestBody BaseRequest<UserCollect> req) throws NoSuchAlgorithmException {
        BaseResponse<Boolean> resp = new BaseResponse<>();
        boolean res = userService.isCollect(req.getData());
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(res);
        System.out.println("resp:" + resp.toString());
        return resp;
    }


}
