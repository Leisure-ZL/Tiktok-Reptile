package dao;

import bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public int add(Connection con, User user) throws SQLException {
        //主键不存在插入，存在则更新数据
        String sql = "insert into user(nickname,follower_count,total_favorited,user_link) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE" +
                " follower_count=VALUES(follower_count),total_favorited=VALUES(total_favorited),user_link=VALUES(user_link)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, user.getNickname());
        pstm.setString(2, user.getFollower_count());
        pstm.setString(3, user.getTotal_favorited());
        pstm.setString(4, user.getUser_link());
        return pstm.executeUpdate();
    }

}
