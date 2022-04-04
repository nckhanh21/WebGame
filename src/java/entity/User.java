/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Duong
 */
public class User {

    private int uId;
    private String uName;
    private int experience;
    private String profilePicture;
    private String uMail;
    private String uPhone;
    private String uAddress;
    private double wallet;
    private String system_role;
    private String username;
    private String pass;
    private int status;

    public User() {
    }

    public User(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public User(String uName, String uMail, String uPhone, String uAddress, String system_role, String username, String pass) {
        this.uName = uName;
        this.uMail = uMail;
        this.uPhone = uPhone;
        this.uAddress = uAddress;
        this.system_role = system_role;
        this.username = username;
        this.pass = pass;
    }

    public User(int uId, String uName, String uMail, String uPhone, String uAddress) {
        this.uId = uId;
        this.uName = uName;
        this.uMail = uMail;
        this.uPhone = uPhone;
        this.uAddress = uAddress;
    }

    public User(int uId, String uName, int experience,String profilePicture, String uMail, String uPhone, String uAddress, double wallet, String system_role, String username, String pass, int status) {
        this.uId = uId;
        this.uName = uName;
        this.experience = experience;
        this.profilePicture = profilePicture;
        this.uMail = uMail;
        this.uPhone = uPhone;
        this.uAddress = uAddress;
        this.wallet = wallet;
        this.system_role = system_role;
        this.username = username;
        this.pass = pass;
        this.status = status;
    }

    public User(int uId, String uName, int experience, String uMail, String uPhone, String uAddress, double wallet, String system_role, String username, String pass) {
        this.uId = uId;
        this.uName = uName;
        this.experience = experience;
        this.uMail = uMail;
        this.uPhone = uPhone;
        this.uAddress = uAddress;
        this.wallet = wallet;
        this.system_role = system_role;
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getuMail() {
        return uMail;
    }

    public void setuMail(String uMail) {
        this.uMail = uMail;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getSystem_role() {
        return system_role;
    }

    public void setSystem_role(String system_role) {
        this.system_role = system_role;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "uId=" + uId + ", uName=" + uName + ", experience=" + experience + ", uMail=" + uMail + ", uPhone=" + uPhone + ", uAddress=" + uAddress + ", wallet=" + wallet + ", system_role=" + system_role + ", pass=" + pass + ", status=" + status + '}';
    }

}
