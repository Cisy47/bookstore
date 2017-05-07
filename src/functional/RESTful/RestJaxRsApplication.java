package functional.RESTful;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Created by 47 on 2017/5/5.
 */
public class RestJaxRsApplication extends ResourceConfig {
    public RestJaxRsApplication() {

        // register application resources
        this.register(BookResource.class);

        // register filters
        register(RequestContextFilter.class);
        //register(LoggingResponseFilter.class);
        //register(CORSResponseFilter.class);

        // register features
        register(JacksonFeature.class);
        //register(MultiPartFeature.class);
    }
}
