package Serv;

import DB.DbManager;
import DB.JdbcUtil;
import Model.Code;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;
import Util.HttpConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/24.
 * Email : ikiler@126.com
 */
@WebServlet(name = "getFood",value = "/getFood")
public class getFood extends HttpServlet {

    User user;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user = AuthUtil.AuthSession(request);
        String json = GsonUtil.GsonString(DbManager.getFood(JdbcUtil.localhost));
        Code code = new Code(HttpConfig.REQUEST_SUCCESS,"get food success",json);
        response.getWriter().append(GsonUtil.GsonString(code));
    }
}
