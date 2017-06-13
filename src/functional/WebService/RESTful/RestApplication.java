package functional.WebService.RESTful;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by 47 on 2017/5/16.
 */
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        //服务类所在的包路径
        packages("functional.WebService.RESTful");
        //注册JSON转换器
        register(JacksonJsonProvider.class);
    }

}
