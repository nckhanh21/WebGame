/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Order;
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
public class DAOOrder {

    DBConnection dbConn;
    Connection conn;

    public DAOOrder(DBConnection dbConn) {
        this.dbConn = dbConn;
        conn = dbConn.getConnection();
    }

    public int insertOrder(Order obj) {
        int n = 0;
        String sql = "Insert into [Order] (uId, orderDate, type, total, status)"
                + " values (?,GETDATE(),?,?,1)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, obj.getuId());
            pre.setString(2, obj.getType());
            pre.setDouble(3, obj.getTotal());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int changeStatus(String orderId) {
        int n = 0;
        String sql = "UPDATE [Order] SET status = 1 WHERE oid = '" + orderId + "'";

        Statement state;
        try {
            state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateOrder(Order obj) {
        int n = 0;
        String sql = "update [Order] set uId=?, orderDate=?, type=?, total=?, status=? where oId=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, obj.getuId());
            pre.setTimestamp(2, obj.getOrderDate());
            pre.setString(3, obj.getType());
            pre.setDouble(4, obj.getTotal());
            pre.setInt(5, obj.getStatus());
            pre.setInt(6, obj.getoId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeOrder(String orderId) {
        int n = 0;
        String sql = "Select * from [Order] as a join Order_Detail as b on a.oId = b.oId where a.oId = '" + orderId + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                String s = rs.getString("oId");
                return s == null ? 0 : Integer.parseInt(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int getMaxOrderId() {
        String sql = "select MAX(oId) as orderId from [Order]";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                String s = rs.getString("orderId");
                return s == null ? 0 : Integer.parseInt(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "select * from [Order] where status = 1  order by oId desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Order o = new Order();
                o.setoId(rs.getInt("oId"));
                o.setuId(rs.getInt("uId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setType(rs.getString("type"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getInt("status"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Order> getOrderByUIdAndStatus(int uId, int status) {
        ArrayList<Order> list = new ArrayList<>();
        String s = " ";
        if (status != -1) {
            s = " and status = " + status + " ";
        }
        String sql = "select * from [Order] where uId = " + uId + s + "order by uId desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Order o = new Order();
                o.setoId(rs.getInt("oId"));
                o.setuId(rs.getInt("uId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setType(rs.getString("type"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getInt("status"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Order getOrderByOId(int oId) {
        Order o = new Order();
        String sql = "select * from [Order] where status = 1 and oId="+oId;
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                o.setoId(rs.getInt("oId"));
                o.setuId(rs.getInt("uId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setType(rs.getString("type"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getInt("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    
    public ArrayList<Order> getOrdersByUId(int uId) {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "select * from [Order] where status = 1 and uId="+uId;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Order o = new Order();
                o.setoId(rs.getInt("oId"));
                o.setuId(rs.getInt("uId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setType(rs.getString("type"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getInt("status"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int getLatestOrderByUseridAndTotal(int uID, double total){
        int newestOrderId = -1;
        String sql = "select top 1 * from [Order] where status = 1 and uId= ? and total = ? order by oid desc";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, uID);
            ps.setDouble(2, total);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newestOrderId = rs.getInt("oId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newestOrderId;
    } 
    // type = 'buygame'
    public ArrayList<Order> getAllOrderByDate() {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "select * from [Order] where status = 1 order by orderDate desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Order o = new Order();
                o.setoId(rs.getInt("oId"));
                o.setuId(rs.getInt("uId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setType(rs.getString("type"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getInt("status"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
