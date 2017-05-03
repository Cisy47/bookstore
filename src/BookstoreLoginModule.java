import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 47 on 2017/3/17.
 */
public class BookstoreLoginModule implements LoginModule{
        private CallbackHandler handler;
        private Subject subject;
        private UserPrincipal userPrincipal;
        private RolePrincipal rolePrincipal;
        private String login;
        private List<String> userGroups;

        @Override
        public void initialize(Subject subject,
            CallbackHandler callbackHandler,
            Map<String, ?> sharedState,
            Map<String, ?> options) {

        handler = callbackHandler;
        this.subject = subject;
    }

        @Override
        public boolean login() throws LoginException {
            String url = "jdbc:mysql://localhost:3306/bookstore";
            String driver = "com.mysql.jdbc.Driver";
            String userD = "root";
            String passwordD = "4747";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Callback[] callbacks = new Callback[2];
            callbacks[0] = new NameCallback("username:");
            callbacks[1] = new PasswordCallback("password", true);

        try {
            handler.handle(callbacks);
            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

            if (name != null && password != null) {
                if (userGroups == null) {
                    userGroups = new ArrayList<String>();
                }

                try {
                    Connection conn = DriverManager.getConnection(url, userD, passwordD);//获取连接
                    PreparedStatement statement=conn.prepareStatement("SELECT * FROM users WHERE account= ?");
                    statement.setString(1,name);
                    ResultSet resultSet=statement.executeQuery();
                    if(!resultSet.next()){
                        throw new LoginException("Authentication fail");
                    }
                    String pw =resultSet.getString("password");
                    if(!pw.equals(password)){
                        throw new LoginException("Authentication fail");
                    }
                    login=name;
                    if(resultSet.getInt("roleid")==1){
                        userGroups.add("user");
                    }else{
                        userGroups.add("manager");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            // If credentials are NOT OK we throw a LoginException
            throw new LoginException("Authentication failed");
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }

    }
        @Override
        public boolean commit() throws LoginException {

        userPrincipal = new UserPrincipal(login);
        subject.getPrincipals().add(userPrincipal);

        if (userGroups != null && userGroups.size() > 0) {
            for (String groupName : userGroups) {
                rolePrincipal = new RolePrincipal(groupName);
                subject.getPrincipals().add(rolePrincipal);
            }
        }

        return true;
    }
        @Override
        public boolean abort() throws LoginException {
        return false;
    }
        @Override
        public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }
    }

