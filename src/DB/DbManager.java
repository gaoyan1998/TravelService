package DB;

import Model.Note;
import Model.Phone;
import Model.User;

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
}
