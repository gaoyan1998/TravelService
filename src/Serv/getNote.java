package Serv;

import Util.HttpConfig;
import DB.DbManager;
import Model.Code;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
@WebServlet("/getNote")
public class getNote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Code code;
        User user = AuthUtil.Auth(request);
        code = new Code(HttpConfig.REQUEST_SUCCESS, "Return note successful", GsonUtil.GsonString(DbManager.getNote(user)));
        response.getWriter().append(GsonUtil.GsonString(code));
    }
}
