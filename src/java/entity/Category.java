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
public class Category {
    private int caId;
    private String caName;
    private String descript;
    private int status;

    public Category() {
    }

    public Category(int caId, String caName, String descript, int status) {
        this.caId = caId;
        this.caName = caName;
        this.descript = descript;
        this.status = status;
    }

    public int getCaId() {
        return caId;
    }

    public String getCaName() {
        return caName;
    }

    public String getDescript() {
        return descript;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCaId(int caId) {
        this.caId = caId;
    }

    public void setCaName(String caName) {
        this.caName = caName;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
