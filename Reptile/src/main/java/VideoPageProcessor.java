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

    List<String> list = new ArrayList<>();

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数等
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
            user.setFollowerCount(follower_count);
            user.setLikeCount(total_favorited);
            user.setLink(user_link);
            page.putField("user",user);
        }

        //保存当前视频信息
        Video video = new Video();
        String video_name = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[1]/div/h1/span[2]/span/span/span/span/text()").toString();
        String like_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[1]/span/text()").toString();
        String comment_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[2]/span/text()").toString();
        String collect_num = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/div[1]/div[3]/span/text()").toString();
        String videoUrl = page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[1]/div/div[2]/div[2]/xg-video-container/video/source[3]/@src").toString();
        if(video_name != null){
            video.setVideoName(video_name);
            video.setLikeNum(like_num);
            video.setCommentNum(comment_num);
            video.setCollectNum(collect_num);
            video.setVideoUrl(videoUrl);
            video.setUrl(page.getUrl().toString());
            video.setUserName(user.getNickname());
            page.putField("video",video);
        }

        //从页面发现后续的url地址来抓取(右侧主页视频和推荐视频)
        page.addTargetRequests(page.getHtml().links().regex("//www.douyin.com/video/\\w+").all());


        //添加入口urlList（前面获取到的url列表）
        page.addTargetRequests(list);
    }

    @Override
    public Site getSite() {
        return site;
    }


}