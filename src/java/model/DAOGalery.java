/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Galery;
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
 * @author Admin
 */
public class DAOGalery {

    DBConnection dbConn;
    Connection conn;
    String sql;

    public DAOGalery(DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }

    public int insertGalery(Galery gal) {
        sql = "insert into Galery(gId,link,type,status) values(?,?,?,?)";
        int n=0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gal.getgId());
            ps.setString(2, gal.getLink());
            ps.setString(3, gal.getType());
            ps.setInt(4, gal.getStatus());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int updateGalery(Galery oldGal, Galery newGal) {
        sql = "update Galery set link=?, type=?, status=? where gId=? and link=?";
        int n = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newGal.getLink());
            ps.setString(2, newGal.getType());
            ps.setInt(3, newGal.getStatus());
            ps.setInt(4, oldGal.getgId());
            ps.setString(5, oldGal.getLink());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public ArrayList<Galery> getAllGaleries() {
        sql = "select * from Galery where status = 1";
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Galery> getTrueGaleries() {
        sql = "select * from Galery";
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Galery> getGaleryById(int fgId) {
        sql = "select * from Galery where gId = " + fgId;       
        ArrayList<Galery> x = new ArrayList<>();
        int gId;
        String link;
        String type;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                Galery a = new Galery(gId, link, type, status);
                x.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
    
    public ArrayList<Galery> getOneGaleryByType(ArrayList<Galery> ga, String type) {
        ArrayList<Galery> galery = new ArrayList<>();
        for (Galery galery1 : ga) {
            if (galery1.getType().equalsIgnoreCase(type)) {
                galery.add(galery1);
            }
        }
        return galery;
    }
    
    public ArrayList<Galery> getGaleryByTypeId(int xgId, String xtype) {
        sql = "select * from Galery WHERE gId = ? and type = ?";
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, xgId);
            ps.setString(2, xtype);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Galery> getTrailer() {
        sql = "select * from Galery WHERE type = 'vid-trailer' and status=1";
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Galery> getVideo() {
        sql = "select * from Galery WHERE type like 'vid%' and status=1";
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int changeStatus(int id, int status) {
        int n = 0;
        String sql = "update Galery set status = " + (status == 1 ? 1 : 0) + " where gId = '" + id + "'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeGame(int id) {
        int n = 0;
        String sql = "Select * from Game as a join Galery as b on a.gId = b.gId where b.gId = '" + id + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                changeStatus(rs.getInt("gId"), 1);
            } else {
                String sqlDelete = "delete from Galery where gId = '" + id + "'";
                Statement state = conn.createStatement();
                n = state.executeUpdate(sqlDelete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public boolean checkExistGaleryInfo(int gId,String link) {
        String sql = "SELECT * FROM Galery WHERE link = '" + link + "' and gId = "+ gId;
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Galery> getFullGameGalery(int gid ){
        String sql = "SELECT * FROM Galery WHERE status=1 and gid = ? and not (type = 'img-po') order by type desc";
        PreparedStatement ps;
        ArrayList<Galery> list = new ArrayList<>();
        Galery x = null;
        int gId;
        String link;
        String type;
        int status;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                gId = rs.getInt("gId");
                link = rs.getString("link");
                type = rs.getString("type");
                status = rs.getInt("status");
                x = new Galery(gId, link, type, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
//    public static void main(String[] args) {
//        DAOGalery dao = new DAOGalery(new DBConnection());
//        System.out.println(dao.updateGalery(
//                new Galery(22, "randomfileagain2.jpg", "vid-gp", 1), 
//                new Galery(22, "test1.jpg", "vid-trailer", 1)));
//    }
}
