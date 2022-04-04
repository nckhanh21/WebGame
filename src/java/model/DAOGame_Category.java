/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DAOGame_Category {

    DBConnection dbConn;
    Connection conn;
    String sql;

    public DAOGame_Category(DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }

    public void insertGame_Category(Game_Category gameCategory) {
        sql = "insert into Game_Category(caId,gId,status) values(?,?,1)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gameCategory.getCaId());
            ps.setInt(2, gameCategory.getgId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Game_Category> getAllGame_Categories() {
        sql = "select * from Game_Category where status=1";
        ArrayList<Game_Category> list = new ArrayList<>();
        Game_Category x = null;
        int caId;
        int gId;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("caId");
                gId = rs.getInt("gId");
                status = rs.getInt("status");
                x = new Game_Category(caId, gId, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<Game_Category> getTrueGame_Categories() {
        sql = "select * from Game_Category";
        ArrayList<Game_Category> list = new ArrayList<>();
        Game_Category x = null;
        int caId;
        int gId;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("caId");
                gId = rs.getInt("gId");
                status = rs.getInt("status");
                x = new Game_Category(caId, gId, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
