/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Game_Platform;
import java.sql.Connection;
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
public class DAOGame_Platform {
    DBConnection dbConn;
    Connection conn;
    String sql;

    public DAOGame_Platform (DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }

    public void insertGame_Platform(Game_Platform game) {
        sql = "insert into Game_Platform(plId,gId,status) values(?,?,1)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, game.getPlId());
            ps.setInt(2, game.getgId());
            ps.setInt(3, game.getStatus());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Platform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Game_Platform> getAllGame_Platforms() {
        sql = "select * from Game_Category where status=1";
        ArrayList<Game_Platform> list = new ArrayList<>();
        Game_Platform x = null;
        int caId;
        int gId;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("plId");
                gId = rs.getInt("gId");
                status = rs.getInt("status");
                x = new Game_Platform(caId, gId, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Platform.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Game_Platform> getTrueGame_Platforms() {
        sql = "select * from Game_Category where status=1";
        ArrayList<Game_Platform> list = new ArrayList<>();
        Game_Platform x = null;
        int caId;
        int gId;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("plId");
                gId = rs.getInt("gId");
                status = rs.getInt("status");
                x = new Game_Platform(caId, gId, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame_Platform.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
