/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Admin
 */
public class Comment {
    private int cId;
    private int gId;
    private int uId;
    private String content;
    private int rating;
    private int status;

    public Comment() {
    }

    public Comment(int cId, int gId, int uId, String content, int rating, int status) {
        this.cId = cId;
        this.gId = gId;
        this.uId = uId;
        this.content = content;
        this.rating = rating;
        this.status = status;
    }

    public int getcId() {
        return cId;
    }

    public int getStatus() {
        return status;
    }

    public int getgId() {
        return gId;
    }

    public int getuId() {
        return uId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
}
