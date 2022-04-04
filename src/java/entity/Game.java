/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class Game {
    private int gid;
    private String title;
    private int coID;
    private String description;
    private String version;
    private int rating;
    private Date releaseDate;
    private double price;
    private String state;
    private int status;

    public Game(int gid, String title, int coID, String description, String version,int rating, Date releaseDate, double price, String state, int status) {
        this.gid = gid;
        this.title = title;
        this.coID = coID;
        this.description = description;
        this.version = version;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.price = price;
        this.state = state;
        this.status = status;
    }
    
    public Game(int gid, String title, int coID, String description, String version,int rating, Date releaseDate, double price, String state) {
        this.gid = gid;
        this.title = title;
        this.coID = coID;
        this.description = description;
        this.version = version;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.price = price;
        this.state = state;
    }
    

    public Game() {
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoID() {
        return coID;
    }

    public void setCoID(int coID) {
        this.coID = coID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString(){
        return "GameID: "+gid+"; Title: "+title+"; Company ID: "+coID+
                "<br>Description: "+description+"<br>Version: "+version+
                "; Rating:"+rating+"; Release Date: "+releaseDate+
                "<br>Price: "+price+"; State: "+state+"; Status: "+status;
    }
}
