package Util;

import DB.DbManager;
import Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static User AuthSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    /**
     * @param email 待验证的字符串
     * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean AuthEmail( String email ) {
        String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
        return match( regex ,email );
    }
    /**
     * @param str 待验证的字符串
     * @return 如果是符合网址格式的字符串,返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isHomepage( String str ){
        String regex = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*" ;
        return match( regex ,str );
    }
    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match( String regex ,String str ){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher( str );
        return matcher.matches();
    }

}
