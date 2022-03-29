import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;


public class LinkPageProcessor implements PageProcessor {

    static final String regex = "https://www.iesdouyin.com/share/video/\\w+";
   // static final String regex = "https://www.iesdouyin.com/share/video/7079674752108907812";

    static String json;
  //  public static List<String> list = new ArrayList<>();

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

      //  page.putField("video", page.getHtml().xpath("/html").toString());
      //  page.putField("video", page.getJson().all().toString());


        if(page.getUrl().regex("https://creator.douyin.com/aweme/v1/creator/data/billboard/\\?billboard_type=\\d+").match()){

            json = page.getJson().all().toString();
          //  json = page.getHtml().xpath("/html/body/pre").toString();
           // System.out.println(json);
            //能够获取到多个url
            JsonPathSelector jsonPathSelector = new JsonPathSelector("$..extra_list[*].link");

            Main.urlList = jsonPathSelector.selectList(json);

            for(String s : Main.urlList){
                System.out.println(s);
            }

         //   page.addTargetRequests(list);
        }else{
            System.out.println("***" + page.getUrl());
        }


        // 部分三：从页面发现后续的url地址来抓取
       // page.addTargetRequests(page.getHtml().links().regex(regex).all());
    }

    @Override
    public Site getSite() {
        return site;
    }


}