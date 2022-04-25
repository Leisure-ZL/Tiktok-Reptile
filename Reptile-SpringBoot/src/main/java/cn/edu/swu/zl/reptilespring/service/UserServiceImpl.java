package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserRaw> getAll() {
        return userDao.getUserRawAll();
    }

    @Override
    public List<UserRaw> getUserRankByFollower() {
        List<UserRaw> list = userDao.getUserRawAll();
        Collections.sort(list, new Comparator<UserRaw>() {
            @Override
            public int compare(UserRaw o1, UserRaw o2) {
                long res = DataUtil.stringToNum(o1.getFollowerCount()) - DataUtil.stringToNum(o2.getFollowerCount());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list.subList(0,100);
    }


    @Override
    public List<User> getUserByFollowerInc(int size) {
        return userDao.getUserByFollowerInc(size);
    }

    @Override
    public List<User> getUserByLikeInc(int size) {
        return userDao.getUserByLikeInc(size);
    }

    @Override
    public List<User> getUserByFollower(int size) {
        return userDao.getUserByFollower(size);
    }

    @Override
    public List<User> getUserByLike(int size) {
        return userDao.getUserByLike(size);
    }

    @Override
    public User getUser(Map<String,String> map) {
        return userDao.getUser(map);
    }

    @Override
    public List<User> getUserByVague(String s) {
        return userDao.getUserByVague(s);
    }


}
