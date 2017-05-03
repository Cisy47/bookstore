package Message;

import Biz.OrderBiz;
import Biz.UserBiz;
import Entity.User;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;

/**
 * Created by 47 on 2017/4/15.
 */
public class OrderMessageListener implements MessageListener {
    private JmsTemplate jmsTemplate;
    private OrderBiz orderBiz;
    private UserBiz userBiz;

    public OrderBiz getOrderBiz() {
        return orderBiz;
    }
    public void setOrderBiz(OrderBiz orderBiz) {
        this.orderBiz = orderBiz;
    }
    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage text = (TextMessage) message;
                String[] strs=text.getText().split("@");
                User user=userBiz.getUserByAccount(strs[0]);
                HashMap<Integer,Integer> shopCart =new HashMap<Integer, Integer>();
                for(int i=1;i<strs.length;i=i+2){
                    shopCart.put(Integer.parseInt(strs[i]),Integer.parseInt(strs[i+1]));
                }
                try{
                    orderBiz.createOrderFromShopCart(shopCart, user);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                shopCart.clear();
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }

    }
}
