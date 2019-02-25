package DB;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static User getUser(String name) {
        User user = null;
        String sql = "SELECT * FROM travelapp.user where user_name = ?";
        try {
            ResultSet resultSet = JdbcUtil.query(sql, name);
            //如果返回结果为空，直接返回null；
            if (!resultSet.next()) {
                user = null;
            } else {
                user = new User();
                user.setId(resultSet.getInt("_id"));
                user.setName(name);
                user.setPwd(resultSet.getString("user_pwd"));
                user.setEmail(resultSet.getString("user_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        System.out.println("DbManager:获取用户信息");
        return user;
    }

    public static int addUser(String name, String pwd, String email) {
        String sql = "INSERT INTO `travelapp`.`user` (`user_name`, `user_pwd`, `user_email`) VALUES (?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, name, pwd, email);
        System.out.println("DbManager:添加用户");
        return flage;
    }

    public static List<Note> getNote(User user) {
        String sql = "SELECT * FROM travelapp.note where user_id = ?";
        List<Note> list = null;
        try {
            ResultSet resultSet = JdbcUtil.query(sql, user.getId() + "");
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Note note = new Note();
                    note.setId(resultSet.getInt("_id"));
                    note.setContent(resultSet.getString("content"));
                    note.setUserId(resultSet.getInt("user_id"));
                    note.setPicPath(resultSet.getString("pic_path"));
                    note.setVoicePath(resultSet.getString("voice_path"));
                    list.add(note);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        System.out.println("DbManager:返回用户日记列表，用户名：" + user.getName() + " 数量：" + list.size());
        return list;
    }

    public static int addNote(Note note) {
        String sql = "INSERT INTO `travelapp`.`note` (`user_id`, `content`, `pic_path`, `voice_path`) VALUES (?, ?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, note.getUserId() + "", note.getContent(), note.getPicPath(), note.getVoicePath());
        System.out.println("DbManager:添加日记");
        return flage;
    }

    public static int deleteNote(String id) {
        String sql = "delete from travelapp.note where _id = ?";
        int flage = JdbcUtil.executelSql(sql, id);
        ;
        System.out.println("DbManager:删除用户日记");
        return flage;
    }

    public static int addPhone(Phone phone) {
        String sql = "INSERT INTO `travelapp`.`phone` (`phone_name`, `phone_number`, `user_id`) VALUES (?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, phone.getName(), phone.getNumber(), phone.getUserId() + "");
        System.out.println("DbManager:添加手机号");
        return flage;
    }

    public static int deletePhone(String id,int userId) {
        String sql = "delete  FROM travelapp.phone where _id = ? and user_id = ?";
        int flage = JdbcUtil.executelSql(sql, id + "",userId+"");
        System.out.println("DbManager:删除手机号");
        return flage;
    }

    public static List<Phone> getPhone(int id) {

        List<Phone> list = null;

        String sql = "SELECT * FROM travelapp.phone where user_id = ?";
        try {
            ResultSet resultSet = JdbcUtil.query(sql, id + "");
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Phone phone = new Phone();
                    phone.setId(resultSet.getInt("_id"));
                    phone.setName(resultSet.getString("phone_name"));
                    phone.setNumber(resultSet.getString("phone_number"));
                    phone.setUserId(resultSet.getInt("user_id"));
                    list.add(phone);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("DbManager:查询手机号");
        return list;
    }
    public static List<Food> getFood(String url){

        List<Food> list = null;
        String sql = "SELECT * FROM travelapp.food";
        try {
            ResultSet resultSet = JdbcUtil.query(sql);
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Food food = new Food();
                    food.setId(resultSet.getInt("_id"));
                    food.setImagePath(url+"/"+resultSet.getString("img"));
                    food.setName(resultSet.getString("name"));
                    food.setText(resultSet.getString("text"));
                    list.add(food);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.releaseConn();
        }
        return list;
    }
    public static int addFood(Food food){
        String sql = "INSERT INTO `travelapp`.`food` (`name`, `text`, `img`) VALUES (?,?,?)";
        int flage = JdbcUtil.executelSql(sql,food.getName(),food.getText(),food.getImagePath());
        return flage;
    }
    public static int upDateFood(Food food){
        String sql = "UPDATE `travelapp`.`food` SET `name` = ?, `text` = ?, `img` = ? WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql,food.getName(),food.getText(),food.getImagePath(),food.getId()+"");
        return flage;
    }
    public static int deleteFood(String id){
        String sql = "DELETE FROM `travelapp`.`food` WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql,id);
        return flage;
    }

    public static int addSpot(Spot spot){
        String sql = "INSERT INTO `travelapp`.`spot` (`name`, `text`, `img`) VALUES (?,?,?)";
        int flage = JdbcUtil.executelSql(sql,spot.getName(),spot.getText(),spot.getImagePath());
        return flage;
    }
    public static int upDateSpot(Spot spot){
        String sql = "UPDATE `travelapp`.`food` SET `name` = ?, `text` = ?, `img` = ? WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql,spot.getName(),spot.getText(),spot.getImagePath(),spot.getId()+"");
        return flage;
    }
    public static int deleteSpot(String id){
        String sql = "DELETE FROM `travelapp`.`spot` WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql,id);
        return flage;
    }
    public static List<Food> getSpot(String url){

        List<Food> list = null;
        String sql = "SELECT * FROM travelapp.spot";
        try {
            ResultSet resultSet = JdbcUtil.query(sql);
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Spot spot = new Spot();
                    spot.setId(resultSet.getInt("_id"));
                    spot.setImagePath(url+"/"+resultSet.getString("img"));
                    spot.setName(resultSet.getString("name"));
                    spot.setText(resultSet.getString("text"));
                    list.add(spot);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.releaseConn();
        }
        return list;
    }
}
