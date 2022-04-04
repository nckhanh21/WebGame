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
public class DAOComment {
    DBConnection dbConn;
    Connection conn;
    String sql;
    
    public DAOComment(DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }
    
    public int insertComment(Comment obj){
        int n = 0;
        sql = "insert into Comment(gId,uId,content,rating,status) values (?,?,?,?,1)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getgId());
            ps.setInt(2, obj.getuId());
            ps.setString(3, obj.getContent());
            ps.setInt(4, obj.getRating());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public ArrayList<Comment> getCommentsByGameId(int gId){
        ArrayList<Comment> comments = new ArrayList<>();
        
        sql = "select * from Comment where gId  = ? order by cId desc"  ;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment x = new Comment();
                x.setcId(rs.getInt("cId"));
                x.setgId(rs.getInt("gId"));
                x.setuId(rs.getInt("uId"));
                x.setContent(rs.getString("content"));
                x.setRating(rs.getInt("rating"));
                x.setStatus(rs.getInt("status"));
                comments.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comments;
    }
    
    public boolean checkExistComment(int gId,int uId){
        ArrayList<Comment> comments = new ArrayList<>();
        boolean check = false;
        sql = "select * from Comment where gId  = ? and uId = ? order by cId desc"  ;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gId);
            ps.setInt(2, uId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOComment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
     
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        DAOComment dAOComment = new DAOComment(db);
//        Comment x = new Comment(-1, 2, 2, "he", 5, 1);
        Comment y = new Comment(-1, 2, 2, "hi", 5, 1);
//        dAOComment.insertComment(y);
        ArrayList<Comment> comments = dAOComment.getCommentsByGameId(2);
        System.out.println(comments.size());
//        for (Comment comment : comments) {
//            System.out.println(comment.getcId() + "  " +  comment.getContent());
//        }
//        if(dAOComment.checkExistComment(2, 2)){
//            System.out.println("fucjk");
//        }
    }
}
