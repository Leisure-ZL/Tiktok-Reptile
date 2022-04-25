package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
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

    /**
    * user
    * */

    public List<User> getUserByFollower(int size){
        String sql = "select * from user order by follower_count desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgUser(rs);
        });
    }



    public List<User> getUserByLike(int size){
        String sql = "select * from user order by like_count desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgUser(rs);
        });
    }

    public List<User> getUserByLikeInc(int size){
        String sql = "select * from user order by like_incremental desc limit " + size;
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return pkgUser(rs);
            }
        });
    }



    public List<User> getUserAll(){
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return pkgUser(rs);
            }
        });
    }

    public User getUser(Map<String,String> map){
        String key = map.get("key");
        String value = map.get("value");
        List<User> list = jdbcTemplate.query(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("select * from user where "+key+"=?");
                statement.setString(1, value);
                return statement;
            }
        },new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return pkgUser(rs);
            }
        });
        if (list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public List<User> getUserByVague(String s){
        List<User> list = jdbcTemplate.query(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("select * from user where nickname like ? limit 8");
                statement.setString(1, "%"+s+"%");
                return statement;
            }
        },new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return pkgUser(rs);
            }
        });
        return list;
    }


    public boolean insertUserToUserTab(User user) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                "follower_count=?,like_count=?,follower_incremental=?," +
                "like_incremental=?";
        int res = jdbcTemplate.update(sql,user.getId(),user.getNickname(),DataUtil.stringToNum(user.getFollowerCount())
                ,DataUtil.stringToNum(user.getLikeCount()), user.getLink(),user.getHeadImg(),
                (int)user.getFollowerIncremental(),(int)user.getLikeIncremental(),
                DataUtil.stringToNum(user.getFollowerCount()),DataUtil.stringToNum(user.getLikeCount()),
                (int)user.getFollowerIncremental(),(int)user.getLikeIncremental());
        return res > 0;
    }



    /**
    * user_inc
    * */

    public List<User> getUserByFollowerInc(int size){
        String sql = "select * from user_inc order by follower_incremental desc limit " + size;
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


    /**
     * base
     * */

    private User pkgUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNickname(rs.getString("nickname"));
        user.setFollowerCount(DataUtil.numToString(rs.getLong("follower_count")));
        user.setLikeCount(DataUtil.numToString(rs.getLong("like_count")));
        user.setLink(rs.getString("link"));
        user.setHeadImg(rs.getString("head_img"));
        user.setFollowerIncremental(rs.getInt("follower_incremental"));
        user.setLikeIncremental(rs.getInt("like_incremental"));
        return user;
    }


}
