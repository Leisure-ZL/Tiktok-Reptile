package dao;

import bean.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideoDao {

    public int add(Connection con, Video video) throws SQLException {
        //主键不存在插入，存在则更新数据
        String sql = "insert into video(video_name,like_num,comment_num,collect_num,url,user_name) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE" +
                " like_num=VALUES(like_num),comment_num=VALUES(comment_num),collect_num=VALUES(collect_num)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, video.getVideo_name());
        pstm.setString(2, video.getLike_num());
        pstm.setString(3, video.getComment_num());
        pstm.setString(4, video.getCollect_num());
        pstm.setString(5, video.getUrl());
        pstm.setString(6, video.getUser_name());
        return pstm.executeUpdate();
    }

}
