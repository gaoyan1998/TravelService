package Serv;

import Control.BaseServlet;
import DB.DbManager;
import Model.Addr;
import Util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
@WebServlet("/AddrManager")
public class AddrManager extends BaseServlet {

    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Addr> list = DbManager.getAddr();
        if (list != null)
            setResult(1, GsonUtil.GsonString(list));
        else
            setResult(-1, "");
    }

    @Override
    public void sel() throws IOException {

    }

    @Override
    public void del() {

    }

    @Override
    public void add() {

    }

    @Override
    public void update() {

    }
}
