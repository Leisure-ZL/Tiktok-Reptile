import bean.User;
import dao.UserDao;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DBPipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {

        User user = resultItems.get("user");

        UserDao userDao = new UserDao();

        Connection connection = JdbcUtil.getConnection();

        try {
            userDao.add(connection,user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
