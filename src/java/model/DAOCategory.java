/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.*;
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
public class DAOCategory {

    DBConnection dbConn;
    Connection conn;
    String sql;

    public DAOCategory(DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }

    public void insertCategory(Category cate) {
        sql = "Insert into Category(caName,description,status) values (?,?,?)";
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cate.getCaName());
            ps.setString(2, cate.getDescript());
            ps.setInt(3, cate.getStatus());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateCategory(Category cate) {
        sql = "update Category set caName=?, description=?, status=? where caId=?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cate.getCaName());
            ps.setString(2, cate.getDescript());
            ps.setInt(3, cate.getStatus());
            ps.setInt(4, cate.getCaId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Category> getAllCategories() {
        sql = "select * from Category WHERE status=1";
        ArrayList<Category> list = new ArrayList<>();
        Category x = null;
        int caId;
        String caName;
        String descript;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("caId");
                caName = rs.getString("caName");
                descript = rs.getString("description");
                status = rs.getInt("status");
                x = new Category(caId, caName, descript, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Category> getTrueCategories() {
        sql = "select * from Category";
        ArrayList<Category> list = new ArrayList<>();
        Category x = null;
        int caId;
        String caName;
        String descript;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("caId");
                caName = rs.getString("caName");
                descript = rs.getString("description");
                status = rs.getInt("status");
                x = new Category(caId, caName, descript, status);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Category getCategoryById(int fcaId) {
        sql = "select * from Category where caId=" + fcaId;
        Category x = null;
        int caId;
        String caName;
        String descript;
        int status;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caId = rs.getInt("caId");
                caName = rs.getString("caName");
                descript = rs.getString("description");
                status = rs.getInt("status");
                x = new Category(caId, caName, descript, status);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
    
    public int changeStatus(int id, int status) {
        int n = 0;
        String sql = "update Category set status = " + (status == 1 ? 1 : 0) + " where caId = '" + id + "'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int removeCategory(int id) {
        int n = 0;
        String sql = "Select * from Category as a join Game_Category as b on a.caId = b.caId where a.caId = '" + id + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                changeStatus(rs.getInt("caId"), 1);
            } else {
                String sqlDelete = "delete from Category where caId = '" + id + "'";
                Statement state = conn.createStatement();
                n = state.executeUpdate(sqlDelete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public boolean checkExistCategoryName(String caName) {
        String sql = "SELECT * FROM Category WHERE caName = '" + caName + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
        public ArrayList<Category> getCategory(int gId) {
        sql = "select caid from Game_Category where gId ="+gId;
        ArrayList<Category> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int caid = rs.getInt("caid");
                Category x= getCategoryById(caid);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
