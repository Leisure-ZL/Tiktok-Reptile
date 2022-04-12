package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.User;

import java.util.List;

public interface UserService {

    //获取用户粉丝数排名(从大到小)
    public List<User> getUserRankByFollower();


}
