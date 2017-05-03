/**
 * Created by 47 on 2017/3/17.
 */
import java.security.Principal;

public class RolePrincipal implements Principal{

    private String name;

    public RolePrincipal(String name) {
        super();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}