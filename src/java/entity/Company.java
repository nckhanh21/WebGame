/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Duong
 */
public class Company {
    private int coId;
    private String coName;
    private Date foundDate;
    private String description;
    private String logo;
    private String coAddress;
    private String coPhone;
    private String coMail;
    private int status;

    public Company() {
    }

    public Company(int coId, String coName, Date foundDate, String description, String logo, String coAddress, String coPhone, String coMail) {
        this.coId = coId;
        this.coName = coName;
        this.foundDate = foundDate;
        this.description = description;
        this.logo = logo;
        this.coAddress = coAddress;
        this.coPhone = coPhone;
        this.coMail = coMail;
    }

    public Company(int coId, String coName, Date foundDate, String description, String logo, String coAddress, String coPhone, String coMail, int status) {
        this.coId = coId;
        this.coName = coName;
        this.foundDate = foundDate;
        this.description = description;
        this.logo = logo;
        this.coAddress = coAddress;
        this.coPhone = coPhone;
        this.coMail = coMail;
        this.status = status;
    }
    
    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoAddress() {
        return coAddress;
    }

    public void setCoAddress(String coAddress) {
        this.coAddress = coAddress;
    }

    public String getCoPhone() {
        return coPhone;
    }

    public void setCoPhone(String coPhone) {
        this.coPhone = coPhone;
    }

    public String getCoMail() {
        return coMail;
    }

    public void setCoMail(String coMail) {
        this.coMail = coMail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Company{" + "coId=" + coId + ", coName=" + coName + ", foundDate=" + foundDate + ", coAddress=" + coAddress + ", coPhone=" + coPhone + ", coMail=" + coMail + ", status=" + status + '}';
    }
    
}
