package functional.WebService.SOAP;

import Entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by siqi.lou on 11/05/2017.
 */
@WebService
public interface UserService {

    @WebMethod(operationName = "getUser")
    @WebResult(name = "out")
    public User getUser(@WebParam(name = "userId",targetNamespace = "http://SOAP.WebService.functional/") int id);
}
