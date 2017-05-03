package method;

import Entity.Counter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by 47 on 2017/4/4.
 */
public class CountAction extends ActionSupport {
    private Counter counter;
    private int hitCount;
    private String content;

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public CountAction() {
        this.hitCount = 0;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        hitCount = counter.getHits();
        context.put("count",hitCount);
        return SUCCESS;
    }

}
