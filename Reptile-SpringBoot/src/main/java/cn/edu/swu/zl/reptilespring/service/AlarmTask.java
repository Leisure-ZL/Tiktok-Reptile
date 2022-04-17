package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.dao.VideoDao;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AlarmTask {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VideoDao videoDao;

    WebDriver driver;

   // @Scheduled(cron = "0/5 * * * * *")
    public void updateUserIncremental(){
        System.getProperties().setProperty("webdriver.chrome.driver", "doc/util/chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("进行数据更新");

        //数据库获取userRaw
        List<UserRaw> list = userDao.getUserRawAll();

        //TODO:多线程访问
        //通过访问user主页，获取最新数据
        for(UserRaw e : list){
            System.out.println("update "+ e.toString());
            User user = getUser(e.getLink());
            //计算增量，重新封装user
            user.setId(e.getId());
            user.setNickname(e.getNickname());
            user.setLink(e.getLink());
            user.setFollowerIncremental(getIncremental(e.getFollowerCount(), user.getFollowerCount()));
            user.setLikeIncremental(getIncremental(e.getLikeCount(), user.getLikeCount()));
            userDao.insertUserToUserTab(user);

            //更新userRaw
            e.setFollowerCount(user.getFollowerCount());
            e.setLikeCount(user.getLikeCount());
            userDao.updateUserRaw(e);

            //更新user_inc
            updateUserIncTable(user);
        }
        System.out.println("数据更新结束");

    }


    public void updateVideoIncremental(){
        List<VideoRaw> list = videoDao.getVideoRawAll();

        for(VideoRaw e : list){
            Video video = getVideo(e.getUrl());
            video.setId(e.getId());
            video.setVideoName(e.getVideoName());
            video.setUrl(e.getUrl());
            video.setVideoUrl(e.getVideoUrl());
            video.setUserName(e.getUserName());
            video.setLikeIncremental(getIncremental(e.getLikeNum(),video.getLikeNum()));
            video.setCommentIncremental(getIncremental(e.getCommentNum(),video.getCommentNum()));
            video.setCollectIncremental(getIncremental(e.getCollectNum(),video.getCollectNum()));
            videoDao.insertVideoToVideoTab(video);

            e.setLikeNum(video.getLikeNum());
            e.setCommentNum(video.getCommentNum());
            e.setCollectNum(video.getCollectNum());
            videoDao.updateVideoRaw(e);

            updateVideoIncTable(video);

        }


    }

    private void updateUserIncTable(User user){
        List<User> list = userDao.getUserByLeastFloInc();
        if(list.get(0).getFollowerIncremental() < user.getFollowerIncremental()){
            userDao.deleteUserFromUserIcnTab(list.get(0));
            userDao.insertUserToUserIncTab(user);
        }
    }

    private void updateVideoIncTable(Video video){
        List<Video> list = videoDao.getVideoByLeastFloInc();
        if(list.get(0).getLikeIncremental() < video.getLikeIncremental()){
            videoDao.deleteVideoFromVideoIcnTab(list.get(0));
            videoDao.insertVideoToVideoIncTab(video);
        }
    }

    private static long getIncremental(String pre, String now){
        return DataUtil.stringToNum(now) - DataUtil.stringToNum(pre);
    }

    public User getUser(String url){

        User user = new User();
        driver.get("https:"+url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement followerView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[2]/div[2]"));
        WebElement likeView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div[2]"));
        WebElement headImg = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[1]/div/img"));

        user.setHeadImg(headImg.getAttribute("src"));
        user.setFollowerCount(followerView.getText());
        user.setLikeCount(likeView.getText());

        return user;
    }

    public Video getVideo(String url){
        Video video = new Video();
        driver.get(url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement likeView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[1]/span"));
        WebElement commentView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[2]/span"));
        WebElement collectView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[3]/span"));

        video.setLikeNum(likeView.getText());
        video.setCommentNum(commentView.getText());
        video.setCollectNum(collectView.getText());

        return video;
    }


}
