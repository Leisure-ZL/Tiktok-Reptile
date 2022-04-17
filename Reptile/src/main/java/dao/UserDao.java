package dao;

import bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public int add(Connection con, User user) throws SQLException {
        //主键不存在插入，存在则更新数据
        String sql = "insert into user_raw(nickname,follower_count,like_count,link) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE" +
                " follower_count=VALUES(follower_count),like_count=VALUES(like_count),link=VALUES(link)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, user.getNickname());
        pstm.setString(2, user.getFollowerCount());
        pstm.setString(3, user.getLikeCount());
        pstm.setString(4, user.getLink());
        return pstm.executeUpdate();
    }

}
