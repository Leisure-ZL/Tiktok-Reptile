import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.util.List;

public class Main {

    public static List<String> urlList;

    public static void main(String[] args) {
        final String adUrl = "https://creator.douyin.com/aweme/v1/creator/data/billboard/?billboard_type=1";

        Spider.create(new LinkPageProcessor())
                .addUrl(adUrl)
                .thread(1).run();


        Spider.create(new VideoPageProcessor())
                .setDownloader(new SeleniumDownloader("doc/util/chromedriver.exe"))
                .addUrl(urlList.get(0))
                .addPipeline(new JsonFilePipeline("C:\\Users\\86182\\Desktop\\Reptile\\data"))
                .thread(5).run();
    }


}
