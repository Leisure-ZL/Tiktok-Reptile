package util;

import java.sql.*;

public class JdbcUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            conn = DriverManager.getConnection(Ignore.jdbcUrl,Ignore.jdbcUser ,Ignore.jdbcPass );
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
