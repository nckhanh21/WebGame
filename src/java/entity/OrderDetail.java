/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Duong
 */
public class OrderDetail {
    private int oId;
    private int gId;
    private double price;
    private int status;

    public OrderDetail() {
    }

    public OrderDetail(int oId, int gId, double price, int status) {
        this.oId = oId;
        this.gId = gId;
        this.price = price;
        this.status = status;
    }

    public OrderDetail(int oId, int gId, double price) {
        this.oId = oId;
        this.gId = gId;
        this.price = price;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order_Detail{" + "oId=" + oId + ", gId=" + gId + ", price=" + price + ", status=" + status + '}';
    }
    
}
