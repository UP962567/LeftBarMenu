package com.example.LeftMenuBar.backcode;

/**
 * Created by MaDy on 11/04/2022 / 03:33
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class Friend {

    private String profileImageURL,gender,username;

    public Friend() {
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "profileImageURL='" + profileImageURL + '\'' +
                ", gender='" + gender + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
