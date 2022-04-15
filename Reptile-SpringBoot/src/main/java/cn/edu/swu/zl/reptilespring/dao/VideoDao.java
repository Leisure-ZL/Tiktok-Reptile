package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public VideoDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Video> getUserAll(){
        String sql = "select * from video";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Video.class));
    }

}
