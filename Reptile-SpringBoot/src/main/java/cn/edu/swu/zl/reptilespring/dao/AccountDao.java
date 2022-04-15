package cn.edu.swu.zl.reptilespring.dao;

import cn.edu.swu.zl.reptilespring.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Repository
public class AccountDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public AccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //验证用户名，密码是否正确
    public Account checkAccount(String username, String pass){
        String md5Password = DigestUtils.md5DigestAsHex(pass.getBytes());

        final String sql = "select * from account where username=? and password=?;";

        Account account = new Account();

        Map<String, Object> map;
        try{
            map = jdbcTemplate.queryForMap(sql,username,md5Password);
        }catch (EmptyResultDataAccessException e){  //查询不到
            return account;
        }

        //查询到结果
        account.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        account.setUsername((String) map.get("username"));
        account.setPassword((String) map.get("password"));      //md5
        account.setNickname((String) map.get("nickname"));
        account.setHeadImg((String) map.get("headImg"));

        return account;
    }

    //查找是否注册此用户名
    public boolean checkUsername(String username){
        final String sql = "select * from account where username=?;";

        try{
            jdbcTemplate.queryForMap(sql,username);
        }catch (EmptyResultDataAccessException e){
            return false;
        }
        return true;
    }


    //注册账号
    public boolean registerAccount(Account account){
        String md5Password = DigestUtils.md5DigestAsHex(account.getPassword().getBytes());

        final String sql = "insert into account(username, password, nickname, headImg) value(?,?,?,?);";

        Object[] objects = new Object[]{account.getUsername(),md5Password,account.getNickname(),account.getHeadImg()};

        return jdbcTemplate.update(sql,objects) > 0;
    }

}
