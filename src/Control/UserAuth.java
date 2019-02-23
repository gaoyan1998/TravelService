package Control;

import Config.HttpConfig;
import Model.Code;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
@WebFilter(filterName = "UserAuth")
public class UserAuth implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest reqest = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String currenPath = reqest.getServletPath();
        if (currenPath.equals("/Login") || currenPath.equals("/register"))
            chain.doFilter(req, resp);
        else if (Auth(reqest,response))
            chain.doFilter(req, resp);
    }

    private boolean Auth(HttpServletRequest reqest, HttpServletResponse response) {
        User user = AuthUtil.Auth(reqest);
        Code code;

        if (user == null){
            code = new Code(HttpConfig.AUTH_ERR,"auth failed","");
            try {
                response.getWriter().append(GsonUtil.GsonString(code));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
