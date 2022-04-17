package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    * user_raw
    * */

    public List<UserRaw> getUserRawAll(){
        String sql = "select * from user_raw;";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserRaw.class));
    }

    public boolean updateUserRaw(UserRaw userRaw){
        String sql = "update user_raw set follower_count=?,like_count = ? where id = ?;";
        int res =jdbcTemplate.update(sql,userRaw.getFollowerCount(),userRaw.getLikeCount(),userRaw.getId());
        return res > 0;
    }

    /*
    * user
    * */

    public List<User> getUserAll(){
        String sql = "select * from user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }


    public boolean insertUserToUserTab(User user) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                "follower_count=?,like_count=?,follower_incremental=?," +
                "like_incremental=?";
        int res = jdbcTemplate.update(sql,user.getId(),user.getNickname(),user.getFollowerCount(),user.getLikeCount(),
                user.getLink(),user.getHeadImg(),(int)user.getFollowerIncremental(),(int)user.getLikeIncremental(),
                user.getFollowerCount(),user.getLikeCount(),(int)user.getFollowerIncremental(),(int)user.getLikeIncremental());
        return res > 0;
    }


    /*
    * user_inc
    * */

    public List<User> getUserIncAllByDesc(){
        String sql = "select * from user_inc order by follower_incremental desc";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }

    public boolean deleteUserFromUserIcnTab(User user){
        String sql = "delete from user_inc where id=?;";
        return jdbcTemplate.update(sql,user.getId()) > 0;
    }

    public List<User> getUserByLeastFloInc(){
        String sql = "select * from user_inc order by 'follower_incremental' asc limit 1;";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }

    public boolean insertUserToUserIncTab(User user) {
        String sql = "insert into user_inc values(?,?,?,?,?,?,?,?);";
        int res = jdbcTemplate.update(sql,user.getId(),user.getNickname(),user.getFollowerCount(),user.getLikeCount(),
                user.getLink(),user.getHeadImg(),(int)user.getFollowerIncremental(),(int)user.getLikeIncremental());
        return res > 0;
    }



}
