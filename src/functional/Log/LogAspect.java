package functional.Log;
import Entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 47 on 2017/5/7.
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogWriter log;

    @Pointcut("execution(* method.*.*(..))")
    private void every(){}

    @Before("every()")
    public void Log(){

    }

    @Pointcut("execution(* functional.Message.*.*(..))")
    private void orderReceiveMethod() {
    }

    @Pointcut("execution(* Biz.OrderBizImp.createOrder*(..))")
    private void orderProcessMethod(){

    }

    @Pointcut("execution(* Dao.*.*(..))")
    private void Dao(){}

    @Before("Dao()")
    public void DaoLog(JoinPoint call){
        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();
        String str = "Dao-Invoke "+className+":"+methodName;
        log.write(str);
    }

    @Pointcut("execution(* update*(..))&&execution(* Dao.*.remove*(..))&&execution(* Dao.*.add*(..))")
    private void daoChangeMethod(){
    }

    @AfterReturning("orderProcessMethod()&&args(user)")
    public void orderSuccess(User user){
        String username =user.getUsername();
        String str = "user:"+username+" 购买成功！";
        log.write(str);
        System.out.println("订单创建成功");
    }

    @AfterThrowing("orderProcessMethod()&&args(user)")
    public void orderFail(User user){
        String username =user.getUsername();
        String str = "user:"+username+" 购买失败！";
        log.write(str);
        System.out.println("订单创建失败");
    }

    // 针对指定的切入点表达式选择的切入点应用前置通知
    @Before("orderReceiveMethod()")
    public void before(JoinPoint call) {
        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();
        System.out.println("【前置通知（Annotation）】:" + className + "类的" + methodName
                + "方法开始了...");
        String str = "Invoke "+className+":"+methodName+"\n"+" 订单已收到！";
        log.write(str);
    }

    @After("daoChangeMethod()")
    public void dao(JoinPoint call){
        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();
        String str = "Invoke "+className+":"+methodName;
        log.write(str);
    }
}
