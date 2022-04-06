import bean.User;
import bean.Video;
import dao.UserDao;
import dao.VideoDao;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.JdbcUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBPipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {

        User user = resultItems.get("user");
        Video video = resultItems.get("video");

        UserDao userDao = new UserDao();
        VideoDao videoDao = new VideoDao();


        try {
            Connection connection = JdbcUtil.getConnection();
            userDao.add(connection,user);
            videoDao.add(connection,video);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }
}
