package Serv;

import Util.HttpConfig;
import Control.SendEmailCode;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 */
@WebServlet("/register")
public class Register extends HttpServlet {
    private User user;
    private Code code;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = Http.getStringFromReq(request);

        user = GsonUtil.GsonToBean(json, User.class);

        if (user.getName().equals("") || user.getPwd().equals("") ||
                user.getEmail().equals("") || AuthUtil.AuthEmail(user.getEmail())) {

            code = new Code(HttpConfig.PARMATER_ERR, "PARMATER ERR", "");

        } else if (DbManager.getUser(user.getName()) != null) {
            code = new Code(HttpConfig.USER_ALREDAY_EXITS, "USER ALREDAY EXITS", "");
        } else if (user.getId() == 0) {
            sendEmail(request);
        } else {
            register(request);
        }
        response.getWriter().append(GsonUtil.GsonString(code));
    }

    private void register(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        int verif = (int) session.getAttribute("verif");
        if (user.getName().equals(name) && (user.getId() + "").equals(verif+"")){
            DbManager.addUser(user.getName(), user.getPwd(), user.getEmail());
            code = new Code(HttpConfig.REQUEST_SUCCESS, "REGISTER SUCCESSFUL", "");
        }else {
            code = new Code(HttpConfig.REQUEST_FORBIDEN, "VERIFY ERR", "");
        }
    }

    private void sendEmail(HttpServletRequest request) {
        int verif = (int) (Math.random() * 10000 + 10000);
        SendEmailCode sendEmailCode = new SendEmailCode(user.getEmail(),verif + "");
        sendEmailCode.start();
        code = new Code(HttpConfig.REQUEST_SUCCESS, "CODE IS SEND", "");
        HttpSession session = request.getSession();
        session.setAttribute("name", user.getName());
        session.setAttribute("verif", verif);
    }

}
