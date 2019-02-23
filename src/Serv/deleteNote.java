package Serv;

import Config.HttpConfig;
import DB.DbManager;
import Model.Code;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;
import Util.Http;

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
@WebServlet("/deleteNote")
public class deleteNote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = AuthUtil.Auth(request);
        String id = request.getParameter("id");
        Code code;
        Boolean flage = DbManager.deleteNote(id);
        if (flage) {
            code = new Code(HttpConfig.REQUEST_SUCCESS, "Delete successful", "");
        } else {
            code = new Code(HttpConfig.SERVER_ERR, "Delete Failed", "");
        }
        response.getWriter().append(GsonUtil.GsonString(code));
    }
}
