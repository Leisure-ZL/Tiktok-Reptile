import bean.User;
import bean.Video;
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

        //保存博主信息
        User user = new User();
        String nickname = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/a/div/span/span/span/span/span/text()").toString();
        String follower_count = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/p/span[2]/text()").toString();
        String total_favorited = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/p/span[4]/text()").toString();
        String user_link = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[1]/div[2]/a/@href").toString();

        if(nickname != null){
            user.setNickname(nickname);
            user.setFollower_count(follower_count);
            user.setTotal_favorited(total_favorited);
            user.setUser_link(user_link);
            page.putField("user",user);
        }

        //保存当前视频信息
        Video video = new Video();
        String video_name = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[1]/div/h1/span[2]/span/span/span/span/text()").toString();
        String like_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[1]/span/text()").toString();
        String comment_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[2]/span/text()").toString();
        String collect_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[3]/span/text()").toString();
        String url = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[1]/div/div[2]/div[2]/xg-video-container/video/source[1]/@src").toString();

        if(video_name != null){
            video.setVideo_name(video_name);
            video.setLike_num(like_num);
            video.setComment_num(comment_num);
            video.setCollect_num(collect_num);
            video.setUrl(url);
            video.setUser_name(user.getNickname());
            page.putField("video",video);
        }

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(list);
    }

    @Override
    public Site getSite() {
        return site;
    }



}