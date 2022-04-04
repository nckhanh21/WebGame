/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Company;
import java.sql.Connection;
import java.sql.Date;
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
public class DAOCompany {
    DBConnection dbConn;
    Connection conn;

    public DAOCompany(DBConnection dbConn) {
        this.dbConn = dbConn;
        conn = dbConn.getConnection();
    }
    
    public int insertCompany(Company obj) {
        int n = 0;
        String sql = "INSERT INTO Company(coName, foundDate, description, logo, coAddress, coPhone, coMail, status) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, obj.getCoName());
            pre.setDate(2, obj.getFoundDate());
            pre.setString(3, obj.getDescription());
            pre.setString(4, obj.getLogo());
            pre.setString(5, obj.getCoAddress());
            pre.setString(6, obj.getCoPhone());
            pre.setString(7, obj.getCoMail());
            pre.setInt(8, obj.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int updateInfoCompany(Company obj) {
        int n = 0;
        String sql = "UPDATE COmpany SET coName = ?, foundDate = ?, description = ?, logo = ?, coAddress = ?, coPhone=?, coMail=? WHERE coId = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, obj.getCoName());
            pre.setDate(2, obj.getFoundDate());
            pre.setString(3, obj.getDescription());
            pre.setString(4, obj.getLogo());
            pre.setString(5, obj.getCoAddress());
            pre.setString(6, obj.getCoPhone());
            pre.setString(7, obj.getCoMail());
            pre.setInt(8, obj.getCoId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int changeStatus(String coName, int status) {
        int n = 0;
        String sql = "UPDATE Company SET status = "
                + (status == 1 ? 1 : 0) + " WHERE coName = '" + coName + "'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int changeStatus(int coId, int status) {
        int n = 0;
        String sql = "UPDATE Company SET status = "
                + (status == 1 ? 1 : 0) + " WHERE coId = '" + coId + "'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int removeCompany(int id) {
        int n = 0;
        String sql = "SELECT * FROM Company AS a JOIN Game as b on a.coId = b.coId where a.coId = " + id;
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                changeStatus(rs.getString("coName"), 1);
            } else {
                String sqlDelete = "DELETE From Company WHERE coId = " + id;
                Statement state = conn.createStatement();
                n = state.executeUpdate(sqlDelete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public boolean checkExistCompanyName(String coName) {
        String sql = "SELECT * FROM Company WHERE coname = '" + coName + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Company> getAllCompany() {
        ArrayList<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM Company WHERE status = 1";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                int coId = rs.getInt("coId");
                String scoName = rs.getString("coName");
                Date foundDate = rs.getDate("foundDate");
                String descripton = rs.getString("description");
                String logo = rs.getString("logo");
                String coAddress = rs.getString("coAddress");
                String coPhone = rs.getString("coPhone");
                String coMail = rs.getString("coMail");
                Company s = new Company(coId, scoName, foundDate, descripton, logo, coAddress, coPhone, coMail);
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Company> getTrueCompany() {
        ArrayList<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM Company";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                int coId = rs.getInt("coId");
                String scoName = rs.getString("coName");
                Date foundDate = rs.getDate("foundDate");
                String descripton = rs.getString("description");
                String logo = rs.getString("logo");
                String coAddress = rs.getString("coAddress");
                String coPhone = rs.getString("coPhone");
                String coMail = rs.getString("coMail");
                Company s = new Company(coId, scoName, foundDate, descripton, logo, coAddress, coPhone, coMail);
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Company getCompany(int coId) {
        String sql = "SELECT * FROM Company WHERE coId = '" + coId + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Company com = new Company();
                com.setCoId(rs.getInt("coId"));
                com.setCoName(rs.getString("coName"));
                com.setFoundDate(rs.getDate("foundDate"));
                com.setDescription(rs.getString("description"));
                com.setLogo(rs.getString("logo"));
                com.setCoAddress(rs.getString("coAddress"));
                com.setCoPhone(rs.getString("coPhone"));
                com.setCoMail(rs.getString("coMail"));
                com.setStatus(rs.getInt("status"));
                return com;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
