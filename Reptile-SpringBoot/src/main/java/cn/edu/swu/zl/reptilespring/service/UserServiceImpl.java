package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserRankByFollower() {
        List<User> list = userDao.getUserAll();
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                long res = DataUtil.stringToNum(o1.getFollower_count()) - DataUtil.stringToNum(o2.getFollower_count());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }

}
