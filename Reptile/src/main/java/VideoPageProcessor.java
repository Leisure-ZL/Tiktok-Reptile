import bean.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class VideoPageProcessor implements PageProcessor {

    static final String regex = "https://www.iesdouyin.com/share/video/\\w+";
   // static final String regex = "https://www.iesdouyin.com/share/video/7079674752108907812";

    static String json;
    List<String> list = new ArrayList<>();

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    public VideoPageProcessor(){
        this.list = Main.urlList;
    }

    @Override
    public void process(Page page) {

        User user = new User();

        String nickname = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/a/div/span/span/span/span/span/text()").toString();
        String follower_count = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/p/span[2]/text()").toString();
        String total_favorited = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/p/span[4]/text()").toString();
        String user_link = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/a/@href").toString();

        user.setNickname(nickname);
        user.setFollower_count(follower_count);
        user.setTotal_favorited(total_favorited);
        user.setUser_link(user_link);

        page.putField("user",user);

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(list);
    }

    @Override
    public Site getSite() {
        return site;
    }



}