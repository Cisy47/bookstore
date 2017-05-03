package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 47 on 2017/4/15.
 */
public class OrderProcessAction extends ActionSupport {
    private String content;
    private JmsTemplate jmsTemplate;
    private Destination requestDestination;
    private Destination replyDestination;
    private UserBiz userBiz;
    String payPass;

    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public String getPayPass() {
        return payPass;
    }
    public void setPayPass(String payPass) {
        this.payPass = payPass;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Destination getRequestDestination() {
        return requestDestination;
    }
    public void setRequestDestination(Destination requestDestination) {
        this.requestDestination = requestDestination;
    }
    public Destination getReplyDestination() {
        return replyDestination;
    }
    public void setReplyDestination(Destination replyDestination) {
        this.replyDestination = replyDestination;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        User user = (User)hSession.get("user");
        if (hSession.get("cart") == null){
            json.put("status", "Empty");
            json.put("message", "购物车为空！");
            content = json.toString();
            return ERROR;
        }
        HashMap<Integer,Integer> shopCart = (HashMap)hSession.get("cart");
        if (shopCart.size() == 0){
            json.put("status", "Empty");
            json.put("message", "购物车为空！");
            content = json.toString();
            return ERROR;
        }
        userBiz.verUserPayPass(user.getUsername(),payPass);
        String message="";
        //user Id
        message+=user.getUsername()+"@";
        for (int o : shopCart.keySet()) {
            message+=String.valueOf(o)+"@"+String.valueOf(shopCart.get(o))+"@";
        }
        sendMessage(message);
        json.put("message", "订单已成功提交");
        shopCart.clear();
        // ((HashMap)hSession.get("shopCart")).clear();
        content = json.toString();
        return SUCCESS;
    }

    public void sendMessage(final String message) {
        jmsTemplate.send(requestDestination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(message);
                    return textMessage;
                }
            });
        }

}
