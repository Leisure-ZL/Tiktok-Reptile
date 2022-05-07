package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.UserDao;
import cn.edu.swu.zl.reptilespring.dao.VideoDao;
import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Service
public class AlarmTask {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VideoDao videoDao;

    WebDriver userDriver;
    WebDriver videoDriver;

    @Scheduled(cron = "0 0 23 * * ?")   //每天23点执行
    public void updateUserIncremental() {
        System.out.println("启动定时任务:Update User");

        System.getProperties().setProperty("webdriver.chrome.driver", "doc/util/chromedriver.exe");
        userDriver = new ChromeDriver();
        System.out.println("进行数据更新");

        //数据库获取userRaw
        List<UserRaw> list = userDao.getUserRawAll();

        //TODO:多线程访问
        //通过访问user主页，获取最新数据
        for (UserRaw e : list) {
            System.out.println("update " + e.toString());
            User user = getUser(e.getLink());
            if (user == null) {
                continue;
            }
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

    @Scheduled(cron = "0 0 24 * * ?")   //每天24点执行
    public void updateVideoIncremental() {
        System.out.println("启动定时任务:Update Video");

        System.getProperties().setProperty("webdriver.chrome.driver", "doc/util/chromedriver.exe");
        videoDriver = new ChromeDriver();

        List<VideoRaw> list = videoDao.getVideoRawAll();

        for (VideoRaw e : list) {
            System.out.println("update " + e.toString());
            Video video = getVideo(e.getUrl());
            if (video == null) {
                continue;
            }
            video.setId(e.getId());
            video.setVideoName(e.getVideoName());
            video.setUrl(e.getUrl());
            video.setVideoUrl(e.getVideoUrl());
            video.setUserName(e.getUserName());
            video.setLikeIncremental(getIncremental(e.getLikeNum(), video.getLikeNum()));
            video.setCommentIncremental(getIncremental(e.getCommentNum(), video.getCommentNum()));
            video.setCollectIncremental(getIncremental(e.getCollectNum(), video.getCollectNum()));
            videoDao.insertVideoToVideoTab(video);

            e.setLikeNum(video.getLikeNum());
            e.setCommentNum(video.getCommentNum());
            e.setCollectNum(video.getCollectNum());
            videoDao.updateVideoRaw(e);

            updateVideoIncTable(video);
        }

    }

    public boolean isExistElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateUserIncTable(User user) {
        List<User> list = userDao.getUserByLeastFloInc();
        if (list.get(0).getFollowerIncremental() < user.getFollowerIncremental()) {
            userDao.insertUserToUserIncTab(user);
        }
        if(list.size() > 100){
            userDao.deleteUserFromUserIcnTab(list.get(0));
        }
    }

    private void updateVideoIncTable(Video video) {
        List<Video> list = videoDao.getVideoByLeastFloInc();
        if (list.get(0).getLikeIncremental() < video.getLikeIncremental()) {
            videoDao.insertVideoToVideoIncTab(video);
        }
        if(list.size() > 100){
            videoDao.deleteVideoFromVideoIcnTab(list.get(0));
        }
    }

    private static long getIncremental(String pre, String now) {
        return DataUtil.stringToNum(now) - DataUtil.stringToNum(pre);
    }

    public User getUser(String url) {

        User user = new User();
        userDriver.get("https:" + url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        try {
            //先判断是否存在元素
            if (!isExistElement(userDriver, By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[2]/div[2]"))) {
                return null;
            }
            WebElement followerView = userDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[2]/div[2]"));
            WebElement likeView = userDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div[2]"));
            WebElement headImg = userDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[1]/div/div/img"));

            user.setHeadImg(headImg.getAttribute("src"));
            user.setFollowerCount(followerView.getText());
            user.setLikeCount(likeView.getText());
        }catch (NoSuchElementException e){
            return null;
        }



        return user;
    }

    public Video getVideo(String url) {
        Video video = new Video();
        videoDriver.get(url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        videoDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        try {
            if (!isExistElement(videoDriver, By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[1]/span"))) {
                return null;
            }
            WebElement likeView = videoDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[1]/span"));
            WebElement commentView = videoDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[2]/span"));
            WebElement collectView = videoDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[3]/span"));
            video.setLikeNum(likeView.getText());
            video.setCommentNum(commentView.getText());
            video.setCollectNum(collectView.getText());
        }catch (NoSuchElementException e){
            return null;
        }

        return video;
    }


}
