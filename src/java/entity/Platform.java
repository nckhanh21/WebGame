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
public class Platform {
    private int plId;
    private String plname;
    private String description;
    private int status;

    public Platform() {
    }

    public Platform(int plId, String plname, String description, int status) {
        this.plId = plId;
        this.plname = plname;
        this.description = description;
        this.status = status;
    }

    public Platform(int plId, String plname, String description) {
        this.plId = plId;
        this.plname = plname;
        this.description = description;
    }
    
    public int getPlId() {
        return plId;
    }

    public void setPlId(int plId) {
        this.plId = plId;
    }

    public String getPlname() {
        return plname;
    }

    public void setPlname(String plname) {
        this.plname = plname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
