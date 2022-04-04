/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Game;
import entity.Library;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duong
 */
public class DAOLibrary {

    DBConnection dbConn;
    Connection conn;

    public DAOLibrary(DBConnection dbConn) {
        this.dbConn = dbConn;
        conn = dbConn.getConnection();
    }

    public int insertLibrary(Library obj) {
        int n = 0;
        String sql = "INSERT INTO Library(uId, gId, [type],  status) values (?,?,'owned',1)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, obj.getuId());
            pre.setInt(2, obj.getgId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public ArrayList<Game> getGameByUIdAndStatus(int uId, int status) {
        ArrayList<Game> list = new ArrayList<>();
        DAOGame daoGame = new DAOGame(dbConn);
        Game g = new Game();
        String s = " ";
        if (status != -1) {
            s = " and status = " + status + " ";
        }
        String sql = "select * from Library where uId = " + uId + "and [type] = 'owned'" + s + "order by uId desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                g = daoGame.getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int insertWishLish(Library obj) {
        int n = 0;
        String sql = "INSERT INTO Library(uId, gId, [type],  status) values (?,?,'favour',1)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, obj.getuId());
            pre.setInt(2, obj.getgId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public ArrayList<Game> getGameInWishList(int uId, int status) {
        ArrayList<Game> list = new ArrayList<>();
        DAOGame daoGame = new DAOGame(dbConn);
        Game g = new Game();
        String s = " ";
        if (status != -1) {
            s = " and status = " + status + " ";
        }
        String sql = "select * from Library where uId = " + uId + "and [type] = 'favour'" + s + "order by uId desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                g = daoGame.getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int deleteWishlist(Library list) {
        int n = 0;
        String sqlDelete = "delete from Library where uId = '" + list.getuId() + "' and gId = '" + list.getgId() + "' and [type] = 'favour'";
        Statement stm;
        try {
            stm = conn.createStatement();
            n = stm.executeUpdate(sqlDelete);
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int changeStatus(int uId, int gid, int status) {
        int n = 0;
        String sql = "update Library set status = " + (status == 1 ? 1 : 0) + " where gId = '" + gid + "' and uId = " + uId;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int deleteOwnedInWishlist(Library list) {
        int n = 0;
        String sql = "select * from Library where uId = " + list.getuId() + " and gId = '" + list.getgId() + "'and [type] = 'owned' and status = 1";
        ResultSet rs = dbConn.getData(sql);
        Library lib = null;
        try {
            if (rs.next()) {
                lib = new Library(rs.getInt("uId"), rs.getInt("gId"));
                String sqlDelete = "delete from Library where uId = '" + lib.getuId() + "' and gId = '" + lib.getgId() + "' and [type] = 'favour'";
                Statement state = conn.createStatement();
                n = state.executeUpdate(sqlDelete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public ArrayList<User> getOwnedByGame(int gId) {
        ArrayList<User> userList = new ArrayList<>();
        DAOUser daoUser = new DAOUser(dbConn);
        String sql = "SELECT * FROM Library WHERE gId = " + gId + " and [type] = 'owned'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while(rs.next()) {
                User u = daoUser.getUserById(rs.getInt("uId"));
                userList.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    
    public ArrayList<User> getWishlistByGame(int gId) {
        ArrayList<User> userList = new ArrayList<>();
        DAOUser daoUser = new DAOUser(dbConn);
        String sql = "SELECT * FROM Library WHERE gId = " + gId + " and [type] = 'favour'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while(rs.next()) {
                User u = daoUser.getUserById(rs.getInt("uId"));
                userList.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    
//    public static void main(String[] args) throws SQLException {
//        DAOLibrary dao = new DAOLibrary(new DBConnection());
//        System.out.println(dao.deleteOwnedInWishlist(new Library(8, 17)));
//    }
}
