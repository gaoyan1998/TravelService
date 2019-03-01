package Serv;

import Model.User;
import Util.AuthUtil;
import Util.HttpConfig;
import DB.DbManager;
import Model.Code;
import Model.Note;
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
@WebServlet("/addNote")
public class addNote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Code code;
        String json = Http.getStringFromReq(request);
        Note note = GsonUtil.GsonToBean(json, Note.class);
        User user = AuthUtil.AuthSession(request);
        note.setUserId(user.getId());
        int flage = DbManager.addNote(note);
        if (flage>0) {
            code = new Code(HttpConfig.REQUEST_SUCCESS, "Add Successful", "");
        } else {
            code = new Code(HttpConfig.SERVER_ERR, "Add failed   server err", "");
        }
        response.getWriter().append(GsonUtil.GsonString(code));
    }
}
