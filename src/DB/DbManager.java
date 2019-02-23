package DB;

import Model.Note;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static User getUser(String name) {
        User user = null;
        String sql = "SELECT * FROM travelapp.user where user_name = ?";
        try {
            ResultSet resultSet = JdbcUtil.getResult(sql, name);
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
        return user;
    }

    public static boolean addUser(String name, String pwd, String email) {
        String sql = "INSERT INTO `travelapp`.`user` (`user_name`, `user_pwd`, `user_email`) VALUES ('?', '?', '?');";
        try {
            JdbcUtil.getResult(sql, name, pwd, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JdbcUtil.releaseConn();
        }
        return true;
    }

    public static List<Note> getNote(User user) {
        String sql = "SELECT * FROM travelapp.note where user_id = ?";
        List<Note> list = null;
        try {
            ResultSet resultSet = JdbcUtil.getResult(sql, user.getId() + "");
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
        return list;
    }

    public static boolean addNote(Note note) {
        String sql = "INSERT INTO `travelapp`.`note` (`user_id`, `content`, `pic_path`, `voice_path`) VALUES ('?', '?', '?', '?')";
        try {
            ResultSet resultSet = JdbcUtil.getResult(sql, note.getUserId() + "", note.getContent(), note.getPicPath(), note.getVoicePath());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JdbcUtil.releaseConn();
        }
        return true;
    }

    public static boolean deleteNote(String id) {
        String sql = "delete from travelapp.note where _id = ?";
        try {
            JdbcUtil.getResult(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JdbcUtil.releaseConn();
        }
        return true;
    }
}
