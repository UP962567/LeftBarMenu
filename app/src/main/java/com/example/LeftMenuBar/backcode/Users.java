package com.example.LeftMenuBar.backcode;

public class Users {

    private String country,fullName,gender,phone,profileImage,status,username, accessLevel;
    private long ID_ACC_LEVEL;

    public Users(String country, String fullName, String gender, String phone, String profileImage, String status, String username, String accessLevel, long ID_ACC_LEVEL) {
        this.country = country;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.profileImage = profileImage;
        this.status = status;
        this.username = username;
        this.accessLevel = accessLevel;
        this.ID_ACC_LEVEL = ID_ACC_LEVEL;
    }

    public Users() {
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public long getID_ACC_LEVEL() {
        return ID_ACC_LEVEL;
    }

    public void setID_ACC_LEVEL(long ID_ACC_LEVEL) {
        this.ID_ACC_LEVEL = ID_ACC_LEVEL;
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


}
