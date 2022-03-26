import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {
        System.out.println("----------get page: " + resultItems.getRequest().getUrl());
    }
}
