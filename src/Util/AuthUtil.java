package Util;

import DB.DbManager;
import Model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 */
public class AuthUtil {
    public static boolean Auth(String name, String pwd) {

        User user = DbManager.getUser(name);
        if (user == null)
            return false;
        if (!pwd.equals(user.getPwd()))
            return false;

        return true;
    }
    public static User Auth(HttpServletRequest request){
        User user = null;
        if (Auth(request.getHeader("name"),request.getHeader("pwd"))){
            user = DbManager.getUser(request.getHeader("name"));
        }
        return user;
    }
}
