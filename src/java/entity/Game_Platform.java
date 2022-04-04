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
public class Game_Platform {
    private int plId;
    private int gId;
    private int status;

    public Game_Platform() {
    }

    public Game_Platform(int plId, int gId, int status) {
        this.plId = plId;
        this.gId = gId;
        this.status = status;
    }

    public int getPlId() {
        return plId;
    }

    public void setPlId(int plId) {
        this.plId = plId;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
