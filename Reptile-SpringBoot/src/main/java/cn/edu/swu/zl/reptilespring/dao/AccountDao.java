package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.Account;
import cn.edu.swu.zl.reptilespring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public AccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkAccount(String username, String pass){
        String sql = "select * from account where username="+username+"and password="+pass+";";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Account.class)).size() != 0;
    }

}
