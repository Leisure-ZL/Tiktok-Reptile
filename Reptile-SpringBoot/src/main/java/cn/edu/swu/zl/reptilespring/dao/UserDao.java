package cn.edu.swu.zl.reptilespring.dao;

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

    public List<UserRaw> getUserAll(){
        String sql = "select * from user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserRaw.class));
    }

}
