/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.OrderDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DAOOrder_Detail {

    DBConnection dbConn;
    Connection conn;

    public DAOOrder_Detail(DBConnection dbConn) {
        this.dbConn = dbConn;
        conn = dbConn.getConnection();
    }

    public int insertOrderDetail(OrderDetail obj) {
        int n = 0;
        String sql = "Insert into Order_Detail(oId, gId, price, status)"
                + " values (?,?,?,1)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, obj.getoId());
            pre.setInt(2, obj.getgId());
            pre.setDouble(3, obj.getPrice());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeOrderDetail(String orderId, String gId) {
        int n = 0;
        String sqlDelete = "delete from Order_Detail where oId = '" + orderId + "' and gId = '" + gId + "'";
        Statement stm;
        try {
            stm = conn.createStatement();
            n = stm.executeUpdate(sqlDelete);
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateOrderDetail(OrderDetail obj) {
        int n = 0;
        String sql = "update Order_Detail set price=? where oId=? and gId=?";
        try {
            PreparedStatement preStm = conn.prepareStatement(sql);
            preStm.setDouble(1, obj.getPrice());
            preStm.setInt(2, obj.getoId());
            preStm.setInt(3, obj.getgId());
            n = preStm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder_Detail.class.getName()).log(Level.SEVERE, null, ex);

        }
        return n;
    }

    public ArrayList<OrderDetail> getByOrderId(int id) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        String sql = "select a.oId, a.gId, b.title, a.price from Order_Detail a inner join Game b on a.gId = b.gId where a.gId = " + id ;
        ResultSet rs = dbConn.getData(sql);
        try {
            while(rs.next()){
                OrderDetail od = new OrderDetail();
                od.setoId(rs.getInt("oId"));
                od.setgId(rs.getInt("gId"));
                od.setPrice(rs.getDouble("price"));
                list.add(od);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<OrderDetail> getByOrdId(int oId) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        String sql = "select * from [Order_Detail] where oId = " + oId ;
        ResultSet rs = dbConn.getData(sql);
        try {
            while(rs.next()){
                OrderDetail od = new OrderDetail();
                od.setoId(rs.getInt("oId"));
                od.setgId(rs.getInt("gId"));
                od.setPrice(rs.getDouble("price"));
                list.add(od);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public OrderDetail getTopUp(int oId) {
        String sql = "SELECT * FROM [Order_Detail] where oId = " + oId ;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setoId(rs.getInt("oId"));
                od.setgId(rs.getInt("gId"));
                od.setPrice(rs.getDouble("price"));
                od.setStatus(rs.getInt("status"));
                return od;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public HashMap<Integer, ArrayList<OrderDetail>> getListOrderDetail() {
        HashMap<Integer, ArrayList<OrderDetail>> list = new HashMap<>();
        DAOOrder daoOd = new DAOOrder(dbConn);
        int number = daoOd.getMaxOrderId();
        for (int i = 1; i <= number; i++) {
            list.put(i, getByOrderId(i));
        }
        return list;
    }
    public static void main(String[] args) {
        DAOOrder_Detail dao = new DAOOrder_Detail(new DBConnection());
        OrderDetail od = new OrderDetail(4, 4, 100, 1);
        dao.insertOrderDetail(od);
    }
}
