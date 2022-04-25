package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;

import java.util.List;
import java.util.Map;

public interface UserService {

    //获取所有用户
     List<UserRaw> getAll();

    //获取用户粉丝数排名(从大到小)
    public List<UserRaw> getUserRankByFollower();

    //获取用户粉丝增量排名
    List<User> getUserByFollowerInc(int size);

    //获取用户点赞增量排名
    List<User> getUserByLikeInc(int size);

    //获取用户粉丝排名
    List<User> getUserByFollower(int size);

    //获取用户点赞排名
    List<User> getUserByLike(int size);

    //通过id查询User
    User getUser(Map<String,String> map);

    //通过关键字模糊查询
    List<User> getUserByVague(String s);

}
