package cn.edu.swu.zl.reptilespring;

import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.dao.VideoDao;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.service.AlarmTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApplicationListenerInitialize implements ApplicationRunner {
    @Autowired
    private UserDao userDao;

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private AlarmTask alarmTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //init inc table
        //initUserIncTable();
      //  alarmTask.updateUserIncremental();

      //  initVideoIncTable();
       // alarmTask.updateVideoIncremental();
    }

    private void initUserIncTable(){
        List<User> list = userDao.getUserAll().subList(0,100);
        for(User e : list){
            userDao.insertUserToUserIncTab(e);
        }
        System.out.println("user_inc table init success!!");
    }

    private void initVideoIncTable(){
        List<Video> list = videoDao.getVideoAll().subList(0,50);
        for(Video e : list){
            videoDao.insertVideoToVideoIncTab(e);
        }
        System.out.println("video_inc table init success!!");
    }



}
