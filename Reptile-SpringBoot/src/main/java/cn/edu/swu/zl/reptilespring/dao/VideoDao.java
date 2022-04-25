package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VideoDao {

    /*
    * videoRaw
    * */

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public VideoDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<VideoRaw> getVideoRawAll(){
        String sql = "select * from video_raw";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(VideoRaw.class));
    }

    public boolean updateVideoRaw(VideoRaw videoRaw){
        String sql = "update video_raw set like_num=?,comment_num = ?,collect_num=? where id = ?;";
        int res =jdbcTemplate.update(sql,videoRaw.getLikeNum(),videoRaw.getCommentNum(),videoRaw.getCollectNum()
                ,videoRaw.getId());
        return res > 0;
    }

    /*
     * video
     * */

    public List<Video> getVideoByLike(int size){
        String sql = "select * from video order by like_num desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgVideo(rs);
        });
    }

    public List<Video> getVideoByComment(int size){
        String sql = "select * from video order by comment_num desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgVideo(rs);
        });
    }

    public List<Video> getVideoByCollect(int size){
        String sql = "select * from video order by collect_num desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgVideo(rs);
        });
    }

    public List<Video> getVideoByCommentInc(int size){
        String sql = "select * from video order by comment_incremental desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgVideo(rs);
        });
    }

    public List<Video> getVideoByCollectInc(int size){
        String sql = "select * from video order by collect_incremental desc limit " + size;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return pkgVideo(rs);
        });
    }



    public List<Video> getVideoAll(){
        String sql = "select * from video";
        return jdbcTemplate.query(sql, new RowMapper<Video>() {
            @Override
            public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
                return pkgVideo(rs);
            }
        });
    }


    public boolean insertVideoToVideoTab(Video video) {
        String sql = "insert into video values(?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                "like_num=?,comment_num=?,collect_num=?,like_incremental=?,comment_incremental=?,collect_incremental=?";
        Object[] objects = new Object[]{
                video.getId(),video.getVideoName(),DataUtil.stringToNum(video.getLikeNum()),
               DataUtil.stringToNum(video.getCommentNum()),DataUtil.stringToNum(video.getCollectNum()),
                video.getUserName(),video.getVideoUrl(),video.getUrl(),(int)video.getLikeIncremental(),
                (int)video.getCommentIncremental(),(int)video.getCollectIncremental(),DataUtil.stringToNum(video.getLikeNum()),
                DataUtil.stringToNum(video.getCommentNum()),DataUtil.stringToNum(video.getCollectNum()),(int)video.getLikeIncremental(),
                (int)video.getCommentIncremental(),(int)video.getCollectIncremental()
        };
        int res = jdbcTemplate.update(sql,objects);
        return res > 0;
    }


    /*
     * video_inc
     * */

    public List<Video> getVideoByLikeInc(int size){
        String sql = "select * from video_inc order by like_incremental desc limit " + size;
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Video.class));
    }



    public boolean deleteVideoFromVideoIcnTab(Video video){
        String sql = "delete from video_inc where id=?;";
        return jdbcTemplate.update(sql,video.getId()) > 0;
    }

    public List<Video> getVideoByLeastFloInc(){
        String sql = "select * from video_inc order by 'like_incremental' asc limit 1;";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Video.class));
    }

    public boolean insertVideoToVideoIncTab(Video video) {
        String sql = "insert into video_inc values(?,?,?,?,?,?,?,?,?,?,?);";
        Object[] objects = new Object[]{
                video.getId(),video.getVideoName(),video.getLikeNum(),video.getCommentNum(),video.getCollectNum(),
                video.getUserName(),video.getVideoUrl(),video.getUrl(),(int)video.getLikeIncremental(),
                (int)video.getCommentIncremental(),(int)video.getCollectIncremental()
        };
        int res = jdbcTemplate.update(sql,objects);
        return res > 0;
    }


    /**
     * base
     * */

    private Video pkgVideo(ResultSet rs) throws SQLException {
        Video video = new Video();
        video.setId(rs.getInt("id"));
        video.setVideoName(rs.getString("video_name"));
        video.setUserName(rs.getString("user_name"));
        video.setLikeNum(DataUtil.numToString(rs.getLong("like_num")));
        video.setCollectNum(DataUtil.numToString(rs.getLong("collect_num")));
        video.setCommentNum(DataUtil.numToString(rs.getLong("comment_num")));
        video.setVideoUrl(rs.getString("video_url"));
        video.setUrl(rs.getString("url"));
        video.setLikeIncremental(rs.getInt("like_incremental"));
        video.setCommentIncremental(rs.getInt("comment_incremental"));
        video.setCollectIncremental(rs.getInt("collect_incremental"));
        return video;
    }


}
