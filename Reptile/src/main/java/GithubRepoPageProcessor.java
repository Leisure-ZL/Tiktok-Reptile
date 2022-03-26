import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class GithubRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
      //  page.putField("author", page.getUrl().regex("http://cis.swu.edu.cn/info/1039/*.htm").toString());
        page.putField("name", page.getHtml().xpath("/html/body/div/div[3]/div/div/div[2]/form/div[1]").toString());

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("http://cis.swu.edu.cn/info/\\w+/\\w+.htm").all());
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor())
                .addUrl("http://cis.swu.edu.cn/")
             //   .addPipeline(new JsonFilePipeline("C:\\Users\\86182\\Desktop"))
                .thread(5).run();
    }
}