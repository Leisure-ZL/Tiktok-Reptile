package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

    public static Connection getConnection() throws IOException {
        Connection conn = null;
        Properties properties=new Properties();//创建文件对象
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        properties.load(file);
        String driver = properties.getProperty("DRIVER");
        String url = properties.getProperty("URL");
        String uname = properties.getProperty("USERNAME");
        String passwd = properties.getProperty("PASSWORD");
        try {
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
            conn = DriverManager.getConnection(url,uname ,passwd );
          //  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reptile",uname ,passwd );

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 关闭连接
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


//    public static void main(String[] args) {
//        Connection connection = getConnection();
//        try {
//            System.out.println(connection.isClosed());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

}
