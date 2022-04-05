import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class LinkPageProcessor implements PageProcessor {

    static final String regex = "https://www.iesdouyin.com/share/video/\\w+";

    static String json;

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

        if(page.getUrl().regex("https://creator.douyin.com/aweme/v1/creator/data/billboard/\\?billboard_type=\\d+").match()){

            json = page.getJson().all().toString();

            //能够获取到多个url
            JsonPathSelector jsonPathSelector = new JsonPathSelector("$..extra_list[*].link");

            Main.urlList = jsonPathSelector.selectList(json);

            for(String s : Main.urlList){
                System.out.println(s);
            }
        }else{
            System.out.println("***" + page.getUrl());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }


}