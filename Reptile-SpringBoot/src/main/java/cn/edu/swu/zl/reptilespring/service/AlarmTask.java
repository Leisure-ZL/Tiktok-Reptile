package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.UserRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlarmTask {

    @Autowired
    private UserService userService;

   // @Scheduled(cron = "0/5 * * * * *")
    public void updateUserIncremental(){
        List<User> res = new ArrayList<>();

        //数据库获取user
        List<UserRaw> list = userService.getAll().subList(0, 5);

        //通过访问user主页，获取最新数据
        for(UserRaw e : list){
            User user = getUser(e.getUser_link());
            //计算增量，重新封装user
            user.setId(e.getId());
            user.setNickname(e.getNickname());
            user.setUser_link(e.getUser_link());
            user.setFollower_incremental(getIncremental(e.getFollower_count(), user.getFollower_count()));
            user.setLike_incremental(getIncremental(e.getTotal_favorited(), user.getTotal_favorited()));

            res.add(user);
        }

        //写回user表
        //TODO:数据表换名字，重创表

    }

    private static long getIncremental(String pre, String now){
        return DataUtil.stringToNum(now) - DataUtil.stringToNum(pre);
    }

    public static User getUser(String url){
        User user = new User();
        System.getProperties().setProperty("webdriver.chrome.driver", "doc/util/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement followerView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[2]/div[2]"));
        WebElement likeView = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div[2]"));
        WebElement headImg = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[1]/div[1]/div/img"));

        user.setHead_img(headImg.getAttribute("src"));
        user.setFollower_count(followerView.getText());
        user.setTotal_favorited(likeView.getText());

        return user;
    }


}
