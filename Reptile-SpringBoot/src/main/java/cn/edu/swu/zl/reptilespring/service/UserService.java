package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;

import java.util.List;

public interface UserService {

    //获取所有用户
     List<UserRaw> getAll();

    //获取用户粉丝数排名(从大到小)
    public List<UserRaw> getUserRankByFollower();

    //获取用户粉丝增量排名 （前5）
    List<User> getUserRankByIncremental();




}
