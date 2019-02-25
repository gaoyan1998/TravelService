package Serv;

import Control.BaseServlet;
import DB.DbManager;
import DB.JdbcUtil;
import Model.Code;
import Model.Food;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;
import Util.Http;
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
@WebServlet(name = "FoodManager",value = "/FoodManager")
public class FoodManager extends BaseServlet {

    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    public void sel() throws IOException {
        String json = GsonUtil.GsonString(DbManager.getFood(JdbcUtil.localhost));
        Code code = new Code(HttpConfig.REQUEST_SUCCESS,"success",json);
        setResult(code);
    }

    @Override
    public void del() {
        String id = getRequest().getParameter("id");
        setResult(DbManager.deleteFood(id),"");
    }

    @Override
    public void add() {
        String json = Http.getStringFromReq(getRequest());
        Food food = GsonUtil.GsonToBean(json,Food.class);
        setResult(DbManager.addFood(food),"");
    }

    @Override
    public void update() {
        String json = Http.getStringFromReq(getRequest());
        Food food = GsonUtil.GsonToBean(json,Food.class);
        setResult(DbManager.upDateFood(food),"");
    }
}
