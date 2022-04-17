package dao;

import bean.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideoDao {

    public int add(Connection con, Video video) throws SQLException {
        //主键不存在插入，存在则更新数据
        String sql = "insert into video_raw(video_name,like_num,comment_num,collect_num,user_name,video_url,url) VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE" +
                " like_num=VALUES(like_num),comment_num=VALUES(comment_num),collect_num=VALUES(collect_num)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, video.getVideoName());
        pstm.setString(2, video.getLikeNum());
        pstm.setString(3, video.getCommentNum());
        pstm.setString(4, video.getCollectNum());
        pstm.setString(5, video.getUserName());
        pstm.setString(6, video.getVideoUrl());
        pstm.setString(7, video.getUrl());

        return pstm.executeUpdate();
    }

}
