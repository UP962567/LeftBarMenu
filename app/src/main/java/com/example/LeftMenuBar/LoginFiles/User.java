package com.example.LeftMenuBar.LoginFiles;

/**
 * Created by MaDy on 09/04/2022 / 23:59
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class User {
    private static String username;
    private static String email;
    private static String level;
    private static String levelAccess;
    private static String fullname;
    private static String imgProfile;
    private static int levelID;

    public static int getLevelID() {
        return levelID;
    }

    public static void setLevelID(int levelID) {
        User.levelID = levelID;
    }

    public static String getLevelAccess() {
        return levelAccess;
    }
    public static void setLevelAccess(String levelAccess) {
        User.levelAccess = levelAccess;
    }

    public static String getImgProfile() {
        return imgProfile;
    }

    public static void setImgProfile(String imgProfile) {
        User.imgProfile = imgProfile;
    }

    public static String getFullname() {
        return fullname;
    }

    public static void setFullname(String fullname) {
        User.fullname = fullname;
    }

    public User() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getLevel() {
        return level;
    }

    public static void setLevel(String level) {
        User.level = level;
    }

}
